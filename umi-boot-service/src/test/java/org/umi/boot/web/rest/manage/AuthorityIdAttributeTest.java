package org.umi.boot.web.rest.manage;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.umi.boot.domain.Authority;

class AuthorityIdAttributeTest {

    @Test
    void adapt() {
        Authority authority = new Authority();
        authority.setId(RandomUtil.randomLong());
        authority.setName("测试角色" + IdUtil.randomUUID());
        authority.setCode(RandomUtil.randomStringUpper(12));
        authority.setDesc(RandomUtil.randomStringUpper(12));
        AuthorityIdAttribute attribute = new AuthorityIdAttribute();
        attribute.setId(authority.getId());
        attribute.setName("测试角色" + IdUtil.randomUUID());
        attribute.setCode(RandomUtil.randomStringUpper(12));
        attribute.setDesc(RandomUtil.randomStringUpper(12));
        Authority newAuthority = AuthorityIdAttribute.adapt(attribute, authority);
        Assertions.assertThat(newAuthority.getId()).isEqualTo(authority.getId());
        Assertions.assertThat(newAuthority.getName()).isEqualTo(attribute.getName());
        Assertions.assertThat(newAuthority.getCode()).isEqualTo(authority.getCode());
        Assertions.assertThat(newAuthority.getDesc()).isEqualTo(authority.getDesc());
        Assertions.assertThat(newAuthority.getUsers()).isEqualTo(authority.getUsers());
        Assertions.assertThat(newAuthority.getPermissions()).isEqualTo(authority.getPermissions());
        Assertions.assertThat(newAuthority.getCreatedBy()).isEqualTo(authority.getCreatedBy());
        Assertions.assertThat(newAuthority.getCreatedDate()).isEqualTo(authority.getCreatedDate());
        Assertions.assertThat(newAuthority.getLastModifiedBy()).isEqualTo(authority.getLastModifiedBy());
        Assertions.assertThat(newAuthority.getLastModifiedDate()).isEqualTo(authority.getLastModifiedDate());
        Assertions.assertThat(newAuthority.getState()).isEqualTo(authority.getState());
    }
}
