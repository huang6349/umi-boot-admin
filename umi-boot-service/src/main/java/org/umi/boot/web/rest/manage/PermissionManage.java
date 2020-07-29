package org.umi.boot.web.rest.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel("菜单管理模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionManage {

    @ApiModelProperty("数据编号")
    private Long id;

    @ApiModelProperty("上级数据编号")
    private Long pid = 0L;

    @ApiModelProperty("菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称的长度只能小于50个字符")
    private String name;

    @ApiModelProperty("菜单路径")
    private String path;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("排序")
    @NotNull(message = "排序不能为空")
    private Integer seq = 0;

    @ApiModelProperty("菜单描述")
    private String desc;
}
