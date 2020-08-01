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
import org.umi.boot.domain.Dict;
import org.umi.boot.repository.DictRepository;
import org.umi.boot.service.DictService;
import org.umi.boot.web.rest.manage.DictAttribute;
import org.umi.boot.web.rest.manage.DictIdAttribute;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class DictResourceTest {

    private static final String DEFAULT_NAME = "测试字典" + IdUtil.randomUUID();
    private static final String DEFAULT_CODE = "测试唯一标识码" + IdUtil.randomUUID();
    private static final String UPDATE_NAME = "测试字典" + IdUtil.randomUUID();
    private static final String UPDATE_CODE = "测试唯一标识码" + IdUtil.randomUUID();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DictRepository dictRepository;

    @Autowired
    private DictService dictService;

    @Test
    void create() throws Exception {
        List<Dict> prevAll = dictRepository.findAll();
        DictAttribute attribute = new DictAttribute();
        attribute.setName(DEFAULT_NAME);
        attribute.setCode(DEFAULT_CODE);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/api/dict")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(attribute)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Dict> currAll = dictRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() + 1);
        Dict dict = currAll.get(currAll.size() - 1);
        Assertions.assertThat(dict.getId()).isNotNull();
        Assertions.assertThat(dict.getPid()).isEqualTo(0);
        Assertions.assertThat(dict.getLevel()).isEqualTo(StringUtils.join(LevelUtil.ROOT, LevelUtil.SUFFIX));
        Assertions.assertThat(dict.getName()).isEqualTo(DEFAULT_NAME);
        Assertions.assertThat(dict.getCode()).isEqualTo(DEFAULT_CODE);
        Assertions.assertThat(dict.getData()).isNull();
        Assertions.assertThat(dict.getDesc()).isNull();
        Assertions.assertThat(dict.getCreatedBy()).isNotNull();
        Assertions.assertThat(dict.getCreatedDate()).isNotNull();
        Assertions.assertThat(dict.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(dict.getLastModifiedDate()).isNotNull();
    }

    @Test
    void query() throws Exception {
        DictAttribute attribute = new DictAttribute();
        attribute.setName(DEFAULT_NAME);
        attribute.setCode(DEFAULT_CODE);
        dictService.create(attribute);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/dict")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryToTree() throws Exception {
        DictAttribute attribute = new DictAttribute();
        attribute.setName(DEFAULT_NAME);
        attribute.setCode(DEFAULT_CODE);
        Dict dict = dictService.create(attribute);
        DictAttribute attribute1 = new DictAttribute();
        attribute1.setPid(dict.getId());
        attribute1.setName(RandomUtil.randomString(12));
        attribute1.setData(RandomUtil.randomString(12));
        dictService.create(attribute1);
        DictAttribute attribute2 = new DictAttribute();
        attribute2.setPid(dict.getId());
        attribute2.setName(RandomUtil.randomString(12));
        attribute2.setData(RandomUtil.randomString(12));
        dictService.create(attribute2);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/dict/tree")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryById() throws Exception {
        DictAttribute attribute = new DictAttribute();
        attribute.setName(DEFAULT_NAME);
        attribute.setCode(DEFAULT_CODE);
        Dict dict = dictService.create(attribute);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/dict/" + dict.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryToChildren() throws Exception {
        DictAttribute attribute = new DictAttribute();
        attribute.setName(DEFAULT_NAME);
        attribute.setCode(DEFAULT_CODE);
        Dict dict = dictService.create(attribute);
        DictAttribute attribute1 = new DictAttribute();
        attribute1.setPid(dict.getId());
        attribute1.setName(RandomUtil.randomString(12));
        attribute1.setData(RandomUtil.randomString(12));
        dictService.create(attribute1);
        DictAttribute attribute2 = new DictAttribute();
        attribute2.setPid(dict.getId());
        attribute2.setName(RandomUtil.randomString(12));
        attribute2.setData(RandomUtil.randomString(12));
        dictService.create(attribute2);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/dict/children/" + dict.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void queryByPageable() throws Exception {
        DictAttribute attribute = new DictAttribute();
        attribute.setName(DEFAULT_NAME);
        attribute.setCode(DEFAULT_CODE);
        dictService.create(attribute);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/api/dict/pageable")
                .param("page", "0")
                .param("size", "20")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void update() throws Exception {
        DictAttribute attribute = new DictAttribute();
        attribute.setName(DEFAULT_NAME);
        attribute.setCode(DEFAULT_CODE);
        Dict prevDict = dictService.create(attribute);
        DictIdAttribute updateattribute = new DictIdAttribute();
        BeanUtils.copyProperties(prevDict, updateattribute);
        updateattribute.setName(UPDATE_NAME);
        updateattribute.setCode(UPDATE_CODE);
        List<Dict> prevAll = dictRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.put("/api/dict")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateattribute)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Dict> currAll = dictRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        Dict currDict = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currDict.getId()).isEqualTo(prevDict.getId());
        Assertions.assertThat(currDict.getPid()).isEqualTo(prevDict.getPid());
        Assertions.assertThat(currDict.getLevel()).isEqualTo(prevDict.getLevel());
        Assertions.assertThat(currDict.getName()).isEqualTo(UPDATE_NAME);
        Assertions.assertThat(currDict.getCode()).isEqualTo(UPDATE_CODE);
        Assertions.assertThat(currDict.getData()).isEqualTo(prevDict.getData());
        Assertions.assertThat(currDict.getDesc()).isEqualTo(prevDict.getDesc());
        Assertions.assertThat(currDict.getCreatedBy()).isEqualTo(prevDict.getCreatedBy());
        Assertions.assertThat(currDict.getCreatedDate()).isEqualTo(prevDict.getCreatedDate());
        Assertions.assertThat(currDict.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currDict.getLastModifiedDate()).isNotNull();
    }

    @Test
    void delete() throws Exception {
        DictAttribute attribute1 = new DictAttribute();
        attribute1.setName(RandomUtil.randomString(12));
        attribute1.setCode(RandomUtil.randomString(12));
        Dict dict1 = dictService.create(attribute1);
        DictAttribute attribute2 = new DictAttribute();
        attribute2.setName(RandomUtil.randomString(12));
        attribute2.setCode(RandomUtil.randomString(12));
        Dict dict2 = dictService.create(attribute2);
        List<Dict> prevAll = dictRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/api/dict/" + dict1.getId() + "," + dict2.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Dict> currAll = dictRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() - 2);
    }
}
