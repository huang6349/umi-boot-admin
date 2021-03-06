package org.umi.boot.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;
import org.umi.boot.commons.exception.BadRequestException;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.domain.File;
import org.umi.boot.repository.FileRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;

@Service
@Transactional
public class FileService {

    @Autowired
    private MultipartProperties multipartProperties;

    @Autowired
    private FileRepository fileRepository;

    @Transactional(readOnly = true)
    public File findById(Long id, String errorMessage) {
        if (id == null) throw new BadRequestException(errorMessage);
        Optional<File> file = fileRepository.findById(id);
        if (!file.isPresent()) throw new BadRequestException(errorMessage);
        return file.get();
    }

    public File create(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new BadRequestException("上传失败，不允许上传空文件");
        }
        String fileName = multipartFile.getOriginalFilename();
        String rawFileName = StrUtil.subBefore(fileName, ".", true);
        String fileType = StrUtil.subAfter(fileName, ".", true);
        String localFilePath = StrUtil.format("{}{}{}{}-{}.{}", StrUtil.trimToEmpty(StrUtil.appendIfMissing(multipartProperties.getLocation(), "/")), StrUtil.appendIfMissing(DateUtil.today(), "/"), StrUtil.appendIfMissing(fileType, "/"), rawFileName, DateUtil.current(false), fileType);
        try {
            multipartFile.transferTo(FileUtil.touch(localFilePath));
        } catch (IOException e) {
            throw new BadRequestException("上传失败，服务器暂时无法处理这个文件");
        }
        File file = new File();
        file.setName(fileName);
        file.setUrl(localFilePath);
        file.setSize(StrUtil.format("{}KB", DataSize.ofBytes(multipartFile.getSize()).toKilobytes()));
        file.setState(GlobalConstants.DATA_NORMAL_STATE);
        return fileRepository.save(file);
    }

    public void download(HttpServletResponse response, Long id) {
        File file = findById(id, StrUtil.format("数据编号为【{}】的文件信息不存在，无法进行下载操作", id));
        try {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, StrUtil.format("attachment; fileName={}", URLEncoder.encode(file.getName(), "UTF-8").replaceAll("\\+", "%20")));
            FileReader reader = new FileReader(file.getUrl());
            reader.writeToStream(response.getOutputStream());
            file.setNum(file.getNum() + 1);
            fileRepository.save(file);
        } catch (IORuntimeException e) {
            throw new BadRequestException("下载失败，这个文件已经被删除了");
        } catch (IOException e) {
            throw new BadRequestException("下载失败，服务器暂时无法处理这个文件");
        }
    }
}
