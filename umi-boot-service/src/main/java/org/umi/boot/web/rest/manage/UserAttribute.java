package org.umi.boot.web.rest.manage;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.User;
import org.umi.boot.domain.UserInfo;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

@ApiModel(description = "用户管理（新增属性）", value = "UserAttribute")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAttribute {

    @ApiModelProperty(value = "用户帐号", required = true)
    @NotBlank(message = "用户帐号不能为空")
    @Pattern(regexp = "^[a-zA-Z][-_a-zA-Z0-9]{4,19}$", message = "用户帐号只能是5-19个字母，数字，减号，下划线的组合，且首位必须是字母")
    private String username;

    @ApiModelProperty(value = "用户邮箱")
    @Email(message = "错误的邮箱格式")
    private String email;

    @ApiModelProperty(value = "用户手机号")
    @Pattern(regexp = "^$|^(?:(?:\\+|00)86)?1[3-9]\\d{9}$", message = "错误的手机号格式")
    private String mobilePhone;

    @ApiModelProperty(value = "用户昵称", required = true)
    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 50, message = "用户昵称的长度只能小于50个字符")
    private String nickname;

    @ApiModelProperty(value = "用户真实姓名")
    private String realname;

    @ApiModelProperty(value = "用户性别")
    @NotNull(message = "用户性别不能为空")
    private Long sexId;

    @ApiModelProperty(value = "用户生日")
    @Past(message = "用户生日必须是一个过去的日期")
    private Date birthday;

    @ApiModelProperty(value = "用户身份证")
    @Pattern(regexp = "(^$|^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)", message = "错误的身份证格式")
    private String idCard;

    @ApiModelProperty(value = "用户角色编号列表")
    @NotEmpty(message = "用户角色不能为空")
    private Set<Long> authoritieIds = Sets.newHashSet();

    public static User adapt(UserAttribute attribute) {
        if (attribute == null) return null;
        User user = new User();
        BeanUtils.copyProperties(attribute, user);
        UserInfo info = new UserInfo();
        BeanUtils.copyProperties(attribute, info);
        user.setInfo(info);
        return user;
    }
}
