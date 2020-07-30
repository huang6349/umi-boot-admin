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
import org.umi.boot.domain.User;
import org.umi.boot.repository.UserRepository;
import org.umi.boot.service.UserService;
import org.umi.boot.service.mapper.UserMapper;
import org.umi.boot.web.rest.filters.UserFilters;
import org.umi.boot.web.rest.manage.UserManage;

import javax.validation.Valid;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/api")
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @ApiOperation("新增一个用户")
    @PostMapping("/user")
    public InfoStructure create(@Valid @RequestBody UserManage manage) {
        if (manage.getId() != null) {
            throw new DataNotAlreadyIDException();
        }
        return Info.success(userMapper.adapt(userService.create(manage)));
    }

    @ApiOperation("查询所有的用户")
    @GetMapping("/user")
    public InfoStructure query() {
        return Info.success(userMapper.adapt(userRepository.findAll()));
    }

    @ApiOperation("查询一个用户")
    @GetMapping("/user/{id}")
    public InfoStructure query(@PathVariable Long id) {
        return Info.success(userMapper.adapt(userRepository.findById(id).orElse(null)));
    }

    @ApiOperation("分页查询用户")
    @GetMapping("/user/pageable")
    public InfoStructure query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String filters) {
        UserFilters userFilters = JSONUtil.toBean(filters, UserFilters.class);
        Specification<User> specification = Specifications.<User>and()
                .like(StrUtil.isNotBlank(userFilters.getUsername()), "username", "%" + StrUtil.trim(userFilters.getUsername()) + "%")
                .like(StrUtil.isNotBlank(userFilters.getEmail()), "email", "%" + StrUtil.trim(userFilters.getEmail()) + "%")
                .like(StrUtil.isNotBlank(userFilters.getMobilePhone()), "mobilePhone", "%" + StrUtil.trim(userFilters.getMobilePhone()) + "%")
                .like(StrUtil.isNotBlank(userFilters.getNickname()), "info.nickname", "%" + StrUtil.trim(userFilters.getNickname()) + "%")
                .like(StrUtil.isNotBlank(userFilters.getRealname()), "info.realname", "%" + StrUtil.trim(userFilters.getRealname()) + "%")
                .like(StrUtil.isNotBlank(userFilters.getIdCard()), "info.idCard", "%" + StrUtil.trim(userFilters.getIdCard()) + "%")
                .like(StrUtil.isNotBlank(userFilters.getLastModifiedBy()), "lastModifiedBy", "%" + StrUtil.trim(userFilters.getLastModifiedBy()) + "%")
                .build();
        return Info.success(userRepository.findAll(specification, pageable).map(userMapper::adapt));
    }

    @ApiOperation("修改一个用户")
    @PutMapping("/user")
    public InfoStructure update(@Valid @RequestBody UserManage manage) {
        return Info.success(userMapper.adapt(userService.update(manage)));
    }

    @ApiOperation("启用一个或多个用户")
    @PutMapping("/user/enable/{ids}")
    public InfoStructure enable(@PathVariable Long[] ids) {
        return Info.success(userMapper.adapt(userService.batchEnable(ids)));
    }

    @ApiOperation("禁用一个或多个用户")
    @PutMapping("/user/disable/{ids}")
    public InfoStructure disable(@PathVariable Long[] ids) {
        return Info.success(userMapper.adapt(userService.batchDisable(ids)));
    }

    @ApiOperation("删除一个或多个用户")
    @DeleteMapping("/user/{ids}")
    public InfoStructure delete(@PathVariable Long[] ids) {
        return Info.success(userMapper.adapt(userService.batchDelete(ids)));
    }
}
