package org.umi.boot.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("数据审计传输对象（带编号）")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractIdAuditingDTO {

    @ApiModelProperty("数据编号")
    private Long id;
}
