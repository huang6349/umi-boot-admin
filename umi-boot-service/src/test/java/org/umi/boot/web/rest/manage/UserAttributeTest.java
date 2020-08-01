package org.umi.boot.web.rest.manage;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Sets;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.domain.User;
import org.umi.boot.domain.UserInfo;

import java.util.Date;

class UserAttributeTest {

    @Test
    void adapt() {
        UserAttribute attribute = new UserAttribute();
        attribute.setUsername(RandomUtil.randomString(12));
        attribute.setEmail(RandomUtil.randomString(12));
        attribute.setMobilePhone(RandomUtil.randomString(12));
        attribute.setNickname(RandomUtil.randomString(12));
        attribute.setRealname(RandomUtil.randomString(12));
        attribute.setSexId(RandomUtil.randomLong());
        attribute.setBirthday(new Date());
        attribute.setIdCard(RandomUtil.randomString(12));
        attribute.setAuthoritieIds(Sets.newHashSet(RandomUtil.randomLong()));
        User user = UserAttribute.adapt(attribute);
        Assertions.assertThat(user.getId()).isNull();
        Assertions.assertThat(user.getUsername()).isEqualTo(attribute.getUsername());
        Assertions.assertThat(user.getEmail()).isEqualTo(attribute.getEmail());
        Assertions.assertThat(user.getMobilePhone()).isEqualTo(attribute.getMobilePhone());
        Assertions.assertThat(user.getPassword()).isNull();
        Assertions.assertThat(user.getInfo()).isNotNull();
        Assertions.assertThat(user.getAuthorities()).hasSize(0);
        Assertions.assertThat(user.getCreatedBy()).isNull();
        Assertions.assertThat(user.getCreatedDate()).isNotNull();
        Assertions.assertThat(user.getLastModifiedBy()).isNull();
        Assertions.assertThat(user.getLastModifiedDate()).isNotNull();
        Assertions.assertThat(user.getState()).isEqualTo(GlobalConstants.DATA_NORMAL_STATE);
        UserInfo info = user.getInfo();
        Assertions.assertThat(info.getId()).isNull();
        Assertions.assertThat(info.getNickname()).isEqualTo(attribute.getNickname());
        Assertions.assertThat(info.getRealname()).isEqualTo(attribute.getRealname());
        Assertions.assertThat(info.getSex()).isNull();
        Assertions.assertThat(info.getBirthday()).isEqualTo(attribute.getBirthday());
        Assertions.assertThat(info.getIdCard()).isEqualTo(attribute.getIdCard());
        Assertions.assertThat(info.getCreatedBy()).isNull();
        Assertions.assertThat(info.getCreatedDate()).isNotNull();
        Assertions.assertThat(info.getLastModifiedBy()).isNull();
        Assertions.assertThat(info.getLastModifiedDate()).isNotNull();
        Assertions.assertThat(info.getState()).isEqualTo(GlobalConstants.DATA_NORMAL_STATE);
    }
}
