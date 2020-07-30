package org.umi.boot.web.rest.filters;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("文件过滤条件")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileFilters {

    @ApiModelProperty("文件名称")
    private String name;

    @ApiModelProperty("创建人")
    private String createdBy;
}
