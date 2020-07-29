package org.umi.boot.web.rest;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.umi.boot.commons.utils.LevelUtil;
import org.umi.boot.domain.Permission;
import org.umi.boot.repository.PermissionRepository;
import org.umi.boot.service.PermissionService;
import org.umi.boot.web.rest.manage.PermissionManage;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PermissionResourceTest {

    private static final String DEFAULT_NAME = "测试菜单" + IdUtil.randomUUID();
    private static final String UPDATE_NAME = "测试菜单" + IdUtil.randomUUID();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionService permissionService;

    @Test
    void create() throws Exception {
        List<Permission> prevAll = permissionRepository.findAll();
        PermissionManage manage = new PermissionManage();
        manage.setName(DEFAULT_NAME);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/api/permission")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manage)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Permission> currAll = permissionRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() + 1);
        Permission permission = currAll.get(currAll.size() - 1);
        Assertions.assertThat(permission.getId()).isNotNull();
        Assertions.assertThat(permission.getPid()).isEqualTo(0);
        Assertions.assertThat(permission.getLevel()).isEqualTo(StringUtils.join(LevelUtil.ROOT, LevelUtil.SUFFIX));
        Assertions.assertThat(permission.getName()).isEqualTo(DEFAULT_NAME);
        Assertions.assertThat(permission.getPath()).isNull();
        Assertions.assertThat(permission.getIcon()).isNull();
        Assertions.assertThat(permission.getSeq()).isEqualTo(0);
        Assertions.assertThat(permission.getDesc()).isNull();
        Assertions.assertThat(permission.getAuthorities()).isEmpty();
        Assertions.assertThat(permission.getResources()).isEmpty();
        Assertions.assertThat(permission.getCreatedBy()).isNotNull();
        Assertions.assertThat(permission.getCreatedDate()).isNotNull();
        Assertions.assertThat(permission.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(permission.getLastModifiedDate()).isNotNull();
    }

    @Test
    void query() throws Exception {
        PermissionManage manage = new PermissionManage();
        manage.setName(DEFAULT_NAME);
        permissionService.create(manage);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/permission")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryToTree() throws Exception {
        PermissionManage manage = new PermissionManage();
        manage.setName(DEFAULT_NAME);
        Permission permission = permissionService.create(manage);
        PermissionManage manage1 = new PermissionManage();
        manage1.setPid(permission.getId());
        manage1.setName(RandomUtil.randomString(12));
        permissionService.create(manage1);
        PermissionManage manage2 = new PermissionManage();
        manage2.setPid(permission.getId());
        manage2.setName(RandomUtil.randomString(12));
        permissionService.create(manage2);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/permission/tree")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryById() throws Exception {
        PermissionManage manage = new PermissionManage();
        manage.setName(DEFAULT_NAME);
        Permission permission = permissionService.create(manage);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/permission/" + permission.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryToChildren() throws Exception {
        PermissionManage manage = new PermissionManage();
        manage.setName(DEFAULT_NAME);
        Permission permission = permissionService.create(manage);
        PermissionManage manage1 = new PermissionManage();
        manage1.setPid(permission.getId());
        manage1.setName(RandomUtil.randomString(12));
        permissionService.create(manage1);
        PermissionManage manage2 = new PermissionManage();
        manage2.setPid(permission.getId());
        manage2.setName(RandomUtil.randomString(12));
        permissionService.create(manage2);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/permission/children/" + permission.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryByPageable() throws Exception {
        PermissionManage manage = new PermissionManage();
        manage.setName(DEFAULT_NAME);
        permissionService.create(manage);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/permission/pageable")
                .param("page", "0")
                .param("size", "20")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void update() throws Exception {
        PermissionManage manage = new PermissionManage();
        manage.setName(DEFAULT_NAME);
        Permission prevPermission = permissionService.create(manage);
        PermissionManage updateManage = new PermissionManage();
        BeanUtils.copyProperties(prevPermission, updateManage);
        updateManage.setName(UPDATE_NAME);
        List<Permission> prevAll = permissionRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.put("/api/permission")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateManage)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Permission> currAll = permissionRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        Permission currPermission = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currPermission.getId()).isEqualTo(prevPermission.getId());
        Assertions.assertThat(currPermission.getPid()).isEqualTo(prevPermission.getPid());
        Assertions.assertThat(currPermission.getLevel()).isEqualTo(prevPermission.getLevel());
        Assertions.assertThat(currPermission.getName()).isEqualTo(UPDATE_NAME);
        Assertions.assertThat(currPermission.getPath()).isEqualTo(prevPermission.getPath());
        Assertions.assertThat(currPermission.getIcon()).isEqualTo(prevPermission.getIcon());
        Assertions.assertThat(currPermission.getSeq()).isEqualTo(prevPermission.getSeq());
        Assertions.assertThat(currPermission.getDesc()).isEqualTo(prevPermission.getDesc());
        Assertions.assertThat(currPermission.getAuthorities()).isEqualTo(prevPermission.getAuthorities());
        Assertions.assertThat(currPermission.getResources()).isEqualTo(prevPermission.getResources());
        Assertions.assertThat(currPermission.getCreatedBy()).isEqualTo(prevPermission.getCreatedBy());
        Assertions.assertThat(currPermission.getCreatedDate()).isEqualTo(prevPermission.getCreatedDate());
        Assertions.assertThat(currPermission.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currPermission.getLastModifiedDate()).isNotNull();
    }

    @Test
    void delete() throws Exception {
        PermissionManage manage1 = new PermissionManage();
        manage1.setName(RandomUtil.randomString(12));
        Permission permission1 = permissionService.create(manage1);
        PermissionManage manage2 = new PermissionManage();
        manage2.setName(RandomUtil.randomString(12));
        Permission permission2 = permissionService.create(manage2);
        List<Permission> prevAll = permissionRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/api/permission/" + permission1.getId() + "," + permission2.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Permission> currAll = permissionRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() - 2);
    }
}
