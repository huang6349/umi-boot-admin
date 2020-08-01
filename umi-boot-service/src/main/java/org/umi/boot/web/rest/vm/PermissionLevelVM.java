package org.umi.boot.web.rest.vm;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Permission;

import java.time.Instant;

@ApiModel(description = "菜单信息（显示模型-树形数据）", value = "PermissionLevelVM")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionLevelVM extends AbstractLevelAuditingVM<PermissionLevelVM> {

    private String name;

    private String path;

    private String icon;

    private Integer seq;

    private String desc;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public static PermissionLevelVM adapt(Permission permission) {
        if (permission == null) return null;
        PermissionLevelVM vm = new PermissionLevelVM();
        BeanUtils.copyProperties(permission, vm);
        return vm;
    }
}
