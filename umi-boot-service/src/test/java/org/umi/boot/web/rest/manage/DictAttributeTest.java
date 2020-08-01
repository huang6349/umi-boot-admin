package org.umi.boot.web.rest.manage;

import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.domain.Dict;

class DictAttributeTest {

    @Test
    void adapt() {
        DictAttribute attribute = new DictAttribute();
        attribute.setPid(RandomUtil.randomLong());
        attribute.setName(RandomUtil.randomString(12));
        attribute.setCode(RandomUtil.randomString(12));
        attribute.setData(RandomUtil.randomString(12));
        attribute.setDesc(RandomUtil.randomString(12));
        Dict dict = DictAttribute.adapt(attribute);
        Assertions.assertThat(dict.getId()).isNull();
        Assertions.assertThat(dict.getPid()).isEqualTo(attribute.getPid());
        Assertions.assertThat(dict.getLevel()).isNull();
        Assertions.assertThat(dict.getName()).isEqualTo(attribute.getName());
        Assertions.assertThat(dict.getCode()).isEqualTo(attribute.getCode());
        Assertions.assertThat(dict.getData()).isEqualTo(attribute.getData());
        Assertions.assertThat(dict.getDesc()).isEqualTo(attribute.getDesc());
        Assertions.assertThat(dict.getCreatedBy()).isNull();
        Assertions.assertThat(dict.getCreatedDate()).isNotNull();
        Assertions.assertThat(dict.getLastModifiedBy()).isNull();
        Assertions.assertThat(dict.getLastModifiedDate()).isNotNull();
        Assertions.assertThat(dict.getState()).isEqualTo(GlobalConstants.DATA_NORMAL_STATE);
    }
}
