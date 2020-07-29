package org.umi.boot.web.rest.filters;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("菜单过滤条件")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionFilters {

    @ApiModelProperty("上级数据编号")
    private Long pid = 0L;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单路径")
    private String path;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("菜单描述")
    private String desc;

    @ApiModelProperty("最后修改人")
    private String lastModifiedBy;
}
