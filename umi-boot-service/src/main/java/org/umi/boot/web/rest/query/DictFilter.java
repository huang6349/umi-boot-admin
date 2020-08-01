package org.umi.boot.web.rest.query;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "字典查询（过滤条件）", value = "DictFilter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictFilter {

    private Long pid = 0L;

    private String name;

    private String code;

    private String data;

    private String desc;

    private String lastModifiedBy;
}
