package org.umi.boot.web.rest;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.umi.boot.domain.User;
import org.umi.boot.domain.UserInfo;
import org.umi.boot.repository.UserRepository;
import org.umi.boot.service.UserService;
import org.umi.boot.web.rest.manage.UserAttribute;
import org.umi.boot.web.rest.manage.UserIdAttribute;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "admin", password = "123456")
class UserResourceTest {

    private static final String DEFAULT_USERNAME = "Test" + RandomUtil.randomNumbers(10);
    private static final String DEFAULT_NICKNAME = "测试用户" + IdUtil.randomUUID();
    private static final String UPDATE_NICKNAME = "测试用户" + IdUtil.randomUUID();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void create() throws Exception {
        Set<Long> authoritieIds = Sets.newHashSet(10000L);
        UserAttribute attribute = new UserAttribute();
        attribute.setUsername(DEFAULT_USERNAME);
        attribute.setNickname(DEFAULT_NICKNAME);
        attribute.setSexId(10001L);
        attribute.setAuthoritieIds(authoritieIds);
        List<User> prevAll = userRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(attribute)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<User> currAll = userRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() + 1);
        User user = currAll.get(currAll.size() - 1);
        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getUsername()).isEqualTo(DEFAULT_USERNAME);
        Assertions.assertThat(user.getEmail()).isNull();
        Assertions.assertThat(user.getMobilePhone()).isNull();
        Assertions.assertThat(passwordEncoder.matches("123456", user.getPassword())).isTrue();
        Assertions.assertThat(user.getAuthorities()).hasSize(authoritieIds.size());
        Assertions.assertThat(user.getCreatedBy()).isNotNull();
        Assertions.assertThat(user.getCreatedDate()).isNotNull();
        Assertions.assertThat(user.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(user.getLastModifiedDate()).isNotNull();
        UserInfo info = user.getInfo();
        Assertions.assertThat(info.getId()).isNotNull();
        Assertions.assertThat(info.getNickname()).isEqualTo(DEFAULT_NICKNAME);
        Assertions.assertThat(info.getRealname()).isNull();
        Assertions.assertThat(info.getSex()).isNotNull();
        Assertions.assertThat(info.getBirthday()).isNull();
        Assertions.assertThat(info.getIdCard()).isNull();
        Assertions.assertThat(info.getCreatedBy()).isNotNull();
        Assertions.assertThat(info.getCreatedDate()).isNotNull();
        Assertions.assertThat(info.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(info.getLastModifiedDate()).isNotNull();
    }

    @Test
    void query() throws Exception {
        Set<Long> authoritieIds = Sets.newHashSet(10000L);
        UserAttribute attribute = new UserAttribute();
        attribute.setUsername(DEFAULT_USERNAME);
        attribute.setNickname(DEFAULT_NICKNAME);
        attribute.setSexId(10001L);
        attribute.setAuthoritieIds(authoritieIds);
        userService.create(attribute);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/user")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryById() throws Exception {
        Set<Long> authoritieIds = Sets.newHashSet(10000L);
        UserAttribute attribute = new UserAttribute();
        attribute.setUsername(DEFAULT_USERNAME);
        attribute.setNickname(DEFAULT_NICKNAME);
        attribute.setSexId(10001L);
        attribute.setAuthoritieIds(authoritieIds);
        User user = userService.create(attribute);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/user/" + user.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryByPageable() throws Exception {
        Set<Long> authoritieIds = Sets.newHashSet(10000L);
        UserAttribute attribute = new UserAttribute();
        attribute.setUsername(DEFAULT_USERNAME);
        attribute.setNickname(DEFAULT_NICKNAME);
        attribute.setSexId(10001L);
        attribute.setAuthoritieIds(authoritieIds);
        userService.create(attribute);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/user/pageable")
                .param("page", "0")
                .param("size", "20")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void update() throws Exception {
        Set<Long> authoritieIds = Sets.newHashSet(10000L);
        UserAttribute attribute = new UserAttribute();
        attribute.setUsername(DEFAULT_USERNAME);
        attribute.setNickname(DEFAULT_NICKNAME);
        attribute.setSexId(10001L);
        attribute.setAuthoritieIds(authoritieIds);
        User prevUser = userService.create(attribute);
        UserInfo prevInfo = prevUser.getInfo();
        UserIdAttribute updateattribute = new UserIdAttribute();
        BeanUtils.copyProperties(prevUser, updateattribute);
        BeanUtils.copyProperties(prevInfo, updateattribute);
        updateattribute.setId(prevUser.getId());
        updateattribute.setNickname(UPDATE_NICKNAME);
        updateattribute.setSexId(10002L);
        updateattribute.setAuthoritieIds(authoritieIds);
        List<User> prevAll = userRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.put("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateattribute)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<User> currAll = userRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        User currUser = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currUser.getId()).isEqualTo(prevUser.getId());
        Assertions.assertThat(currUser.getUsername()).isEqualTo(prevUser.getUsername());
        Assertions.assertThat(currUser.getEmail()).isEqualTo(prevUser.getEmail());
        Assertions.assertThat(currUser.getMobilePhone()).isEqualTo(prevUser.getMobilePhone());
        Assertions.assertThat(currUser.getPassword()).isEqualTo(prevUser.getPassword());
        Assertions.assertThat(currUser.getAuthorities()).isEqualTo(prevUser.getAuthorities());
        Assertions.assertThat(currUser.getCreatedBy()).isEqualTo(prevUser.getCreatedBy());
        Assertions.assertThat(currUser.getCreatedDate()).isEqualTo(prevUser.getCreatedDate());
        Assertions.assertThat(currUser.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currUser.getLastModifiedDate()).isNotNull();
        UserInfo currInfo = currUser.getInfo();
        Assertions.assertThat(currInfo.getId()).isEqualTo(prevInfo.getId());
        Assertions.assertThat(currInfo.getNickname()).isEqualTo(UPDATE_NICKNAME);
        Assertions.assertThat(currInfo.getRealname()).isEqualTo(prevInfo.getRealname());
        Assertions.assertThat(currInfo.getSex()).isNotNull();
        Assertions.assertThat(currInfo.getBirthday()).isEqualTo(prevInfo.getBirthday());
        Assertions.assertThat(currInfo.getIdCard()).isEqualTo(prevInfo.getIdCard());
        Assertions.assertThat(currInfo.getCreatedBy()).isEqualTo(prevInfo.getCreatedBy());
        Assertions.assertThat(currInfo.getCreatedDate()).isEqualTo(prevInfo.getCreatedDate());
        Assertions.assertThat(currInfo.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currInfo.getLastModifiedDate()).isNotNull();
    }

    @Test
    void enable() throws Exception {
        Set<Long> authoritieIds = Sets.newHashSet(10000L);
        UserAttribute attribute1 = new UserAttribute();
        attribute1.setUsername("Test" + RandomUtil.randomNumbers(10));
        attribute1.setNickname(DEFAULT_NICKNAME);
        attribute1.setSexId(10001L);
        attribute1.setAuthoritieIds(authoritieIds);
        User user1 = userService.create(attribute1);
        UserAttribute attribute2 = new UserAttribute();
        attribute2.setUsername("Test" + RandomUtil.randomNumbers(10));
        attribute2.setNickname(DEFAULT_NICKNAME);
        attribute2.setSexId(10002L);
        attribute2.setAuthoritieIds(authoritieIds);
        User user2 = userService.create(attribute2);
        List<User> prevAll = userRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.put("/api/user/enable/" + user1.getId() + "," + user2.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<User> currAll = userRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
    }

    @Test
    void disable() throws Exception {
        Set<Long> authoritieIds = Sets.newHashSet(10000L);
        UserAttribute attribute1 = new UserAttribute();
        attribute1.setUsername("Test" + RandomUtil.randomNumbers(10));
        attribute1.setNickname(DEFAULT_NICKNAME);
        attribute1.setSexId(10001L);
        attribute1.setAuthoritieIds(authoritieIds);
        User user1 = userService.create(attribute1);
        UserAttribute attribute2 = new UserAttribute();
        attribute2.setUsername("Test" + RandomUtil.randomNumbers(10));
        attribute2.setNickname(DEFAULT_NICKNAME);
        attribute2.setSexId(10002L);
        attribute2.setAuthoritieIds(authoritieIds);
        User user2 = userService.create(attribute2);
        List<User> prevAll = userRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.put("/api/user/disable/" + user1.getId() + "," + user2.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<User> currAll = userRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
    }

    @Test
    void resetPassword() throws Exception {
        Set<Long> authoritieIds = Sets.newHashSet(10000L);
        UserAttribute attribute1 = new UserAttribute();
        attribute1.setUsername("Test" + RandomUtil.randomNumbers(10));
        attribute1.setNickname(DEFAULT_NICKNAME);
        attribute1.setSexId(10001L);
        attribute1.setAuthoritieIds(authoritieIds);
        User user1 = userService.create(attribute1);
        UserAttribute attribute2 = new UserAttribute();
        attribute2.setUsername("Test" + RandomUtil.randomNumbers(10));
        attribute2.setNickname(DEFAULT_NICKNAME);
        attribute2.setSexId(10002L);
        attribute2.setAuthoritieIds(authoritieIds);
        User user2 = userService.create(attribute2);
        List<User> prevAll = userRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.put("/api/user/reset/password/" + user1.getId() + "," + user2.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<User> currAll = userRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
    }

    @Test
    void delete() throws Exception {
        Set<Long> authoritieIds = Sets.newHashSet(10000L);
        UserAttribute attribute1 = new UserAttribute();
        attribute1.setUsername("Test" + RandomUtil.randomNumbers(10));
        attribute1.setNickname(DEFAULT_NICKNAME);
        attribute1.setSexId(10001L);
        attribute1.setAuthoritieIds(authoritieIds);
        User user1 = userService.create(attribute1);
        UserAttribute attribute2 = new UserAttribute();
        attribute2.setUsername("Test" + RandomUtil.randomNumbers(10));
        attribute2.setNickname(DEFAULT_NICKNAME);
        attribute2.setSexId(10002L);
        attribute2.setAuthoritieIds(authoritieIds);
        User user2 = userService.create(attribute2);
        List<User> prevAll = userRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/api/user/" + user1.getId() + "," + user2.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<User> currAll = userRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() - 2);
    }
}
