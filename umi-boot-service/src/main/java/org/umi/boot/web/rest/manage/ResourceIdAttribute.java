package org.umi.boot.web.rest.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Resource;

import javax.validation.constraints.NotNull;

@ApiModel(description = "菜单资源管理（修改属性）", value = "ResourceIdAttribute")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceIdAttribute extends ResourceAttribute {

    @ApiModelProperty(value = "数据编号", required = true)
    @NotNull(message = "数据编号不能为空")
    private Long id;

    public static Resource adapt(ResourceIdAttribute attribute, Resource resource) {
        if (attribute == null || resource == null) return null;
        BeanUtils.copyProperties(attribute, resource);
        return resource;
    }
}
