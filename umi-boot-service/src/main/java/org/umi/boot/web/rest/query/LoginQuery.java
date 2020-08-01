package org.umi.boot.web.rest.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel(description = "登录查询", value = "LoginQuery")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginQuery {

    @ApiModelProperty(value = "用户帐号", required = true)
    @NotNull(message = "用户帐号不能为空")
    private String username;

    @ApiModelProperty(value = "用户密码", required = true)
    @NotNull(message = "用户密码不能为空")
    private String password;
}
