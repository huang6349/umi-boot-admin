package org.umi.boot.web.rest.manage;

import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.domain.Permission;

class PermissionAttributeTest {

    @Test
    void adapt() {
        PermissionAttribute attribute = new PermissionAttribute();
        attribute.setPid(RandomUtil.randomLong());
        attribute.setName(RandomUtil.randomString(12));
        attribute.setPath(RandomUtil.randomString(12));
        attribute.setIcon(RandomUtil.randomString(12));
        attribute.setSeq(RandomUtil.randomInt());
        attribute.setDesc(RandomUtil.randomString(12));
        Permission permission = PermissionAttribute.adapt(attribute);
        Assertions.assertThat(permission.getId()).isNull();
        Assertions.assertThat(permission.getPid()).isEqualTo(attribute.getPid());
        Assertions.assertThat(permission.getLevel()).isNull();
        Assertions.assertThat(permission.getName()).isEqualTo(attribute.getName());
        Assertions.assertThat(permission.getPath()).isEqualTo(attribute.getPath());
        Assertions.assertThat(permission.getIcon()).isEqualTo(attribute.getIcon());
        Assertions.assertThat(permission.getSeq()).isEqualTo(attribute.getSeq());
        Assertions.assertThat(permission.getDesc()).isEqualTo(attribute.getDesc());
        Assertions.assertThat(permission.getCreatedBy()).isNull();
        Assertions.assertThat(permission.getCreatedDate()).isNotNull();
        Assertions.assertThat(permission.getLastModifiedBy()).isNull();
        Assertions.assertThat(permission.getLastModifiedDate()).isNotNull();
        Assertions.assertThat(permission.getState()).isEqualTo(GlobalConstants.DATA_NORMAL_STATE);
    }
}
