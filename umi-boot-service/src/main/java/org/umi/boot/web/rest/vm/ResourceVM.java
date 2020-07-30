package org.umi.boot.web.rest.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Resource;

import java.time.Instant;

@ApiModel("菜单资源视图模型")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceVM extends AbstractIdAuditingVM {

    @ApiModelProperty("所属菜单")
    private Long permissionId;

    @ApiModelProperty("所属菜单（文字）")
    private String permissionText;

    @ApiModelProperty("资源地址")
    private String pattern;

    @ApiModelProperty("资源类型")
    private Long methodId;

    @ApiModelProperty("资源类型（文字）")
    private String methodText;

    @ApiModelProperty("资源描述")
    private String desc;

    @ApiModelProperty("最后修改人")
    private String lastModifiedBy;

    @ApiModelProperty("最后修改时间")
    private Instant lastModifiedDate;

    public static ResourceVM adapt(Resource resource) {
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
