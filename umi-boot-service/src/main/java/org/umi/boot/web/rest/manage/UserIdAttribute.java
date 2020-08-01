package org.umi.boot.web.rest.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.User;
import org.umi.boot.domain.UserInfo;

import javax.validation.constraints.NotNull;

@ApiModel(description = "用户管理（修改属性）", value = "UserIdAttribute")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIdAttribute extends UserAttribute {

    @ApiModelProperty(value = "数据编号", required = true)
    @NotNull(message = "数据编号不能为空")
    private Long id;

    public static User adapt(UserAttribute attribute, User user) {
        if (attribute == null || user == null) return null;
        BeanUtils.copyProperties(attribute, user);
        UserInfo info = user.getInfo();
        if (info != null) {
            BeanUtils.copyProperties(attribute, info, "id");
            user.setInfo(info);
        }
        return user;
    }
}
