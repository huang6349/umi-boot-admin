package org.umi.boot.web.rest.filters;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("字典过滤条件")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictFilters {

    @ApiModelProperty("上级数据编号")
    private Long pid = 0L;

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("字典唯一标识码")
    private String code;

    @ApiModelProperty("字典数据")
    private String data;

    @ApiModelProperty("字典描述")
    private String desc;

    @ApiModelProperty("最后修改人")
    private String lastModifiedBy;
}
