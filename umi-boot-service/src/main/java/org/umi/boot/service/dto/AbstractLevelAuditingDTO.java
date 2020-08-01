package org.umi.boot.service.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "数据传输对象（树形结构）", value = "AbstractLevelAuditingDTO<E>")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractLevelAuditingDTO<E> extends AbstractIdAuditingDTO {

    private Long pid = 0L;

    private String level;

    private List<E> children = Lists.newArrayList();
}
