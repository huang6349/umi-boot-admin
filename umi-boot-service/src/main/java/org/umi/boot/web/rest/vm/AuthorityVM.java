package org.umi.boot.web.rest.vm;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
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

@ApiModel(description = "角色信息（显示模型）", value = "AuthorityVM")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityVM extends AbstractIdAuditingVM {

    private String name;

    private String code;

    private String desc;

    private Set<Long> permissionIds = Sets.newHashSet();

    private Set<String> permissionNames = Sets.newHashSet();

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public static AuthorityVM adapt(Authority authority) {
        if (authority == null) return null;
        AuthorityVM vm = new AuthorityVM();
        BeanUtils.copyProperties(authority, vm);
        vm.setCode(StringUtils.upperCase(StringUtils.replaceOnce(authority.getCode(), "ROLE_", "")));
        vm.setPermissionIds(authority.getPermissions().stream().map(Permission::getId).collect(Collectors.toSet()));
        vm.setPermissionNames(authority.getPermissions().stream().map(Permission::getName).collect(Collectors.toSet()));
        return vm;
    }
}
