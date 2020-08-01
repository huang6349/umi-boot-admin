package org.umi.boot.web.rest.vm;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Dict;

import java.time.Instant;

@ApiModel(description = "字典信息（显示模型-树形数据）", value = "DictLevelVM")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictLevelVM extends AbstractLevelAuditingVM<DictLevelVM> {

    private String name;

    private String code;

    private String data;

    private String desc;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public static DictLevelVM adapt(Dict dict) {
        if (dict == null) return null;
        DictLevelVM vm = new DictLevelVM();
        BeanUtils.copyProperties(dict, vm);
        return vm;
    }
}
