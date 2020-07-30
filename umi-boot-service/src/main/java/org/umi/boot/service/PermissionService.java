package org.umi.boot.service;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
import org.umi.boot.web.rest.manage.PermissionManage;

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

    public Permission create(PermissionManage manage) {
        if (!permissionRepository.findByName(manage.getName()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("名称为【{}】的菜单信息已经存在了", manage.getName()));
        }
        Permission permission = new Permission();
        BeanUtils.copyProperties(manage, permission);
        permission.setState(GlobalConstants.DATA_NORMAL_STATE);
        permission.setLevel(LevelUtil.calculateLevel(getLevel(manage.getPid()), manage.getPid()));
        return permissionRepository.save(permission);
    }

    public Permission update(PermissionManage manage) {
        Permission permission = findById(manage.getId(), StrUtil.format("数据编号为【{}】的菜单信息不存在，无法进行修改操作", manage.getId()));
        if (GlobalConstants.DATA_KEEP_STATE.equals(permission.getState())) {
            if (!StringUtils.equals(StringUtils.trimToNull(manage.getName()), StringUtils.trimToNull(permission.getName()))) {
                throw new BadRequestException("该菜单为系统保留菜单，无法进行菜单名称修改操作");
            }
            if (!StringUtils.equals(StringUtils.trimToNull(manage.getPath()), StringUtils.trimToNull(permission.getPath()))) {
                throw new BadRequestException("该菜单为系统保留菜单，无法进行菜单路径修改操作");
            }
            if (!StringUtils.equals(StringUtils.trimToNull(manage.getIcon()), StringUtils.trimToNull(permission.getIcon()))) {
                throw new BadRequestException("该菜单为系统保留菜单，无法进行菜单图标修改操作");
            }
        }
        if (!permissionRepository.findByNameAndIdNot(manage.getName(), manage.getId()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("名称为【{}】的菜单信息已经存在了", manage.getName()));
        }
        String beforeLevel = permission.getLevel();
        String afterLevel = LevelUtil.calculateLevel(getLevel(manage.getPid()), manage.getPid());
        if (StringUtils.startsWith(afterLevel, LevelUtil.calculateLevel(beforeLevel, manage.getId()))) {
            throw new BadRequestException("不允许将自己设置为自己的子级");
        }
        BeanUtils.copyProperties(manage, permission);
        if (!StringUtils.equals(beforeLevel, afterLevel)) {
            permission.setLevel(afterLevel);
            batchUpdateLevel(beforeLevel, afterLevel, manage.getId());
        }
        return permissionRepository.save(permission);
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
