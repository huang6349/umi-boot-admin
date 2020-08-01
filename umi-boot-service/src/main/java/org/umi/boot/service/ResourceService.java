package org.umi.boot.service;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umi.boot.commons.exception.BadRequestException;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.domain.Dict;
import org.umi.boot.domain.Permission;
import org.umi.boot.domain.Resource;
import org.umi.boot.repository.ResourceRepository;
import org.umi.boot.web.rest.manage.ResourceAttribute;
import org.umi.boot.web.rest.manage.ResourceIdAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private DictService dictService;

    @Transactional(readOnly = true)
    public Resource findById(Long id, String errorMessage) {
        if (id == null) throw new BadRequestException(errorMessage);
        Optional<Resource> resource = resourceRepository.findById(id);
        if (!resource.isPresent()) throw new BadRequestException(errorMessage);
        return resource.get();
    }

    @Transactional(readOnly = true)
    public List<Resource> getCurrentUserResources() {
        return permissionService.getCurrentUserPermissions()
                .stream()
                .flatMap(permission -> permission.getResources().stream())
                .collect(Collectors.toList());
    }

    public Resource create(ResourceAttribute attribute) {
        Permission permission = permissionService.findById(attribute.getPermissionId(), StrUtil.format("数据编号为【{}】的菜单信息不存在，无法进行新增操作", attribute.getPermissionId()));
        Dict method = dictService.findById(attribute.getMethodId(), 10100L, StrUtil.format("数据编号为【{}】的资源类型不存在，无法进行新增操作", attribute.getMethodId()));
        Resource resource = ResourceAttribute.adapt(attribute);
        resource.setPermission(permission);
        resource.setMethod(method);
        resource.setState(GlobalConstants.DATA_NORMAL_STATE);
        return resourceRepository.save(resource);
    }

    public Resource update(ResourceIdAttribute attribute) {
        Resource resource = findById(attribute.getId(), StrUtil.format("数据编号为【{}】的资源信息不存在，无法进行修改操作", attribute.getId()));
        if (GlobalConstants.DATA_KEEP_STATE.equals(resource.getState())) {
            if (!StringUtils.equals(StringUtils.trimToNull(attribute.getPattern()), StringUtils.trimToNull(resource.getPattern()))) {
                throw new BadRequestException("该资源为系统保留资源，无法进行地址修改操作");
            }
            if (attribute.getMethodId() == null || !attribute.getMethodId().equals(resource.getMethod().getId())) {
                throw new BadRequestException("该资源为系统保留资源，无法进行类型修改操作");
            }
        }
        Permission permission = permissionService.findById(attribute.getPermissionId(), StrUtil.format("数据编号为【{}】的菜单信息不存在，无法进行修改操作", attribute.getPermissionId()));
        Dict method = dictService.findById(attribute.getMethodId(), 10100L, StrUtil.format("数据编号为【{}】的资源类型不存在，无法进行修改操作", attribute.getMethodId()));
        Resource updateResource = ResourceIdAttribute.adapt(attribute, resource);
        updateResource.setPermission(permission);
        updateResource.setMethod(method);
        return resourceRepository.save(updateResource);
    }

    public List<Resource> batchDelete(Long[] ids) {
        return Arrays.stream(ids).map(this::delete).collect(Collectors.toList());
    }

    public Resource delete(Long id) {
        Resource resource = findById(id, StrUtil.format("数据编号为【{}】的资源信息不存在，无法进行删除操作", id));
        if (GlobalConstants.DATA_KEEP_STATE.equals(resource.getState())) {
            throw new BadRequestException("该资源为系统保留资源，无法进行删除操作");
        }
        resource.setState(GlobalConstants.DATA_DELETE_STATE);
        return resourceRepository.save(resource);
    }
}
