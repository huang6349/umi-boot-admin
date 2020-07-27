package org.umi.boot.web.rest.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("数据审计视图模型（带编号）")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractIdAuditingVM {

    @ApiModelProperty("数据编号")
    private Long id;
}
