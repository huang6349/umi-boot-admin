package org.umi.boot.web.rest.filters;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("用户过滤条件")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFilters {

    @ApiModelProperty("用户帐号")
    private String username;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户手机号")
    private String mobilePhone;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户真实姓名")
    private String realname;

    @ApiModelProperty("用户身份证号码")
    private String idCard;

    @ApiModelProperty("最后修改人")
    private String lastModifiedBy;
}
