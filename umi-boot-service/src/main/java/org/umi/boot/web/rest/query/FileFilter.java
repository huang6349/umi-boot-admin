package org.umi.boot.web.rest.query;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "文件查询（过滤条件）", value = "FileFilter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileFilter {

    private String name;

    private String createdBy;
}
