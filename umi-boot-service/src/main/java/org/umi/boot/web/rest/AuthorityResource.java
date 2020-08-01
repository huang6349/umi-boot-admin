package org.umi.boot.web.rest;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.wenhao.jpa.Specifications;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
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
import org.umi.boot.domain.Authority;
import org.umi.boot.repository.AuthorityRepository;
import org.umi.boot.service.AuthorityService;
import org.umi.boot.service.mapper.AuthorityMapper;
import org.umi.boot.web.rest.query.AuthorityFilter;
import org.umi.boot.web.rest.manage.AuthorityAttribute;
import org.umi.boot.web.rest.manage.AuthorityIdAttribute;

import javax.validation.Valid;
import java.util.Set;

@Api(tags = "角色管理", value = "AuthorityResource")
@RestController
@RequestMapping("/api")
public class AuthorityResource {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private AuthorityMapper authorityMapper;

    @ApiOperation(value = "新增一个角色")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @PostMapping("/authority")
    public InfoStructure create(@Valid @RequestBody AuthorityAttribute attribute) {
        return Info.success(authorityMapper.adapt(authorityService.create(attribute)));
    }

    @ApiOperation(value = "查询所有的角色")
    @GetMapping("/authority")
    public InfoStructure query() {
        return Info.success(authorityMapper.adapt(authorityRepository.findAll()));
    }

    @ApiOperation(value = "查询一个角色")
    @GetMapping("/authority/{id}")
    public InfoStructure query(@PathVariable Long id) {
        return Info.success(authorityMapper.adapt(authorityRepository.findById(id).orElse(null)));
    }

    @ApiOperation(value = "分页查询角色")
    @GetMapping("/authority/pageable")
    public InfoStructure query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String filters) {
        AuthorityFilter authorityFilter = JSONUtil.toBean(filters, AuthorityFilter.class);
        Specification<Authority> specification = Specifications.<Authority>and()
                .like(StrUtil.isNotBlank(authorityFilter.getName()), "name", "%" + StrUtil.trim(authorityFilter.getName()) + "%")
                .like(StrUtil.isNotBlank(authorityFilter.getCode()), "code", "%" + StrUtil.trim(authorityFilter.getCode()) + "%")
                .like(StrUtil.isNotBlank(authorityFilter.getDesc()), "desc", "%" + StrUtil.trim(authorityFilter.getDesc()) + "%")
                .like(StrUtil.isNotBlank(authorityFilter.getLastModifiedBy()), "lastModifiedBy", "%" + StrUtil.trim(authorityFilter.getLastModifiedBy()) + "%")
                .build();
        return Info.success(authorityRepository.findAll(specification, pageable).map(authorityMapper::adapt));
    }

    @ApiOperation(value = "修改一个角色")
    @PutMapping("/authority")
    public InfoStructure update(@Valid @RequestBody AuthorityIdAttribute attribute) {
        return Info.success(authorityMapper.adapt(authorityService.update(attribute)));
    }

    @ApiOperation(value = "修改一个角色的菜单")
    @PutMapping("/authority/permissions/{id}")
    public InfoStructure update(@PathVariable Long id, @Valid @RequestBody Set<Long> permissionIds) {
        return Info.success(authorityMapper.adapt(authorityService.update(id, permissionIds)));
    }

    @ApiOperation(value = "删除一个或多个角色")
    @DeleteMapping("/authority/{ids}")
    public InfoStructure delete(@PathVariable Long[] ids) {
        return Info.success(authorityMapper.adapt(authorityService.batchDelete(ids)));
    }
}
