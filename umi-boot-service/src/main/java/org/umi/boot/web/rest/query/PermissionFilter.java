package org.umi.boot.web.rest.query;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "菜单查询（过滤条件）", value = "PermissionFilter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionFilter {

    private Long pid = 0L;

    private String name;

    private String path;

    private String icon;

    private String desc;

    private String lastModifiedBy;
}
