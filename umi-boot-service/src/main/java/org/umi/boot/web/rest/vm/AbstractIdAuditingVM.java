package org.umi.boot.web.rest.vm;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "显示模型（带编号）", value = "AbstractIdAuditingVM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractIdAuditingVM {

    private Long id;
}
