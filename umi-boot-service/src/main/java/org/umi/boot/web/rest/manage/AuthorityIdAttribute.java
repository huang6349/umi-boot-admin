package org.umi.boot.web.rest.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Authority;

import javax.validation.constraints.NotNull;

@ApiModel(description = "角色管理（修改属性）", value = "AuthorityIdAttribute")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityIdAttribute extends AuthorityAttribute {

    @ApiModelProperty(value = "数据编号", required = true)
    @NotNull(message = "数据编号不能为空")
    private Long id;

    public static Authority adapt(AuthorityIdAttribute attribute, Authority authority) {
        if (attribute == null || authority == null) return null;
        BeanUtils.copyProperties(attribute, authority);
        return authority;
    }
}
