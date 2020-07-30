package org.umi.boot.web.rest.manage;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

@ApiModel("用户管理模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserManage {

    @ApiModelProperty("数据编号")
    private Long id;

    @ApiModelProperty("用户帐号")
    @NotBlank(message = "用户帐号不能为空")
    @Pattern(regexp = "^[a-zA-Z][-_a-zA-Z0-9]{4,19}$", message = "用户帐号只能是5-19个字母，数字，减号，下划线的组合，且首位必须是字母")
    private String username;

    @ApiModelProperty("用户邮箱")
    @Email(message = "错误的邮箱格式")
    private String email;

    @ApiModelProperty("用户手机号")
    @Pattern(regexp = "^$|^(?:(?:\\+|00)86)?1[3-9]\\d{9}$", message = "错误的手机号格式")
    private String mobilePhone;

    @ApiModelProperty("用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 50, message = "用户昵称的长度只能小于50个字符")
    private String nickname;

    @ApiModelProperty("用户真实姓名")
    private String realname;

    @ApiModelProperty("用户性别")
    @NotNull(message = "用户性别不能为空")
    private Long sexId;

    @ApiModelProperty("用户生日")
    @Past(message = "用户生日必须是一个过去的日期")
    private Date birthday;

    @ApiModelProperty("用户身份证")
    @Pattern(regexp = "(^$|^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)", message = "错误的身份证格式")
    private String idCard;

    @ApiModelProperty("用户角色编号列表")
    @NotEmpty(message = "用户角色不能为空")
    private Set<Long> authoritieIds = Sets.newHashSet();
}
