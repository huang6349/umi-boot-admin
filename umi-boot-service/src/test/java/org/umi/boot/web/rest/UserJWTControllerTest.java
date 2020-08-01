package org.umi.boot.web.rest;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.umi.boot.domain.User;
import org.umi.boot.service.UserService;
import org.umi.boot.web.rest.manage.UserAttribute;
import org.umi.boot.web.rest.query.LoginQuery;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserJWTControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Test
    void authorize() throws Exception {
        Set<Long> authoritieIds = Sets.newHashSet(10000L);
        UserAttribute attribute = new UserAttribute();
        attribute.setUsername(RandomUtil.randomString(12));
        attribute.setNickname(RandomUtil.randomString(12));
        attribute.setSexId(10001L);
        attribute.setAuthoritieIds(authoritieIds);
        User user = userService.create(attribute);
        LoginQuery query = new LoginQuery();
        query.setUsername(user.getUsername());
        query.setPassword("123456");
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/api/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(query)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }
}
