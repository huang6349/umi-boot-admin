package org.umi.boot.web.rest.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Permission;

import javax.validation.constraints.NotNull;

@ApiModel(description = "菜单管理（修改属性）", value = "PermissionIdAttribute")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionIdAttribute extends PermissionAttribute {

    @ApiModelProperty(value = "数据编号", required = true)
    @NotNull(message = "数据编号不能为空")
    private Long id;

    public static Permission adapt(PermissionIdAttribute attribute, Permission permission) {
        if (attribute == null || permission == null) return null;
        BeanUtils.copyProperties(attribute, permission);
        return permission;
    }
}
