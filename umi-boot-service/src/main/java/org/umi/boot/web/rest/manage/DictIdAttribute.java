package org.umi.boot.web.rest.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Dict;

import javax.validation.constraints.NotNull;

@ApiModel(description = "字典管理（修改属性）", value = "DictIdAttribute")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictIdAttribute extends DictAttribute {

    @ApiModelProperty(value = "数据编号", required = true)
    @NotNull(message = "数据编号不能为空")
    private Long id;

    public static Dict adapt(DictIdAttribute attribute, Dict dict) {
        if (attribute == null || dict == null) return null;
        BeanUtils.copyProperties(attribute, dict);
        return dict;
    }
}
