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
import org.umi.boot.commons.info.Info;
import org.umi.boot.commons.info.InfoStructure;
import org.umi.boot.domain.Resource;
import org.umi.boot.repository.ResourceRepository;
import org.umi.boot.service.ResourceService;
import org.umi.boot.service.mapper.ResourceMapper;
import org.umi.boot.web.rest.manage.ResourceIdAttribute;
import org.umi.boot.web.rest.query.ResourceFilter;
import org.umi.boot.web.rest.manage.ResourceAttribute;

import javax.validation.Valid;

@Api(tags = "菜单资源管理", value = "ResourceResource")
@RestController
@RequestMapping("/api")
public class ResourceResource {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceMapper resourceMapper;

    @ApiOperation(value = "新增一个菜单资源")
    @PostMapping("/resource")
    public InfoStructure create(@Valid @RequestBody ResourceAttribute attribute) {
        return Info.success(resourceMapper.adapt(resourceService.create(attribute)));
    }

    @ApiOperation(value = "查询所有的菜单资源")
    @GetMapping("/resource")
    public InfoStructure query() {
        return Info.success(resourceMapper.adapt(resourceRepository.findAll()));
    }

    @ApiOperation(value = "查询一个菜单资源")
    @GetMapping("/resource/{id}")
    public InfoStructure query(@PathVariable Long id) {
        return Info.success(resourceMapper.adapt(resourceRepository.findById(id).orElse(null)));
    }

    @ApiOperation(value = "分页查询菜单资源")
    @GetMapping("/resource/pageable")
    public InfoStructure query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String filters) {
        ResourceFilter resourceFilter = JSONUtil.toBean(filters, ResourceFilter.class);
        Specification<Resource> specification = Specifications.<Resource>and()
                .eq(resourceFilter.getPermissionId() != null, "permission.id", resourceFilter.getPermissionId())
                .like(StrUtil.isNotBlank(resourceFilter.getPattern()), "pattern", "%" + StrUtil.trim(resourceFilter.getPattern()) + "%")
                .eq(resourceFilter.getMethodId() != null, "method.id", resourceFilter.getMethodId())
                .like(StrUtil.isNotBlank(resourceFilter.getDesc()), "desc", "%" + StrUtil.trim(resourceFilter.getDesc()) + "%")
                .like(StrUtil.isNotBlank(resourceFilter.getLastModifiedBy()), "lastModifiedBy", "%" + StrUtil.trim(resourceFilter.getLastModifiedBy()) + "%")
                .build();
        return Info.success(resourceRepository.findAll(specification, pageable).map(resourceMapper::adapt));
    }

    @ApiOperation(value = "修改一个菜单资源")
    @PutMapping("/resource")
    public InfoStructure update(@Valid @RequestBody ResourceIdAttribute attribute) {
        return Info.success(resourceMapper.adapt(resourceService.update(attribute)));
    }

    @ApiOperation(value = "删除一个或多个菜单资源")
    @DeleteMapping("/resource/{ids}")
    public InfoStructure delete(@PathVariable Long[] ids) {
        return Info.success(resourceMapper.adapt(resourceService.batchDelete(ids)));
    }
}
