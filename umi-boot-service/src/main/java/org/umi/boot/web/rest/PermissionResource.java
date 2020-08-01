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
import org.umi.boot.commons.utils.LevelUtil;
import org.umi.boot.domain.Permission;
import org.umi.boot.repository.PermissionRepository;
import org.umi.boot.service.PermissionService;
import org.umi.boot.service.mapper.PermissionLevelMapper;
import org.umi.boot.service.mapper.PermissionMapper;
import org.umi.boot.web.rest.manage.PermissionIdAttribute;
import org.umi.boot.web.rest.query.PermissionFilter;
import org.umi.boot.web.rest.manage.PermissionAttribute;

import javax.validation.Valid;

@Api(tags = "菜单管理", value = "PermissionResource")
@RestController
@RequestMapping("/api")
public class PermissionResource {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private PermissionLevelMapper permissionLevelMapper;

    @ApiOperation(value = "新增一个菜单")
    @PostMapping("/permission")
    public InfoStructure create(@Valid @RequestBody PermissionAttribute attribute) {
        return Info.success(permissionMapper.adapt(permissionService.create(attribute)));
    }

    @ApiOperation(value = "查询所有的菜单")
    @GetMapping("/permission")
    public InfoStructure query() {
        return Info.success(permissionMapper.adapt(permissionRepository.findAll()));
    }

    @ApiOperation(value = "查询所有的菜单（树形数据）")
    @GetMapping("/permission/tree")
    public InfoStructure queryToTree() {
        return Info.success(permissionLevelMapper.adaptToTree(permissionRepository.findAll()));
    }

    @ApiOperation(value = "查询一个菜单")
    @GetMapping("/permission/{id}")
    public InfoStructure query(@PathVariable Long id) {
        return Info.success(permissionMapper.adapt(permissionRepository.findById(id).orElse(null)));
    }

    @ApiOperation(value = "查询一个菜单（子级列表）")
    @GetMapping("/permission/children/{id}")
    public InfoStructure queryToChildren(@PathVariable Long id) {
        Permission permission = permissionService.findById(id, StrUtil.format("数据编号为【{}】的菜单信息不存在，无法进行查询操作", id));
        Specification<Permission> specification = Specifications.<Permission>and()
                .like("level", LevelUtil.calculateLevel(permission.getLevel(), permission.getId()) + "%")
                .build();
        return Info.success(permissionMapper.adapt(permissionRepository.findAll(specification)));
    }

    @ApiOperation(value = "分页查询菜单")
    @GetMapping("/permission/pageable")
    public InfoStructure query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String filters) {
        PermissionFilter permissionFilter = JSONUtil.toBean(filters, PermissionFilter.class);
        Specification<Permission> specification = Specifications.<Permission>and()
                .eq(permissionFilter.getPid() != null, "pid", permissionFilter.getPid())
                .like(StrUtil.isNotBlank(permissionFilter.getName()), "name", "%" + StrUtil.trim(permissionFilter.getName()) + "%")
                .like(StrUtil.isNotBlank(permissionFilter.getPath()), "path", "%" + StrUtil.trim(permissionFilter.getPath()) + "%")
                .like(StrUtil.isNotBlank(permissionFilter.getIcon()), "icon", "%" + StrUtil.trim(permissionFilter.getIcon()) + "%")
                .like(StrUtil.isNotBlank(permissionFilter.getDesc()), "desc", "%" + StrUtil.trim(permissionFilter.getDesc()) + "%")
                .like(StrUtil.isNotBlank(permissionFilter.getLastModifiedBy()), "lastModifiedBy", "%" + StrUtil.trim(permissionFilter.getLastModifiedBy()) + "%")
                .build();
        return Info.success(permissionRepository.findAll(specification, pageable).map(permissionMapper::adapt));
    }

    @ApiOperation(value = "修改一个菜单")
    @PutMapping("/permission")
    public InfoStructure update(@Valid @RequestBody PermissionIdAttribute attribute) {
        return Info.success(permissionMapper.adapt(permissionService.update(attribute)));
    }

    @ApiOperation(value = "删除一个或多个菜单")
    @DeleteMapping("/permission/{ids}")
    public InfoStructure delete(@PathVariable Long[] ids) {
        return Info.success(permissionMapper.adapt(permissionService.batchDelete(ids)));
    }
}
