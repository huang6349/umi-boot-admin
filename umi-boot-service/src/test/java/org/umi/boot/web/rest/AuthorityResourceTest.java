package org.umi.boot.web.rest;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.umi.boot.domain.Authority;
import org.umi.boot.domain.Permission;
import org.umi.boot.repository.AuthorityRepository;
import org.umi.boot.service.AuthorityService;
import org.umi.boot.service.PermissionService;
import org.umi.boot.web.rest.manage.AuthorityAttribute;
import org.umi.boot.web.rest.manage.AuthorityIdAttribute;
import org.umi.boot.web.rest.manage.PermissionAttribute;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "admin", password = "123456")
class AuthorityResourceTest {

    private static final String DEFAULT_NAME = "测试角色" + IdUtil.randomUUID();
    private static final String DEFAULT_CODE = RandomUtil.randomStringUpper(12);
    private static final String UPDATE_NAME = "测试角色" + IdUtil.randomUUID();
    private static final String UPDATE_CODE = RandomUtil.randomStringUpper(12);

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PermissionService permissionService;

    @Test
    void create() throws Exception {
        List<Authority> prevAll = authorityRepository.findAll();
        AuthorityAttribute attribute = new AuthorityAttribute();
        attribute.setName(DEFAULT_NAME);
        attribute.setCode(DEFAULT_CODE);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/api/authority")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(attribute)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Authority> currAll = authorityRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() + 1);
        Authority authority = currAll.get(currAll.size() - 1);
        Assertions.assertThat(authority.getId()).isNotNull();
        Assertions.assertThat(authority.getName()).isEqualTo(DEFAULT_NAME);
        Assertions.assertThat(authority.getCode()).isEqualTo(StrUtil.format("ROLE_{}", DEFAULT_CODE));
        Assertions.assertThat(authority.getDesc()).isNull();
        Assertions.assertThat(authority.getUsers()).isEmpty();
        Assertions.assertThat(authority.getPermissions()).isEmpty();
        Assertions.assertThat(authority.getCreatedBy()).isNotNull();
        Assertions.assertThat(authority.getCreatedDate()).isNotNull();
        Assertions.assertThat(authority.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(authority.getLastModifiedDate()).isNotNull();
    }

    @Test
    void query() throws Exception {
        AuthorityAttribute attribute = new AuthorityAttribute();
        attribute.setName(DEFAULT_NAME);
        attribute.setCode(DEFAULT_CODE);
        authorityService.create(attribute);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/authority")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryById() throws Exception {
        AuthorityAttribute attribute = new AuthorityAttribute();
        attribute.setName(DEFAULT_NAME);
        attribute.setCode(DEFAULT_CODE);
        Authority authority = authorityService.create(attribute);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/authority/" + authority.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryByPageable() throws Exception {
        AuthorityAttribute attribute = new AuthorityAttribute();
        attribute.setName(DEFAULT_NAME);
        attribute.setCode(DEFAULT_CODE);
        authorityService.create(attribute);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/authority/pageable")
                .param("page", "0")
                .param("size", "20")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void update() throws Exception {
        AuthorityAttribute attribute = new AuthorityAttribute();
        attribute.setName(DEFAULT_NAME);
        attribute.setCode(DEFAULT_CODE);
        Authority prevAuthority = authorityService.create(attribute);
        List<Authority> prevAll = authorityRepository.findAll();
        AuthorityIdAttribute updateattribute = new AuthorityIdAttribute();
        BeanUtils.copyProperties(prevAuthority, updateattribute);
        updateattribute.setName(UPDATE_NAME);
        updateattribute.setCode(UPDATE_CODE);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.put("/api/authority")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateattribute)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Authority> currAll = authorityRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        Authority currAuthority = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currAuthority.getId()).isEqualTo(prevAuthority.getId());
        Assertions.assertThat(currAuthority.getName()).isEqualTo(UPDATE_NAME);
        Assertions.assertThat(currAuthority.getCode()).isEqualTo(StrUtil.format("ROLE_{}", UPDATE_CODE));
        Assertions.assertThat(currAuthority.getDesc()).isEqualTo(prevAuthority.getDesc());
        Assertions.assertThat(currAuthority.getUsers()).isEqualTo(prevAuthority.getUsers());
        Assertions.assertThat(currAuthority.getPermissions()).isEqualTo(prevAuthority.getPermissions());
        Assertions.assertThat(currAuthority.getCreatedBy()).isEqualTo(prevAuthority.getCreatedBy());
        Assertions.assertThat(currAuthority.getCreatedDate()).isEqualTo(prevAuthority.getCreatedDate());
        Assertions.assertThat(currAuthority.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currAuthority.getLastModifiedDate()).isNotNull();
    }

    @Test
    void updatePermissions() throws Exception {
        AuthorityAttribute attribute = new AuthorityAttribute();
        attribute.setName(DEFAULT_NAME);
        attribute.setCode(DEFAULT_CODE);
        Authority prevAuthority = authorityService.create(attribute);
        List<Authority> prevAll = authorityRepository.findAll();
        PermissionAttribute permissionattribute1 = new PermissionAttribute();
        permissionattribute1.setName(RandomUtil.randomString(12));
        Permission permission1 = permissionService.create(permissionattribute1);
        PermissionAttribute permissionattribute2 = new PermissionAttribute();
        permissionattribute2.setName(RandomUtil.randomString(12));
        Permission permission2 = permissionService.create(permissionattribute2);
        Set<Long> permissionIds = Sets.newHashSet(permission1.getId(), permission2.getId());
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.put("/api/authority/permissions/" + prevAuthority.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(permissionIds)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Authority> currAll = authorityRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        Authority currAuthority = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currAuthority.getId()).isEqualTo(prevAuthority.getId());
        Assertions.assertThat(currAuthority.getName()).isEqualTo(prevAuthority.getName());
        Assertions.assertThat(currAuthority.getCode()).isEqualTo(prevAuthority.getCode());
        Assertions.assertThat(currAuthority.getDesc()).isEqualTo(prevAuthority.getDesc());
        Assertions.assertThat(currAuthority.getUsers()).isEqualTo(prevAuthority.getUsers());
        Assertions.assertThat(currAuthority.getPermissions()).hasSize(permissionIds.size());
        Assertions.assertThat(currAuthority.getCreatedBy()).isEqualTo(prevAuthority.getCreatedBy());
        Assertions.assertThat(currAuthority.getCreatedDate()).isEqualTo(prevAuthority.getCreatedDate());
        Assertions.assertThat(currAuthority.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currAuthority.getLastModifiedDate()).isNotNull();
    }

    @Test
    void delete() throws Exception {
        AuthorityAttribute attribute1 = new AuthorityAttribute();
        attribute1.setName(RandomUtil.randomString(12));
        attribute1.setCode(RandomUtil.randomStringUpper(12));
        Authority authority1 = authorityService.create(attribute1);
        AuthorityAttribute attribute2 = new AuthorityAttribute();
        attribute2.setName(RandomUtil.randomString(12));
        attribute2.setCode(RandomUtil.randomStringUpper(12));
        Authority authority2 = authorityService.create(attribute2);
        List<Authority> prevAll = authorityRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/api/authority/" + authority1.getId() + "," + authority2.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Authority> currAll = authorityRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() - 2);
    }
}
