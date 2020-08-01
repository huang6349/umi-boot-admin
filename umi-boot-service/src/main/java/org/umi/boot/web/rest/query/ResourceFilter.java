package org.umi.boot.web.rest.query;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "菜单资源查询（过滤条件）", value = "ResourceFilter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceFilter {

    private Long permissionId;

    private String pattern;

    private Long methodId;

    private String desc;

    private String lastModifiedBy;
}
