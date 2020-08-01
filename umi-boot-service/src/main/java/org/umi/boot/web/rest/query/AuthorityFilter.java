package org.umi.boot.web.rest.query;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "角色查询（过滤条件）", value = "AuthorityFilter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityFilter {

    private String name;

    private String code;

    private String desc;

    private String lastModifiedBy;
}
