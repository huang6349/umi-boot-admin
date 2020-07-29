package org.umi.boot.service;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umi.boot.commons.exception.BadRequestException;
import org.umi.boot.domain.Permission;
import org.umi.boot.repository.PermissionRepository;

import java.util.Optional;

@Service
@Transactional
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional(readOnly = true)
    public Permission findById(Long id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        if (!permission.isPresent()) {
            throw new BadRequestException(StrUtil.format("未能在系统中找到数据编号为【{}】的菜单信息", id));
        }
        return permission.get();
    }
}
