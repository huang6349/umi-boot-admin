package org.umi.boot.web.rest;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.wenhao.jpa.Specifications;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.umi.boot.commons.exception.DataNotAlreadyIDException;
import org.umi.boot.commons.info.Info;
import org.umi.boot.commons.info.InfoStructure;
import org.umi.boot.commons.utils.LevelUtil;
import org.umi.boot.domain.Dict;
import org.umi.boot.repository.DictRepository;
import org.umi.boot.service.DictService;
import org.umi.boot.service.mapper.DictLevelMapper;
import org.umi.boot.service.mapper.DictMapper;
import org.umi.boot.web.rest.filters.DictFilters;
import org.umi.boot.web.rest.manage.DictManage;

import javax.validation.Valid;

@Api(tags = "字典管理")
@RestController
@RequestMapping("/api")
public class DictResource {

    @Autowired
    private DictRepository dictRepository;

    @Autowired
    private DictService dictService;

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private DictLevelMapper dictLevelMapper;

    @ApiOperation("新增一个字典")
    @PostMapping("/dict")
    public InfoStructure create(@Valid @RequestBody DictManage manage) {
        if (manage.getId() != null) {
            throw new DataNotAlreadyIDException();
        }
        return Info.success(dictMapper.adapt(dictService.create(manage)));
    }

    @ApiOperation("查询所有的字典")
    @GetMapping("/dict")
    public InfoStructure query() {
        return Info.success(dictMapper.adapt(dictRepository.findAll()));
    }

    @ApiOperation("查询所有的字典（树形数据）")
    @GetMapping("/dict/tree")
    public InfoStructure queryToTree() {
        return Info.success(dictLevelMapper.adaptToTree(dictRepository.findAll()));
    }

    @ApiOperation("查询一个字典")
    @GetMapping("/dict/{id}")
    public InfoStructure query(@PathVariable Long id) {
        return Info.success(dictMapper.adapt(dictRepository.findById(id).orElse(null)));
    }

    @ApiOperation("查询一个字典（子级列表）")
    @GetMapping("/dict/children/{id}")
    public InfoStructure queryToChildren(@PathVariable Long id) {
        Dict dict = dictService.findById(id);
        Specification<Dict> specification = Specifications.<Dict>and()
                .like("level", LevelUtil.calculateLevel(dict.getLevel(), dict.getId()) + "%")
                .build();
        return Info.success(dictMapper.adapt(dictRepository.findAll(specification)));
    }

    @ApiOperation("分页查询字典")
    @GetMapping("/dict/pageable")
    public InfoStructure query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String filters) {
        DictFilters dictFilters = JSONUtil.toBean(filters, DictFilters.class);
        Specification<Dict> specification = Specifications.<Dict>and()
                .eq(dictFilters.getPid() != null, "pid", dictFilters.getPid())
                .like(StrUtil.isNotBlank(dictFilters.getName()), "name", "%" + StrUtil.trim(dictFilters.getName()) + "%")
                .like(StrUtil.isNotBlank(dictFilters.getCode()), "code", "%" + StrUtil.trim(dictFilters.getCode()) + "%")
                .like(StrUtil.isNotBlank(dictFilters.getData()), "data", "%" + StrUtil.trim(dictFilters.getData()) + "%")
                .like(StrUtil.isNotBlank(dictFilters.getDesc()), "desc", "%" + StrUtil.trim(dictFilters.getDesc()) + "%")
                .like(StrUtil.isNotBlank(dictFilters.getLastModifiedBy()), "lastModifiedBy", "%" + StrUtil.trim(dictFilters.getLastModifiedBy()) + "%")
                .build();
        return Info.success(dictRepository.findAll(specification, pageable).map(dictMapper::adapt));
    }

    @ApiOperation("修改一个字典")
    @PutMapping("/dict")
    public InfoStructure update(@Valid @RequestBody DictManage manage) {
        return Info.success(dictMapper.adapt(dictService.update(manage)));
    }

    @ApiOperation("删除一个或多个字典")
    @DeleteMapping("/dict/{ids}")
    public InfoStructure delete(@PathVariable Long[] ids) {
        return Info.success(dictMapper.adapt(dictService.batchDelete(ids)));
    }
}
