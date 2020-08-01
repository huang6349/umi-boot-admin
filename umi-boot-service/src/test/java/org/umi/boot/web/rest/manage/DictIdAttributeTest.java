package org.umi.boot.web.rest.manage;

import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.umi.boot.domain.Dict;

class DictIdAttributeTest {

    @Test
    void adapt() {
        Dict dict = new Dict();
        dict.setId(RandomUtil.randomLong());
        dict.setPid(RandomUtil.randomLong());
        dict.setName(RandomUtil.randomString(12));
        dict.setCode(RandomUtil.randomString(12));
        dict.setData(RandomUtil.randomString(12));
        dict.setDesc(RandomUtil.randomString(12));
        DictIdAttribute attribute = new DictIdAttribute();
        attribute.setId(dict.getId());
        attribute.setPid(RandomUtil.randomLong());
        attribute.setName(RandomUtil.randomString(12));
        attribute.setCode(RandomUtil.randomString(12));
        attribute.setData(RandomUtil.randomString(12));
        attribute.setDesc(RandomUtil.randomString(12));
        Dict newDict = DictIdAttribute.adapt(attribute, dict);
        Assertions.assertThat(newDict.getId()).isEqualTo(dict.getId());
        Assertions.assertThat(newDict.getPid()).isEqualTo(attribute.getPid());
        Assertions.assertThat(newDict.getLevel()).isEqualTo(dict.getLevel());
        Assertions.assertThat(newDict.getName()).isEqualTo(attribute.getName());
        Assertions.assertThat(newDict.getCode()).isEqualTo(attribute.getCode());
        Assertions.assertThat(newDict.getData()).isEqualTo(attribute.getData());
        Assertions.assertThat(newDict.getDesc()).isEqualTo(attribute.getDesc());
        Assertions.assertThat(newDict.getCreatedBy()).isEqualTo(dict.getCreatedBy());
        Assertions.assertThat(newDict.getCreatedDate()).isEqualTo(dict.getCreatedDate());
        Assertions.assertThat(newDict.getLastModifiedBy()).isEqualTo(dict.getLastModifiedBy());
        Assertions.assertThat(newDict.getLastModifiedDate()).isEqualTo(dict.getLastModifiedDate());
        Assertions.assertThat(newDict.getState()).isEqualTo(dict.getState());
    }
}
