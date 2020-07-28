package org.umi.boot.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.umi.boot.commons.info.Info;
import org.umi.boot.commons.info.InfoStructure;
import org.umi.boot.repository.ResourceRepository;
import org.umi.boot.service.mapper.ResourceMapper;

@Api(tags = "菜单资源管理")
@RestController
@RequestMapping("/api")
public class ResourceResource {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceMapper resourceMapper;

    @ApiOperation("查询所有的菜单资源")
    @GetMapping("/resource")
    public InfoStructure query() {
        return Info.success(resourceMapper.adapt(resourceRepository.findAll()));
    }
}
