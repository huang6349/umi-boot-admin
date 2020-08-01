package org.umi.boot.web.rest.manage;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Sets;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.umi.boot.domain.User;
import org.umi.boot.domain.UserInfo;

import java.util.Date;

class UserIdAttributeTest {

    @Test
    void adapt() {
        User user = new User();
        user.setId(RandomUtil.randomLong());
        user.setUsername(RandomUtil.randomString(12));
        user.setEmail(RandomUtil.randomString(12));
        user.setMobilePhone(RandomUtil.randomString(12));
        UserInfo info = new UserInfo();
        info.setId(RandomUtil.randomLong());
        info.setNickname(RandomUtil.randomString(12));
        info.setRealname(RandomUtil.randomString(12));
        info.setBirthday(new Date());
        info.setIdCard(RandomUtil.randomString(12));
        user.setInfo(info);
        UserIdAttribute attribute = new UserIdAttribute();
        attribute.setId(user.getId());
        attribute.setUsername(RandomUtil.randomString(12));
        attribute.setEmail(RandomUtil.randomString(12));
        attribute.setMobilePhone(RandomUtil.randomString(12));
        attribute.setNickname(RandomUtil.randomString(12));
        attribute.setRealname(RandomUtil.randomString(12));
        attribute.setSexId(RandomUtil.randomLong());
        attribute.setBirthday(new Date());
        attribute.setIdCard(RandomUtil.randomString(12));
        attribute.setAuthoritieIds(Sets.newHashSet(RandomUtil.randomLong()));
        User newUser = UserIdAttribute.adapt(attribute, user);
        Assertions.assertThat(newUser.getId()).isEqualTo(user.getId());
        Assertions.assertThat(newUser.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(newUser.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(newUser.getMobilePhone()).isEqualTo(user.getMobilePhone());
        Assertions.assertThat(newUser.getPassword()).isEqualTo(user.getPassword());
        Assertions.assertThat(newUser.getInfo()).isEqualTo(user.getInfo());
        Assertions.assertThat(newUser.getAuthorities()).isEqualTo(user.getAuthorities());
        Assertions.assertThat(newUser.getCreatedBy()).isEqualTo(user.getCreatedBy());
        Assertions.assertThat(newUser.getCreatedDate()).isEqualTo(user.getCreatedDate());
        Assertions.assertThat(newUser.getLastModifiedBy()).isEqualTo(user.getLastModifiedBy());
        Assertions.assertThat(newUser.getLastModifiedDate()).isEqualTo(user.getLastModifiedDate());
        Assertions.assertThat(newUser.getState()).isEqualTo(user.getState());
        UserInfo newInfo = newUser.getInfo();
        Assertions.assertThat(newInfo.getId()).isEqualTo(info.getId());
        Assertions.assertThat(newInfo.getNickname()).isEqualTo(info.getNickname());
        Assertions.assertThat(newInfo.getRealname()).isEqualTo(info.getRealname());
        Assertions.assertThat(newInfo.getSex()).isEqualTo(info.getSex());
        Assertions.assertThat(newInfo.getBirthday()).isEqualTo(info.getBirthday());
        Assertions.assertThat(newInfo.getIdCard()).isEqualTo(info.getIdCard());
        Assertions.assertThat(newInfo.getCreatedBy()).isEqualTo(info.getCreatedBy());
        Assertions.assertThat(newInfo.getCreatedDate()).isEqualTo(info.getCreatedDate());
        Assertions.assertThat(newInfo.getLastModifiedBy()).isEqualTo(info.getLastModifiedBy());
        Assertions.assertThat(newInfo.getLastModifiedDate()).isEqualTo(info.getLastModifiedDate());
        Assertions.assertThat(newInfo.getState()).isEqualTo(info.getState());
    }
}
