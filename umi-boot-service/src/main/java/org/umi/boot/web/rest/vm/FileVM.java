package org.umi.boot.web.rest.vm;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.File;

import java.time.Instant;

@ApiModel(description = "文件信息（显示模型）", value = "FileVM")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileVM extends AbstractIdAuditingVM {

    private String name;

    private String url;

    private String type;

    private String size;

    private Long num = 0L;

    private String download;

    private String createdBy;

    private Instant createdDate;

    public static FileVM adapt(File file) {
        if (file == null) return null;
        FileVM vm = new FileVM();
        BeanUtils.copyProperties(file, vm);
        vm.setType(StrUtil.subAfter(file.getName(), ".", true));
        vm.setDownload(StrUtil.format("/api/file/download/{}", file.getId()));
        return vm;
    }
}
