package org.umi.boot.web.rest.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("菜单资源管理模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceManage {

    @ApiModelProperty("数据编号")
    private Long id;

    @ApiModelProperty("所属菜单")
    @NotNull(message = "所属菜单不能为空")
    private Long permissionId;

    @ApiModelProperty("资源地址")
    @NotBlank(message = "资源地址不能为空")
    private String pattern;

    @ApiModelProperty("资源类型")
    @NotNull(message = "资源类型不能为空")
    private Long methodId;

    @ApiModelProperty("资源描述")
    private String desc;
}
