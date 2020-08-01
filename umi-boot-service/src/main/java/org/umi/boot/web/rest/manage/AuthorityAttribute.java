package org.umi.boot.web.rest.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Authority;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel(description = "角色管理（新增属性）", value = "AuthorityAttribute")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityAttribute {

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50, message = "角色名称的长度只能小于50个字符")
    private String name;

    @ApiModelProperty(value = "角色唯一标识码", required = true)
    @NotBlank(message = "角色唯一标识码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,18}$", message = "角色唯一标识码只能是1-18个字母和数字的组合")
    private String code;

    @ApiModelProperty(value = "角色描述")
    private String desc;

    public static Authority adapt(AuthorityAttribute attribute) {
        if (attribute == null) return null;
        Authority authority = new Authority();
        BeanUtils.copyProperties(attribute, authority);
        return authority;
    }
}
