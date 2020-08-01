package org.umi.boot.web.rest.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(description = "菜单资源管理（新增属性）", value = "ResourceAttribute")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceAttribute {

    @ApiModelProperty(value = "所属菜单", required = true)
    @NotNull(message = "所属菜单不能为空")
    private Long permissionId;

    @ApiModelProperty(value = "资源地址", required = true)
    @NotBlank(message = "资源地址不能为空")
    private String pattern;

    @ApiModelProperty(value = "资源类型", required = true)
    @NotNull(message = "资源类型不能为空")
    private Long methodId;

    @ApiModelProperty(value = "资源描述")
    private String desc;

    public static Resource adapt(ResourceAttribute attribute) {
        if (attribute == null) return null;
        Resource resource = new Resource();
        BeanUtils.copyProperties(attribute, resource);
        return resource;
    }
}
