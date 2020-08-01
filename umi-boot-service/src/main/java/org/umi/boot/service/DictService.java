package org.umi.boot.service;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umi.boot.commons.exception.BadRequestException;
import org.umi.boot.commons.exception.DataAlreadyExistException;
import org.umi.boot.commons.utils.LevelUtil;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.domain.Dict;
import org.umi.boot.repository.DictRepository;
import org.umi.boot.web.rest.manage.DictAttribute;
import org.umi.boot.web.rest.manage.DictIdAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DictService {

    @Autowired
    private DictRepository dictRepository;

    @Transactional(readOnly = true)
    public Dict findById(Long id, String errorMessage) {
        if (id == null) throw new BadRequestException(errorMessage);
        Optional<Dict> dict = dictRepository.findById(id);
        if (!dict.isPresent()) throw new BadRequestException(errorMessage);
        return dict.get();
    }

    @Transactional(readOnly = true)
    public Dict findById(Long id, Long pid, String errorMessage) {
        Dict dict = findById(id, errorMessage);
        if (!dict.getPid().equals(pid)) throw new BadRequestException(errorMessage);
        return dict;
    }

    public Dict create(DictAttribute attribute) {
        boolean isRoot = attribute.getPid() == null || attribute.getPid().equals(0L);
        if (isRoot && StrUtil.isBlank(attribute.getCode())) {
            throw new BadRequestException("一级字典信息的唯一标识码不能为空");
        }
        if (isRoot && StrUtil.isNotBlank(attribute.getData())) {
            throw new BadRequestException("一级字典信息的数据必须为空");
        }
        if (isRoot && !dictRepository.findByCode(attribute.getCode()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("唯一标识码为【{}】的字典信息已经存在了", attribute.getCode()));
        }
        if (!isRoot && StrUtil.isNotBlank(attribute.getCode())) {
            throw new BadRequestException("子级字典信息的唯一标识码必须为空");
        }
        if (!isRoot && StrUtil.isBlank(attribute.getData())) {
            throw new BadRequestException("子级字典信息的数据不能为空");
        }
        Dict dict = DictAttribute.adapt(attribute);
        dict.setState(GlobalConstants.DATA_NORMAL_STATE);
        dict.setLevel(LevelUtil.calculateLevel(getLevel(attribute.getPid()), attribute.getPid()));
        return dictRepository.save(dict);
    }

    public Dict update(DictIdAttribute attribute) {
        Dict dict = findById(attribute.getId(), StrUtil.format("数据编号为【{}】的字典信息不存在，无法进行修改操作", attribute.getId()));
        if (GlobalConstants.DATA_KEEP_STATE.equals(dict.getState())) {
            if (!StrUtil.equals(StrUtil.trimToNull(attribute.getName()), StrUtil.trimToNull(dict.getName()))) {
                throw new BadRequestException("该字典为系统保留字典，无法进行名称修改操作");
            }
            if (!StrUtil.equals(StrUtil.trimToNull(attribute.getCode()), StrUtil.trimToNull(dict.getCode()))) {
                throw new BadRequestException("该字典为系统保留字典，无法进行唯一标识码修改操作");
            }
            if (!StrUtil.equals(StrUtil.trimToNull(attribute.getData()), StrUtil.trimToNull(dict.getData()))) {
                throw new BadRequestException("该字典为系统保留字典，无法进行数据修改操作");
            }
        }
        boolean isRoot = attribute.getPid() == null || attribute.getPid().equals(0L);
        if (isRoot && StringUtils.isBlank(attribute.getCode())) {
            throw new BadRequestException("一级字典信息的唯一标识码不能为空");
        }
        if (isRoot && StringUtils.isNotBlank(attribute.getData())) {
            throw new BadRequestException("一级字典信息的数据必须为空");
        }
        if (isRoot && !dictRepository.findByCodeAndIdNot(attribute.getCode(), dict.getId()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("唯一标识码为【{}】的信息已经存在了", attribute.getCode()));
        }
        if (!isRoot && StringUtils.isNotBlank(attribute.getCode())) {
            throw new BadRequestException("子级字典信息的唯一标识码必须为空");
        }
        if (!isRoot && StringUtils.isBlank(attribute.getData())) {
            throw new BadRequestException("子级字典信息的数据不能为空");
        }
        String beforeLevel = dict.getLevel();
        String afterLevel = LevelUtil.calculateLevel(getLevel(attribute.getPid()), attribute.getPid());
        if (StringUtils.startsWith(afterLevel, LevelUtil.calculateLevel(beforeLevel, attribute.getId()))) {
            throw new BadRequestException("不允许将自己设置为自己的子级");
        }
        Dict updateDict = DictIdAttribute.adapt(attribute, dict);
        if (!StringUtils.equals(beforeLevel, afterLevel)) {
            updateDict.setLevel(afterLevel);
            batchUpdateLevel(beforeLevel, afterLevel, attribute.getId());
        }
        return dictRepository.save(updateDict);
    }

    public List<Dict> batchDelete(Long[] ids) {
        return Arrays.stream(ids).map(this::delete).collect(Collectors.toList());
    }

    public Dict delete(Long id) {
        Dict dict = findById(id, StrUtil.format("数据编号为【{}】的字典信息不存在，无法进行删除操作", id));
        if (GlobalConstants.DATA_KEEP_STATE.equals(dict.getState())) {
            throw new BadRequestException(StrUtil.format("数据编号为【{}】的字典信息为系统保留数据，无法进行删除操作", id));
        }
        dict.setState(GlobalConstants.DATA_DELETE_STATE);
        batchUpdateState(dict.getLevel(), id);
        return dictRepository.save(dict);
    }

    private String getLevel(Long id) {
        return dictRepository.findById(id).map(Dict::getLevel).orElse(null);
    }

    private void batchUpdateLevel(String beforeLevel, String afterLevel, Long id) {
        dictRepository.findByLevelStartsWith(LevelUtil.calculateLevel(beforeLevel, id)).forEach(dict -> {
            dict.setLevel(StringUtils.replaceOnce(dict.getLevel(), beforeLevel, afterLevel));
            dictRepository.save(dict);
        });
    }

    private void batchUpdateState(String level, Long id) {
        dictRepository.findByLevelStartsWith(LevelUtil.calculateLevel(level, id)).forEach(dict -> {
            dict.setState(GlobalConstants.DATA_DELETE_STATE);
            dictRepository.save(dict);
        });
    }
}
