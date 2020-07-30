package org.umi.boot.web.rest.filters;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("菜单资源过滤条件")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceFilters {

    @ApiModelProperty("所属菜单")
    private Long permissionId;

    @ApiModelProperty("资源地址")
    private String pattern;

    @ApiModelProperty("资源类型")
    private Long methodId;

    @ApiModelProperty("资源描述")
    private String desc;

    @ApiModelProperty("最后修改人")
    private String lastModifiedBy;
}
