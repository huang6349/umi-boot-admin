package org.umi.boot.web.rest.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@ApiModel(description = "用户密码更新管理", value = "ChangePasswordAttribute")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordAttribute {

    @ApiModelProperty(value = "旧的用户密码", required = true)
    @NotBlank(message = "旧的密码不能为空")
    private String oldPassword;

    @ApiModelProperty(value = "新的用户密码", required = true)
    @NotBlank(message = "新的密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{6,16}$", message = "新的密码的长度只能在6-16个字符之间，且至少包括1个大写字母，1个小写字母，1个数字")
    private String newPassword;

    @ApiModelProperty("确认新的用户密码")
    private String confirm;
}
