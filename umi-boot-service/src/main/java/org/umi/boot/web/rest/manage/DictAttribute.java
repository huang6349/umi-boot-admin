package org.umi.boot.web.rest.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Dict;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(description = "字典管理（新增属性）", value = "DictAttribute")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictAttribute {

    @ApiModelProperty(value = "上级数据编号")
    private Long pid = 0L;

    @ApiModelProperty(value = "字典名称", required = true)
    @NotBlank(message = "字典名称不能为空")
    @Size(max = 50, message = "字典名称的长度只能小于50个字符")
    private String name;

    @ApiModelProperty(value = "字典唯一标识码")
    @Size(max = 50, message = "字典唯一标识码的长度只能小于50个字符")
    private String code;

    @ApiModelProperty(value = "字典数据")
    @Size(max = 50, message = "字典数据的长度只能小于50个字符")
    private String data;

    @ApiModelProperty(value = "字典描述")
    private String desc;

    public static Dict adapt(DictAttribute attribute) {
        if (attribute == null) return null;
        Dict dict = new Dict();
        BeanUtils.copyProperties(attribute, dict);
        return dict;
    }
}
