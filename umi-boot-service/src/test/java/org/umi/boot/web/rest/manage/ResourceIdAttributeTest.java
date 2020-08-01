package org.umi.boot.web.rest.manage;

import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.umi.boot.domain.Resource;

class ResourceIdAttributeTest {

    @Test
    void adapt() {
        Resource resource = new Resource();
        resource.setId(RandomUtil.randomLong());
        resource.setPattern(RandomUtil.randomString(12));
        resource.setDesc(RandomUtil.randomString(12));
        ResourceIdAttribute attribute = new ResourceIdAttribute();
        attribute.setId(resource.getId());
        attribute.setPattern(RandomUtil.randomString(12));
        attribute.setDesc(RandomUtil.randomString(12));
        Resource newResource = ResourceIdAttribute.adapt(attribute, resource);
        Assertions.assertThat(newResource.getId()).isEqualTo(resource.getId());
        Assertions.assertThat(newResource.getPattern()).isEqualTo(attribute.getPattern());
        Assertions.assertThat(newResource.getDesc()).isEqualTo(attribute.getDesc());
        Assertions.assertThat(newResource.getCreatedBy()).isEqualTo(resource.getCreatedBy());
        Assertions.assertThat(newResource.getCreatedDate()).isEqualTo(resource.getCreatedDate());
        Assertions.assertThat(newResource.getLastModifiedBy()).isEqualTo(resource.getLastModifiedBy());
        Assertions.assertThat(newResource.getLastModifiedDate()).isEqualTo(resource.getLastModifiedDate());
        Assertions.assertThat(newResource.getState()).isEqualTo(resource.getState());
    }
}
