package org.umi.boot.web.rest.filters;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("角色过滤条件")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityFilters {

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色唯一标识码")
    private String code;

    @ApiModelProperty("角色描述")
    private String desc;

    @ApiModelProperty("最后修改人")
    private String lastModifiedBy;
}
