package org.umi.boot.web.rest.vm;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Resource;

import java.time.Instant;

@ApiModel(description = "菜单资源信息（显示模型）", value = "ResourceVM")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceVM extends AbstractIdAuditingVM {

    private Long permissionId;

    private String permissionText;

    private String pattern;

    private Long methodId;

    private String methodText;

    private String desc;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public static ResourceVM adapt(Resource resource) {
        if (resource == null) return null;
        ResourceVM vm = new ResourceVM();
        BeanUtils.copyProperties(resource, vm);
        if (resource.getPermission() != null) {
            vm.setPermissionId(resource.getPermission().getId());
            vm.setPermissionText(resource.getPermission().getName());
        }
        if (resource.getMethod() != null) {
            vm.setMethodId(resource.getMethod().getId());
            vm.setMethodText(resource.getMethod().getName());
        }
        return vm;
    }
}
