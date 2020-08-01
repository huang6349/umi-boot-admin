package org.umi.boot.web.rest;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.umi.boot.domain.Resource;
import org.umi.boot.repository.ResourceRepository;
import org.umi.boot.service.ResourceService;
import org.umi.boot.web.rest.manage.ResourceAttribute;
import org.umi.boot.web.rest.manage.ResourceIdAttribute;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "admin", password = "123456")
class ResourceResourceTest {

    private static final String DEFAULT_PATTERN = "测试菜单资源" + IdUtil.randomUUID();
    private static final String UPDATE_PATTERN = "测试菜单资源" + IdUtil.randomUUID();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceService resourceService;

    @Test
    void create() throws Exception {
        ResourceAttribute attribute = new ResourceAttribute();
        attribute.setPermissionId(10010L);
        attribute.setPattern(DEFAULT_PATTERN);
        attribute.setMethodId(10101L);
        List<Resource> prevAll = resourceRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/api/resource")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(attribute)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Resource> currAll = resourceRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() + 1);
        Resource resource = currAll.get(currAll.size() - 1);
        Assertions.assertThat(resource.getId()).isNotNull();
        Assertions.assertThat(resource.getPattern()).isEqualTo(DEFAULT_PATTERN);
        Assertions.assertThat(resource.getMethod()).isNotNull();
        Assertions.assertThat(resource.getDesc()).isNull();
        Assertions.assertThat(resource.getPermission()).isNotNull();
        Assertions.assertThat(resource.getCreatedBy()).isNotNull();
        Assertions.assertThat(resource.getCreatedDate()).isNotNull();
        Assertions.assertThat(resource.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(resource.getLastModifiedDate()).isNotNull();
    }

    @Test
    void query() throws Exception {
        ResourceAttribute attribute = new ResourceAttribute();
        attribute.setPermissionId(10010L);
        attribute.setPattern(DEFAULT_PATTERN);
        attribute.setMethodId(10101L);
        resourceService.create(attribute);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/resource")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryById() throws Exception {
        ResourceAttribute attribute = new ResourceAttribute();
        attribute.setPermissionId(10010L);
        attribute.setPattern(DEFAULT_PATTERN);
        attribute.setMethodId(10101L);
        Resource resource = resourceService.create(attribute);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/resource/" + resource.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryByPageable() throws Exception {
        ResourceAttribute attribute = new ResourceAttribute();
        attribute.setPermissionId(10010L);
        attribute.setPattern(DEFAULT_PATTERN);
        attribute.setMethodId(10101L);
        resourceService.create(attribute);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/resource/pageable")
                .param("page", "0")
                .param("size", "20")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void update() throws Exception {
        ResourceAttribute attribute = new ResourceAttribute();
        attribute.setPermissionId(10010L);
        attribute.setPattern(DEFAULT_PATTERN);
        attribute.setMethodId(10101L);
        Resource prevResource = resourceService.create(attribute);
        ResourceIdAttribute updateattribute = new ResourceIdAttribute();
        BeanUtils.copyProperties(prevResource, updateattribute);
        updateattribute.setPermissionId(prevResource.getPermission().getId());
        updateattribute.setPattern(UPDATE_PATTERN);
        updateattribute.setMethodId(prevResource.getMethod().getId());
        List<Resource> prevAll = resourceRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.put("/api/resource")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateattribute)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Resource> currAll = resourceRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        Resource currResource = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currResource.getId()).isEqualTo(prevResource.getId());
        Assertions.assertThat(currResource.getPattern()).isEqualTo(UPDATE_PATTERN);
        Assertions.assertThat(currResource.getMethod()).isEqualTo(prevResource.getMethod());
        Assertions.assertThat(currResource.getDesc()).isEqualTo(prevResource.getDesc());
        Assertions.assertThat(currResource.getPermission()).isEqualTo(prevResource.getPermission());
        Assertions.assertThat(currResource.getCreatedBy()).isEqualTo(prevResource.getCreatedBy());
        Assertions.assertThat(currResource.getCreatedDate()).isEqualTo(prevResource.getCreatedDate());
        Assertions.assertThat(currResource.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currResource.getLastModifiedDate()).isNotNull();
    }

    @Test
    void delete() throws Exception {
        ResourceAttribute attribute1 = new ResourceAttribute();
        attribute1.setPermissionId(10010L);
        attribute1.setPattern(RandomUtil.randomString(12));
        attribute1.setMethodId(10101L);
        Resource resource1 = resourceService.create(attribute1);
        ResourceAttribute attribute2 = new ResourceAttribute();
        attribute2.setPermissionId(10010L);
        attribute2.setPattern(RandomUtil.randomString(12));
        attribute2.setMethodId(10101L);
        Resource resource2 = resourceService.create(attribute2);
        List<Resource> prevAll = resourceRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/api/resource/" + resource1.getId() + "," + resource2.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Resource> currAll = resourceRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() - 2);
    }
}
