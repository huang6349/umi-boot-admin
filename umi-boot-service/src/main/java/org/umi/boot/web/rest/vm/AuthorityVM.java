package org.umi.boot.web.rest.vm;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Authority;
import org.umi.boot.domain.Permission;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@ApiModel("角色视图模型")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityVM extends AbstractIdAuditingVM {

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色唯一标识码")
    private String code;

    @ApiModelProperty("角色描述")
    private String desc;

    @ApiModelProperty("角色菜单编号列表")
    private Set<Long> permissionIds = Sets.newHashSet();

    @ApiModelProperty("角色菜单名称列表")
    private Set<String> permissionNames = Sets.newHashSet();

    @ApiModelProperty("最后修改人")
    private String lastModifiedBy;

    @ApiModelProperty("最后修改时间")
    private Instant lastModifiedDate;

    public static AuthorityVM adapt(Authority authority) {
        AuthorityVM vm = new AuthorityVM();
        BeanUtils.copyProperties(authority, vm);
        vm.setCode(StringUtils.upperCase(StringUtils.replaceOnce(authority.getCode(), "ROLE_", "")));
        vm.setPermissionIds(authority.getPermissions().stream().map(Permission::getId).collect(Collectors.toSet()));
        vm.setPermissionNames(authority.getPermissions().stream().map(Permission::getName).collect(Collectors.toSet()));
        return vm;
    }
}
