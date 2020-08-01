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
import org.umi.boot.domain.User;
import org.umi.boot.repository.UserRepository;
import org.umi.boot.service.UserService;
import org.umi.boot.service.mapper.UserMapper;
import org.umi.boot.web.rest.manage.UserIdAttribute;
import org.umi.boot.web.rest.query.UserFilter;
import org.umi.boot.web.rest.manage.UserAttribute;

import javax.validation.Valid;

@Api(tags = "用户管理", value = "UserResource")
@RestController
@RequestMapping("/api")
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value = "新增一个用户")
    @PostMapping("/user")
    public InfoStructure create(@Valid @RequestBody UserAttribute attribute) {
        return Info.success(userMapper.adapt(userService.create(attribute)));
    }

    @ApiOperation(value = "查询所有的用户")
    @GetMapping("/user")
    public InfoStructure query() {
        return Info.success(userMapper.adapt(userRepository.findAll()));
    }

    @ApiOperation(value = "查询一个用户")
    @GetMapping("/user/{id}")
    public InfoStructure query(@PathVariable Long id) {
        return Info.success(userMapper.adapt(userRepository.findById(id).orElse(null)));
    }

    @ApiOperation(value = "分页查询用户")
    @GetMapping("/user/pageable")
    public InfoStructure query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String filters) {
        UserFilter userFilter = JSONUtil.toBean(filters, UserFilter.class);
        Specification<User> specification = Specifications.<User>and()
                .like(StrUtil.isNotBlank(userFilter.getUsername()), "username", "%" + StrUtil.trim(userFilter.getUsername()) + "%")
                .like(StrUtil.isNotBlank(userFilter.getEmail()), "email", "%" + StrUtil.trim(userFilter.getEmail()) + "%")
                .like(StrUtil.isNotBlank(userFilter.getMobilePhone()), "mobilePhone", "%" + StrUtil.trim(userFilter.getMobilePhone()) + "%")
                .like(StrUtil.isNotBlank(userFilter.getNickname()), "info.nickname", "%" + StrUtil.trim(userFilter.getNickname()) + "%")
                .like(StrUtil.isNotBlank(userFilter.getRealname()), "info.realname", "%" + StrUtil.trim(userFilter.getRealname()) + "%")
                .like(StrUtil.isNotBlank(userFilter.getIdCard()), "info.idCard", "%" + StrUtil.trim(userFilter.getIdCard()) + "%")
                .like(StrUtil.isNotBlank(userFilter.getLastModifiedBy()), "lastModifiedBy", "%" + StrUtil.trim(userFilter.getLastModifiedBy()) + "%")
                .build();
        return Info.success(userRepository.findAll(specification, pageable).map(userMapper::adapt));
    }

    @ApiOperation(value = "修改一个用户")
    @PutMapping("/user")
    public InfoStructure update(@Valid @RequestBody UserIdAttribute attribute) {
        return Info.success(userMapper.adapt(userService.update(attribute)));
    }

    @ApiOperation(value = "启用一个或多个用户")
    @PutMapping("/user/enable/{ids}")
    public InfoStructure enable(@PathVariable Long[] ids) {
        return Info.success(userMapper.adapt(userService.batchEnable(ids)));
    }

    @ApiOperation(value = "禁用一个或多个用户")
    @PutMapping("/user/disable/{ids}")
    public InfoStructure disable(@PathVariable Long[] ids) {
        return Info.success(userMapper.adapt(userService.batchDisable(ids)));
    }

    @ApiOperation(value = "重置一个或多个用户密码")
    @PutMapping("/user/reset/password/{ids}")
    public InfoStructure resetPassword(@PathVariable Long[] ids) {
        return Info.success(userMapper.adapt(userService.batchResetPassword(ids)));
    }

    @ApiOperation(value = "删除一个或多个用户")
    @DeleteMapping("/user/{ids}")
    public InfoStructure delete(@PathVariable Long[] ids) {
        return Info.success(userMapper.adapt(userService.batchDelete(ids)));
    }
}
