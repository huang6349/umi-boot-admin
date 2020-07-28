package org.umi.boot.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.umi.boot.commons.info.Info;
import org.umi.boot.commons.info.InfoStructure;
import org.umi.boot.repository.AuthorityRepository;
import org.umi.boot.service.mapper.AuthorityMapper;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/api")
public class AuthorityResource {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthorityMapper authorityMapper;

    @ApiOperation("查询所有的角色")
    @GetMapping("/authority")
    public InfoStructure query() {
        return Info.success(authorityMapper.adapt(authorityRepository.findAll()));
    }
}
