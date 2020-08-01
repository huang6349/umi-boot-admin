package org.umi.boot.web.rest.manage;

import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.domain.Resource;

class ResourceAttributeTest {

    @Test
    void adapt() {
        ResourceAttribute attribute = new ResourceAttribute();
        attribute.setPermissionId(RandomUtil.randomLong());
        attribute.setPattern(RandomUtil.randomString(12));
        attribute.setMethodId(RandomUtil.randomLong());
        attribute.setDesc(RandomUtil.randomString(12));
        Resource resource = ResourceAttribute.adapt(attribute);
        Assertions.assertThat(resource.getId()).isNull();
        Assertions.assertThat(resource.getPermission()).isNull();
        Assertions.assertThat(resource.getPattern()).isEqualTo(attribute.getPattern());
        Assertions.assertThat(resource.getMethod()).isNull();
        Assertions.assertThat(resource.getDesc()).isEqualTo(attribute.getDesc());
        Assertions.assertThat(resource.getCreatedBy()).isNull();
        Assertions.assertThat(resource.getCreatedDate()).isNotNull();
        Assertions.assertThat(resource.getLastModifiedBy()).isNull();
        Assertions.assertThat(resource.getLastModifiedDate()).isNotNull();
        Assertions.assertThat(resource.getState()).isEqualTo(GlobalConstants.DATA_NORMAL_STATE);
    }
}
