package org.umi.boot.web.rest;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.wenhao.jpa.Specifications;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.umi.boot.commons.info.Info;
import org.umi.boot.commons.info.InfoStructure;
import org.umi.boot.commons.utils.LevelUtil;
import org.umi.boot.domain.Dict;
import org.umi.boot.repository.DictRepository;
import org.umi.boot.service.DictService;
import org.umi.boot.service.mapper.DictLevelMapper;
import org.umi.boot.service.mapper.DictMapper;
import org.umi.boot.web.rest.manage.DictAttribute;
import org.umi.boot.web.rest.manage.DictIdAttribute;
import org.umi.boot.web.rest.query.DictFilter;

import javax.validation.Valid;

@Api(tags = "字典管理", value = "DictResource")
@ApiSupport(order = 10)
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

    @ApiOperation(value = "新增一个字典")
    @PostMapping("/dict")
    public InfoStructure create(@Valid @RequestBody DictAttribute attribute) {
        return Info.success(dictMapper.adapt(dictService.create(attribute)));
    }

    @ApiOperation(value = "查询所有的字典")
    @GetMapping("/dict")
    public InfoStructure query() {
        return Info.success(dictMapper.adapt(dictRepository.findAll()));
    }

    @ApiOperation(value = "查询所有的字典（树形数据）")
    @GetMapping("/dict/tree")
    public InfoStructure queryToTree() {
        return Info.success(dictLevelMapper.adaptToTree(dictRepository.findAll()));
    }

    @ApiOperation(value = "查询一个字典")
    @GetMapping("/dict/{id}")
    public InfoStructure query(@PathVariable Long id) {
        return Info.success(dictMapper.adapt(dictRepository.findById(id).orElse(null)));
    }

    @ApiOperation(value = "查询一个字典（子级列表）")
    @GetMapping("/dict/children/{id}")
    public InfoStructure queryToChildren(@PathVariable Long id) {
        Dict dict = dictService.findById(id, StrUtil.format("数据编号为【{}】的字典信息不存在，无法进行查询操作", id));
        Specification<Dict> specification = Specifications.<Dict>and()
                .like("level", LevelUtil.calculateLevel(dict.getLevel(), dict.getId()) + "%")
                .build();
        return Info.success(dictMapper.adapt(dictRepository.findAll(specification)));
    }

    @ApiOperation(value = "分页查询字典")
    @GetMapping("/dict/pageable")
    public InfoStructure query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String filters) {
        DictFilter dictFilter = JSONUtil.toBean(filters, DictFilter.class);
        Specification<Dict> specification = Specifications.<Dict>and()
                .eq(dictFilter.getPid() != null, "pid", dictFilter.getPid())
                .like(StrUtil.isNotBlank(dictFilter.getName()), "name", "%" + StrUtil.trim(dictFilter.getName()) + "%")
                .like(StrUtil.isNotBlank(dictFilter.getCode()), "code", "%" + StrUtil.trim(dictFilter.getCode()) + "%")
                .like(StrUtil.isNotBlank(dictFilter.getData()), "data", "%" + StrUtil.trim(dictFilter.getData()) + "%")
                .like(StrUtil.isNotBlank(dictFilter.getDesc()), "desc", "%" + StrUtil.trim(dictFilter.getDesc()) + "%")
                .like(StrUtil.isNotBlank(dictFilter.getLastModifiedBy()), "lastModifiedBy", "%" + StrUtil.trim(dictFilter.getLastModifiedBy()) + "%")
                .build();
        return Info.success(dictRepository.findAll(specification, pageable).map(dictMapper::adapt));
    }

    @ApiOperation(value = "修改一个字典")
    @PutMapping("/dict")
    public InfoStructure update(@Valid @RequestBody DictIdAttribute attribute) {
        return Info.success(dictMapper.adapt(dictService.update(attribute)));
    }

    @ApiOperation(value = "删除一个或多个字典")
    @DeleteMapping("/dict/{ids}")
    public InfoStructure delete(@PathVariable Long[] ids) {
        return Info.success(dictMapper.adapt(dictService.batchDelete(ids)));
    }
}
