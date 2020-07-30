package org.umi.boot.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.umi.boot.commons.info.Info;
import org.umi.boot.commons.info.InfoStructure;
import org.umi.boot.service.PermissionService;
import org.umi.boot.service.UserService;
import org.umi.boot.service.mapper.PermissionLevelMapper;
import org.umi.boot.service.mapper.PermissionMapper;
import org.umi.boot.service.mapper.UserMapper;

@Api(tags = "帐号管理")
@RestController
@RequestMapping("/api")
public class AccountResource {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private PermissionLevelMapper permissionLevelMapper;

    @ApiOperation("获取当前用户信息")
    @GetMapping("/account")
    public InfoStructure account() {
        return Info.success(userMapper.adapt(userService.getCurrentUser()));
    }

    @ApiOperation("获取当前用户权限")
    @GetMapping("/authorities")
    public InfoStructure authorities() {
        return Info.success(permissionMapper.adapt(permissionService.getCurrentUserPermissions()));
    }

    @ApiOperation("获取当前用户权限（树形数据）")
    @GetMapping("/authorities/tree")
    public InfoStructure authoritiesToTree() {
        return Info.success(permissionLevelMapper.adaptToTree(permissionService.getCurrentUserPermissions()));
    }
}
