package org.umi.boot.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.umi.boot.commons.info.Info;
import org.umi.boot.commons.info.InfoStructure;
import org.umi.boot.service.PermissionService;
import org.umi.boot.service.UserService;
import org.umi.boot.service.mapper.PermissionLevelMapper;
import org.umi.boot.service.mapper.PermissionMapper;
import org.umi.boot.service.mapper.UserMapper;
import org.umi.boot.web.rest.manage.ChangePasswordAttribute;

import javax.validation.Valid;

@Api(tags = "帐号管理", value = "AccountResource")
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

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/account")
    public InfoStructure account() {
        return Info.success(userMapper.adapt(userService.getCurrentUser()));
    }

    @ApiOperation(value = "获取当前用户权限")
    @GetMapping("/authorities")
    public InfoStructure authorities() {
        return Info.success(permissionMapper.adapt(permissionService.getCurrentUserPermissions()));
    }

    @ApiOperation(value = "获取当前用户权限（树形数据）")
    @GetMapping("/authorities/tree")
    public InfoStructure authoritiesToTree() {
        return Info.success(permissionLevelMapper.adaptToTree(permissionService.getCurrentUserPermissions()));
    }

    @ApiOperation(value = "当前用户密码修改")
    @PutMapping("/password/change")
    public InfoStructure changePassword(@Valid @RequestBody ChangePasswordAttribute attribute) {
        return Info.success(userService.changePassword(attribute));
    }
}
