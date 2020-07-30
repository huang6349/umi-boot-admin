package org.umi.boot.web.rest.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Dict;

import java.time.Instant;

@ApiModel("字典视图模型")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictVM extends AbstractIdAuditingVM {

    @ApiModelProperty("上级数据编号")
    private Long pid = 0L;

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("字典唯一标识码")
    private String code;

    @ApiModelProperty("字典数据")
    private String data;

    @ApiModelProperty("字典描述")
    private String desc;

    @ApiModelProperty("最后修改人")
    private String lastModifiedBy;

    @ApiModelProperty("最后修改时间")
    private Instant lastModifiedDate;

    public static DictVM adapt(Dict dict) {
        if (dict == null) return null;
        DictVM vm = new DictVM();
        BeanUtils.copyProperties(dict, vm);
        return vm;
    }
}
