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
import org.umi.boot.domain.Resource;
import org.umi.boot.repository.ResourceRepository;
import org.umi.boot.service.ResourceService;
import org.umi.boot.service.mapper.ResourceMapper;
import org.umi.boot.web.rest.filters.ResourceFilters;
import org.umi.boot.web.rest.manage.ResourceManage;

import javax.validation.Valid;

@Api(tags = "菜单资源管理")
@RestController
@RequestMapping("/api")
public class ResourceResource {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceMapper resourceMapper;

    @ApiOperation("新增一个菜单资源")
    @PostMapping("/resource")
    public InfoStructure create(@Valid @RequestBody ResourceManage manage) {
        if (manage.getId() != null) {
            throw new DataNotAlreadyIDException();
        }
        return Info.success(resourceMapper.adapt(resourceService.create(manage)));
    }

    @ApiOperation("查询所有的菜单资源")
    @GetMapping("/resource")
    public InfoStructure query() {
        return Info.success(resourceMapper.adapt(resourceRepository.findAll()));
    }

    @ApiOperation("查询一个菜单资源")
    @GetMapping("/resource/{id}")
    public InfoStructure query(@PathVariable Long id) {
        return Info.success(resourceMapper.adapt(resourceRepository.findById(id).orElse(null)));
    }

    @ApiOperation("分页查询菜单资源")
    @GetMapping("/resource/pageable")
    public InfoStructure query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String filters) {
        ResourceFilters resourceFilters = JSONUtil.toBean(filters, ResourceFilters.class);
        Specification<Resource> specification = Specifications.<Resource>and()
                .eq(resourceFilters.getPermissionId() != null, "permission.id", resourceFilters.getPermissionId())
                .like(StrUtil.isNotBlank(resourceFilters.getPattern()), "pattern", "%" + StrUtil.trim(resourceFilters.getPattern()) + "%")
                .eq(resourceFilters.getMethodId() != null, "method.id", resourceFilters.getMethodId())
                .like(StrUtil.isNotBlank(resourceFilters.getDesc()), "desc", "%" + StrUtil.trim(resourceFilters.getDesc()) + "%")
                .like(StrUtil.isNotBlank(resourceFilters.getLastModifiedBy()), "lastModifiedBy", "%" + StrUtil.trim(resourceFilters.getLastModifiedBy()) + "%")
                .build();
        return Info.success(resourceRepository.findAll(specification, pageable).map(resourceMapper::adapt));
    }

    @ApiOperation("修改一个菜单资源")
    @PutMapping("/resource")
    public InfoStructure update(@Valid @RequestBody ResourceManage manage) {
        return Info.success(resourceMapper.adapt(resourceService.update(manage)));
    }

    @ApiOperation("删除一个或多个菜单资源")
    @DeleteMapping("/resource/{ids}")
    public InfoStructure delete(@PathVariable Long[] ids) {
        return Info.success(resourceMapper.adapt(resourceService.batchDelete(ids)));
    }
}
