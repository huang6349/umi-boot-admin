package org.umi.boot.web.rest;

import cn.hutool.json.JSONUtil;
import com.github.wenhao.jpa.Specifications;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.umi.boot.commons.info.Info;
import org.umi.boot.commons.info.InfoStructure;
import org.umi.boot.domain.File;
import org.umi.boot.repository.FileRepository;
import org.umi.boot.service.FileService;
import org.umi.boot.service.mapper.FileMapper;
import org.umi.boot.web.rest.filters.FileFilters;

import javax.servlet.http.HttpServletResponse;

@Api(tags = "文件管理")
@RestController
@RequestMapping("/api")
public class FileResource {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileMapper fileMapper;

    @ApiOperation("查询所有的文件")
    @GetMapping("/file")
    public InfoStructure query() {
        return Info.success(fileMapper.adapt(fileRepository.findAll()));
    }

    @ApiOperation("查询一个文件")
    @GetMapping("/file/{id}")
    public InfoStructure query(@PathVariable Long id) {
        return Info.success(fileMapper.adapt(fileRepository.findById(id).orElse(null)));
    }

    @ApiOperation("分页查询文件")
    @GetMapping("/file/pageable")
    public InfoStructure query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String filters) {
        FileFilters fileFilters = JSONUtil.toBean(filters, FileFilters.class);
        Specification<File> specification = Specifications.<File>and()
                .like(StringUtils.isNotBlank(fileFilters.getName()), "name", "%" + StringUtils.trim(fileFilters.getName()) + "%")
                .like(StringUtils.isNotBlank(fileFilters.getCreatedBy()), "createdBy", "%" + StringUtils.trim(fileFilters.getCreatedBy()) + "%")
                .build();
        return Info.success(fileRepository.findAll(specification, pageable).map(fileMapper::adapt));
    }

    @ApiOperation("上传文件")
    @PostMapping(value = "/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public InfoStructure local(@RequestParam("file") MultipartFile file) {
        return Info.success(fileMapper.adapt(fileService.create(file)));
    }

    @ApiOperation("下载文件")
    @GetMapping("/file/download/{id}")
    public void local(HttpServletResponse response, @PathVariable Long id) {
        fileService.download(response, id);
    }
}
