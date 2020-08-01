package org.umi.boot.service;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umi.boot.commons.exception.BadRequestException;
import org.umi.boot.commons.exception.DataAlreadyExistException;
import org.umi.boot.commons.utils.LevelUtil;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.domain.Permission;
import org.umi.boot.repository.PermissionRepository;
import org.umi.boot.repository.UserRepository;
import org.umi.boot.security.SecurityUtils;
import org.umi.boot.web.rest.manage.PermissionAttribute;
import org.umi.boot.web.rest.manage.PermissionIdAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PermissionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional(readOnly = true)
    public Permission findById(Long id, String errorMessage) {
        if (id == null) throw new BadRequestException(errorMessage);
        Optional<Permission> permission = permissionRepository.findById(id);
        if (!permission.isPresent()) throw new BadRequestException(errorMessage);
        return permission.get();
    }

    @Transactional(readOnly = true)
    public List<Permission> getCurrentUserPermissions() {
        return SecurityUtils.getCurrentUserUsername()
                .flatMap(userRepository::findByUsername)
                .map(user -> permissionRepository.findByAuthoritiesInOrderBySeqDesc(user.getAuthorities()))
                .orElse(null);
    }

    public Permission create(PermissionAttribute attribute) {
        if (!permissionRepository.findByName(attribute.getName()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("名称为【{}】的菜单信息已经存在了", attribute.getName()));
        }
        Permission permission = PermissionAttribute.adapt(attribute);
        permission.setState(GlobalConstants.DATA_NORMAL_STATE);
        permission.setLevel(LevelUtil.calculateLevel(getLevel(attribute.getPid()), attribute.getPid()));
        return permissionRepository.save(permission);
    }

    public Permission update(PermissionIdAttribute attribute) {
        Permission permission = findById(attribute.getId(), StrUtil.format("数据编号为【{}】的菜单信息不存在，无法进行修改操作", attribute.getId()));
        if (GlobalConstants.DATA_KEEP_STATE.equals(permission.getState())) {
            if (!StringUtils.equals(StringUtils.trimToNull(attribute.getName()), StringUtils.trimToNull(permission.getName()))) {
                throw new BadRequestException("该菜单为系统保留菜单，无法进行菜单名称修改操作");
            }
            if (!StringUtils.equals(StringUtils.trimToNull(attribute.getPath()), StringUtils.trimToNull(permission.getPath()))) {
                throw new BadRequestException("该菜单为系统保留菜单，无法进行菜单路径修改操作");
            }
            if (!StringUtils.equals(StringUtils.trimToNull(attribute.getIcon()), StringUtils.trimToNull(permission.getIcon()))) {
                throw new BadRequestException("该菜单为系统保留菜单，无法进行菜单图标修改操作");
            }
        }
        if (!permissionRepository.findByNameAndIdNot(attribute.getName(), attribute.getId()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("名称为【{}】的菜单信息已经存在了", attribute.getName()));
        }
        String beforeLevel = permission.getLevel();
        String afterLevel = LevelUtil.calculateLevel(getLevel(attribute.getPid()), attribute.getPid());
        if (StringUtils.startsWith(afterLevel, LevelUtil.calculateLevel(beforeLevel, attribute.getId()))) {
            throw new BadRequestException("不允许将自己设置为自己的子级");
        }
        Permission updatePermission = PermissionIdAttribute.adapt(attribute, permission);
        if (!StringUtils.equals(beforeLevel, afterLevel)) {
            updatePermission.setLevel(afterLevel);
            batchUpdateLevel(beforeLevel, afterLevel, attribute.getId());
        }
        return permissionRepository.save(updatePermission);
    }

    public List<Permission> batchDelete(Long[] ids) {
        return Arrays.stream(ids).map(this::delete).collect(Collectors.toList());
    }

    public Permission delete(Long id) {
        Permission permission = findById(id, StrUtil.format("数据编号为【{}】的菜单信息不存在，无法进行删除操作", id));
        if (GlobalConstants.DATA_KEEP_STATE.equals(permission.getState())) {
            throw new BadRequestException(StrUtil.format("数据编号为【{}】的菜单信息为系统保留数据，无法进行删除操作", id));
        }
        permission.setState(GlobalConstants.DATA_DELETE_STATE);
        batchUpdateState(permission.getLevel(), id);
        return permissionRepository.save(permission);
    }

    private String getLevel(Long id) {
        return permissionRepository.findById(id).map(Permission::getLevel).orElse(null);
    }

    private void batchUpdateLevel(String beforeLevel, String afterLevel, Long id) {
        permissionRepository.findByLevelStartsWith(LevelUtil.calculateLevel(beforeLevel, id)).forEach(permission -> {
            permission.setLevel(StringUtils.replaceOnce(permission.getLevel(), beforeLevel, afterLevel));
            permissionRepository.save(permission);
        });
    }

    private void batchUpdateState(String level, Long id) {
        permissionRepository.findByLevelStartsWith(LevelUtil.calculateLevel(level, id)).forEach(permission -> {
            permission.setState(GlobalConstants.DATA_DELETE_STATE);
            permissionRepository.save(permission);
        });
    }
}
