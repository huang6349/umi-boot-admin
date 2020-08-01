package org.umi.boot.web.rest;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.umi.boot.domain.File;
import org.umi.boot.repository.FileRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "admin", password = "123456")
class FileResourceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FileRepository fileRepository;

    @Test
    void query() throws Exception {
        File file = new File();
        file.setName(RandomUtil.randomString(12));
        file.setUrl(RandomUtil.randomString(12));
        fileRepository.save(file);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/file")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryById() throws Exception {
        File file = new File();
        file.setName(RandomUtil.randomString(12));
        file.setUrl(RandomUtil.randomString(12));
        File newFile = fileRepository.save(file);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/file/" + newFile.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryByPageable() throws Exception {
        File file = new File();
        file.setName(RandomUtil.randomString(12));
        file.setUrl(RandomUtil.randomString(12));
        fileRepository.save(file);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/file/pageable")
                .param("page", "0")
                .param("size", "20")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }
}
