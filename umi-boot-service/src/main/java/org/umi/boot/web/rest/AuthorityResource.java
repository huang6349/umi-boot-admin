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
import org.umi.boot.domain.Authority;
import org.umi.boot.repository.AuthorityRepository;
import org.umi.boot.service.AuthorityService;
import org.umi.boot.service.mapper.AuthorityMapper;
import org.umi.boot.web.rest.filters.AuthorityFilters;
import org.umi.boot.web.rest.manage.AuthorityManage;

import javax.validation.Valid;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/api")
public class AuthorityResource {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private AuthorityMapper authorityMapper;

    @ApiOperation("新增一个角色")
    @PostMapping("/authority")
    public InfoStructure create(@Valid @RequestBody AuthorityManage manage) {
        if (manage.getId() != null) {
            throw new DataNotAlreadyIDException();
        }
        return Info.success(authorityMapper.adapt(authorityService.create(manage)));
    }

    @ApiOperation("查询所有的角色")
    @GetMapping("/authority")
    public InfoStructure query() {
        return Info.success(authorityMapper.adapt(authorityRepository.findAll()));
    }

    @ApiOperation("查询一个角色")
    @GetMapping("/authority/{id}")
    public InfoStructure query(@PathVariable Long id) {
        return Info.success(authorityMapper.adapt(authorityRepository.findById(id).orElse(null)));
    }

    @ApiOperation("分页查询角色")
    @GetMapping("/authority/pageable")
    public InfoStructure query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String filters) {
        AuthorityFilters authorityFilters = JSONUtil.toBean(filters, AuthorityFilters.class);
        Specification<Authority> specification = Specifications.<Authority>and()
                .like(StrUtil.isNotBlank(authorityFilters.getName()), "name", "%" + StrUtil.trim(authorityFilters.getName()) + "%")
                .like(StrUtil.isNotBlank(authorityFilters.getCode()), "code", "%" + StrUtil.trim(authorityFilters.getCode()) + "%")
                .like(StrUtil.isNotBlank(authorityFilters.getDesc()), "desc", "%" + StrUtil.trim(authorityFilters.getDesc()) + "%")
                .like(StrUtil.isNotBlank(authorityFilters.getLastModifiedBy()), "lastModifiedBy", "%" + StrUtil.trim(authorityFilters.getLastModifiedBy()) + "%")
                .build();
        return Info.success(authorityRepository.findAll(specification, pageable).map(authorityMapper::adapt));
    }

    @ApiOperation("修改一个角色")
    @PutMapping("/authority")
    public InfoStructure update(@Valid @RequestBody AuthorityManage manage) {
        return Info.success(authorityMapper.adapt(authorityService.update(manage)));
    }

    @ApiOperation("删除一个或多个角色")
    @DeleteMapping("/authority/{ids}")
    public InfoStructure delete(@PathVariable Long[] ids) {
        return Info.success(authorityMapper.adapt(authorityService.batchDelete(ids)));
    }
}
