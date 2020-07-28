package org.umi.boot.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.umi.boot.commons.info.Info;
import org.umi.boot.commons.info.InfoStructure;
import org.umi.boot.repository.UserRepository;
import org.umi.boot.service.mapper.UserMapper;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/api")
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @ApiOperation("查询所有的用户")
    @GetMapping("/user")
    public InfoStructure query() {
        return Info.success(userMapper.adapt(userRepository.findAll()));
    }
}
