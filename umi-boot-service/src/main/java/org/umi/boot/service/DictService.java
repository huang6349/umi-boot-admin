package org.umi.boot.service;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umi.boot.commons.exception.BadRequestException;
import org.umi.boot.commons.exception.DataAlreadyExistException;
import org.umi.boot.commons.utils.LevelUtil;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.domain.Dict;
import org.umi.boot.repository.DictRepository;
import org.umi.boot.web.rest.manage.DictManage;

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

    public Dict create(DictManage manage) {
        boolean isRoot = manage.getPid() == null || manage.getPid().equals(0L);
        if (isRoot && StrUtil.isBlank(manage.getCode())) {
            throw new BadRequestException("一级字典信息的唯一标识码不能为空");
        }
        if (isRoot && StrUtil.isNotBlank(manage.getData())) {
            throw new BadRequestException("一级字典信息的数据必须为空");
        }
        if (isRoot && !dictRepository.findByCode(manage.getCode()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("唯一标识码为【{}】的字典信息已经存在了", manage.getCode()));
        }
        if (!isRoot && StrUtil.isNotBlank(manage.getCode())) {
            throw new BadRequestException("子级字典信息的唯一标识码必须为空");
        }
        if (!isRoot && StrUtil.isBlank(manage.getData())) {
            throw new BadRequestException("子级字典信息的数据不能为空");
        }
        Dict dict = new Dict();
        BeanUtils.copyProperties(manage, dict);
        dict.setState(GlobalConstants.DATA_NORMAL_STATE);
        dict.setLevel(LevelUtil.calculateLevel(getLevel(manage.getPid()), manage.getPid()));
        return dictRepository.save(dict);
    }

    public Dict update(DictManage manage) {
        Dict dict = findById(manage.getId(), StrUtil.format("数据编号为【{}】的字典信息不存在，无法进行修改操作", manage.getId()));
        if (GlobalConstants.DATA_KEEP_STATE.equals(dict.getState())) {
            if (!StrUtil.equals(StrUtil.trimToNull(manage.getName()), StrUtil.trimToNull(dict.getName()))) {
                throw new BadRequestException("该字典为系统保留字典，无法进行名称修改操作");
            }
            if (!StrUtil.equals(StrUtil.trimToNull(manage.getCode()), StrUtil.trimToNull(dict.getCode()))) {
                throw new BadRequestException("该字典为系统保留字典，无法进行唯一标识码修改操作");
            }
            if (!StrUtil.equals(StrUtil.trimToNull(manage.getData()), StrUtil.trimToNull(dict.getData()))) {
                throw new BadRequestException("该字典为系统保留字典，无法进行数据修改操作");
            }
        }
        boolean isRoot = manage.getPid() == null || manage.getPid().equals(0L);
        if (isRoot && StringUtils.isBlank(manage.getCode())) {
            throw new BadRequestException("一级字典信息的唯一标识码不能为空");
        }
        if (isRoot && StringUtils.isNotBlank(manage.getData())) {
            throw new BadRequestException("一级字典信息的数据必须为空");
        }
        if (isRoot && !dictRepository.findByCodeAndIdNot(manage.getCode(), dict.getId()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("唯一标识码为【{}】的信息已经存在了", manage.getCode()));
        }
        if (!isRoot && StringUtils.isNotBlank(manage.getCode())) {
            throw new BadRequestException("子级字典信息的唯一标识码必须为空");
        }
        if (!isRoot && StringUtils.isBlank(manage.getData())) {
            throw new BadRequestException("子级字典信息的数据不能为空");
        }
        String beforeLevel = dict.getLevel();
        String afterLevel = LevelUtil.calculateLevel(getLevel(manage.getPid()), manage.getPid());
        if (StringUtils.startsWith(afterLevel, LevelUtil.calculateLevel(beforeLevel, manage.getId()))) {
            throw new BadRequestException("不允许将自己设置为自己的子级");
        }
        BeanUtils.copyProperties(manage, dict);
        if (!StringUtils.equals(beforeLevel, afterLevel)) {
            dict.setLevel(afterLevel);
            batchUpdateLevel(beforeLevel, afterLevel, manage.getId());
        }
        return dictRepository.save(dict);
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
