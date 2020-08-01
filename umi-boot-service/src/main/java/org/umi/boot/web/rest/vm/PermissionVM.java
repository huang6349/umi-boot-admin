package org.umi.boot.web.rest.vm;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Permission;

import java.time.Instant;

@ApiModel(description = "菜单信息（显示模型）", value = "PermissionVM")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionVM extends AbstractIdAuditingVM {

    private Long pid = 0L;

    private String name;

    private String path;

    private String icon;

    private Integer seq;

    private String desc;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public static PermissionVM adapt(Permission permission) {
        if (permission == null) return null;
        PermissionVM vm = new PermissionVM();
        BeanUtils.copyProperties(permission, vm);
        return vm;
    }
}
