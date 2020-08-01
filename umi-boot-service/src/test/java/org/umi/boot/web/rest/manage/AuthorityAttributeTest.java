package org.umi.boot.web.rest.manage;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.domain.Authority;

class AuthorityAttributeTest {

    @Test
    void adapt() {
        AuthorityAttribute attribute = new AuthorityAttribute();
        attribute.setName("测试角色" + IdUtil.randomUUID());
        attribute.setCode(RandomUtil.randomStringUpper(12));
        attribute.setDesc(RandomUtil.randomStringUpper(12));
        Authority authority = AuthorityAttribute.adapt(attribute);
        Assertions.assertThat(authority.getId()).isNull();
        Assertions.assertThat(authority.getName()).isEqualTo(attribute.getName());
        Assertions.assertThat(authority.getCode()).isEqualTo(attribute.getCode());
        Assertions.assertThat(authority.getDesc()).isEqualTo(attribute.getDesc());
        Assertions.assertThat(authority.getUsers()).hasSize(0);
        Assertions.assertThat(authority.getPermissions()).hasSize(0);
        Assertions.assertThat(authority.getCreatedBy()).isNull();
        Assertions.assertThat(authority.getCreatedDate()).isNotNull();
        Assertions.assertThat(authority.getLastModifiedBy()).isNull();
        Assertions.assertThat(authority.getLastModifiedDate()).isNotNull();
        Assertions.assertThat(authority.getState()).isEqualTo(GlobalConstants.DATA_NORMAL_STATE);
    }
}
