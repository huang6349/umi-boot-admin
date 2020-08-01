package org.umi.boot.service.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "数据传输对象（带编号）", value = "AbstractIdAuditingDTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractIdAuditingDTO {

    private Long id;
}
