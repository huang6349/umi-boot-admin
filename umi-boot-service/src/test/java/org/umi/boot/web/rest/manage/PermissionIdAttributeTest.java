package org.umi.boot.web.rest.manage;

import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.umi.boot.domain.Permission;

class PermissionIdAttributeTest {

    @Test
    void adapt() {
        Permission permission = new Permission();
        permission.setId(RandomUtil.randomLong());
        permission.setPid(RandomUtil.randomLong());
        permission.setName(RandomUtil.randomString(12));
        permission.setPath(RandomUtil.randomString(12));
        permission.setIcon(RandomUtil.randomString(12));
        permission.setSeq(RandomUtil.randomInt());
        permission.setDesc(RandomUtil.randomString(12));
        PermissionIdAttribute attribute = new PermissionIdAttribute();
        attribute.setId(permission.getId());
        attribute.setPid(RandomUtil.randomLong());
        attribute.setName(RandomUtil.randomString(12));
        attribute.setPath(RandomUtil.randomString(12));
        attribute.setIcon(RandomUtil.randomString(12));
        attribute.setSeq(RandomUtil.randomInt());
        attribute.setDesc(RandomUtil.randomString(12));
        Permission newPermission = PermissionIdAttribute.adapt(attribute, permission);
        Assertions.assertThat(newPermission.getId()).isEqualTo(permission.getId());
        Assertions.assertThat(newPermission.getPid()).isEqualTo(attribute.getPid());
        Assertions.assertThat(newPermission.getLevel()).isEqualTo(permission.getLevel());
        Assertions.assertThat(newPermission.getName()).isEqualTo(attribute.getName());
        Assertions.assertThat(newPermission.getPath()).isEqualTo(attribute.getPath());
        Assertions.assertThat(newPermission.getIcon()).isEqualTo(attribute.getIcon());
        Assertions.assertThat(newPermission.getSeq()).isEqualTo(attribute.getSeq());
        Assertions.assertThat(newPermission.getDesc()).isEqualTo(attribute.getDesc());
        Assertions.assertThat(newPermission.getCreatedBy()).isEqualTo(permission.getCreatedBy());
        Assertions.assertThat(newPermission.getCreatedDate()).isEqualTo(permission.getCreatedDate());
        Assertions.assertThat(newPermission.getLastModifiedBy()).isEqualTo(permission.getLastModifiedBy());
        Assertions.assertThat(newPermission.getLastModifiedDate()).isEqualTo(permission.getLastModifiedDate());
        Assertions.assertThat(newPermission.getState()).isEqualTo(permission.getState());
    }
}
