package org.umi.boot.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.umi.boot.commons.info.Info;
import org.umi.boot.commons.info.InfoStructure;
import org.umi.boot.repository.PermissionRepository;
import org.umi.boot.service.mapper.PermissionLevelMapper;
import org.umi.boot.service.mapper.PermissionMapper;

@Api(tags = "菜单管理")
@RestController
@RequestMapping("/api")
public class PermissionResource {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private PermissionLevelMapper permissionLevelMapper;

    @ApiOperation("查询所有的菜单")
    @GetMapping("/permission")
    public InfoStructure query() {
        return Info.success(permissionMapper.adapt(permissionRepository.findAll()));
    }

    @ApiOperation("查询所有的菜单（树形数据）")
    @GetMapping("/permission/tree")
    public InfoStructure queryToTree() {
        return Info.success(permissionLevelMapper.adaptToTree(permissionRepository.findAll()));
    }
}
