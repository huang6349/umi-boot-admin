package org.umi.boot.web.rest.query;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "用户查询（过滤条件）", value = "UserFilter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFilter {

    private String username;

    private String email;

    private String mobilePhone;

    private String nickname;

    private String realname;

    private String idCard;

    private String lastModifiedBy;
}
