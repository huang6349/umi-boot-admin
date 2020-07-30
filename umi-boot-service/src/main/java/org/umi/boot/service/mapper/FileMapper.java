package org.umi.boot.service.mapper;

import org.springframework.stereotype.Service;
import org.umi.boot.domain.File;
import org.umi.boot.web.rest.vm.FileVM;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FileMapper {

    public List<FileVM> adapt(List<File> files) {
        return files.stream().filter(Objects::nonNull).map(this::adapt).collect(Collectors.toList());
    }

    public FileVM adapt(File file) {
        return FileVM.adapt(file);
    }
}
