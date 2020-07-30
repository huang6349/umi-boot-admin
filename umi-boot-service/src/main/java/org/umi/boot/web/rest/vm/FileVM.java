package org.umi.boot.web.rest.vm;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.File;

import java.time.Instant;

@ApiModel("文件视图模型")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileVM extends AbstractIdAuditingVM {

    @ApiModelProperty("文件名称")
    private String name;

    @ApiModelProperty("文件地址")
    private String url;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件大小")
    private String size;

    @ApiModelProperty("下载次数")
    private Long num = 0L;

    @ApiModelProperty("下载地址")
    private String download;

    @ApiModelProperty("创建人")
    private String createdBy;

    @ApiModelProperty("创建时间")
    private Instant createdDate;

    public static FileVM adapt(File file) {
        FileVM vm = new FileVM();
        BeanUtils.copyProperties(file, vm);
        vm.setType(StrUtil.subAfter(file.getName(), ".", true));
        vm.setDownload(StrUtil.format("/api/file/download/{}", file.getId()));
        return vm;
    }
}
