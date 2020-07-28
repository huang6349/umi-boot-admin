package org.umi.boot.web.rest.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel("字典管理模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictManage {

    @ApiModelProperty("数据编号")
    private Long id;

    @ApiModelProperty("上级数据编号")
    private Long pid = 0L;

    @ApiModelProperty("字典名称")
    @NotBlank(message = "字典名称不能为空")
    @Size(max = 50, message = "字典名称的长度只能小于50个字符")
    private String name;

    @ApiModelProperty("字典唯一标识码")
    @Size(max = 50, message = "字典唯一标识码的长度只能小于50个字符")
    private String code;

    @ApiModelProperty("字典数据")
    @Size(max = 50, message = "字典数据的长度只能小于50个字符")
    private String data;

    @ApiModelProperty("字典描述")
    private String desc;
}
