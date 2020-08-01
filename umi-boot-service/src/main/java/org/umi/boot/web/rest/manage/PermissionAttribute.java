package org.umi.boot.web.rest.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Permission;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(description = "菜单管理（新增属性）", value = "PermissionAttribute")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionAttribute {

    @ApiModelProperty(value = "上级数据编号")
    private Long pid = 0L;

    @ApiModelProperty(value = "菜单名称", required = true)
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称的长度只能小于50个字符")
    private String name;

    @ApiModelProperty(value = "菜单路径")
    private String path;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序不能为空")
    private Integer seq = 0;

    @ApiModelProperty(value = "菜单描述")
    private String desc;

    public static Permission adapt(PermissionAttribute attribute) {
        if (attribute == null) return null;
        Permission permission = new Permission();
        BeanUtils.copyProperties(attribute, permission);
        return permission;
    }
}
