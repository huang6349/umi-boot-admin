package org.umi.boot.service;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umi.boot.commons.exception.BadRequestException;
import org.umi.boot.commons.exception.DataAlreadyExistException;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.domain.Authority;
import org.umi.boot.domain.Permission;
import org.umi.boot.repository.AuthorityRepository;
import org.umi.boot.web.rest.manage.AuthorityManage;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PermissionService permissionService;

    @Transactional(readOnly = true)
    public Authority findById(Long id, String errorMessage) {
        if (id == null) throw new BadRequestException(errorMessage);
        Optional<Authority> permission = authorityRepository.findById(id);
        if (!permission.isPresent()) throw new BadRequestException(errorMessage);
        return permission.get();
    }

    public Authority create(AuthorityManage manage) {
        if (!authorityRepository.findByName(manage.getName()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("名称为【{}】的角色信息已经存在了", manage.getName()));
        }
        String formatCode = StrUtil.format("ROLE_{}", manage.getCode());
        if (GlobalConstants.AUTHORITY_ANONYMOUS.equals(formatCode) || !authorityRepository.findByCode(formatCode).isEmpty()) {
            throw new DataAlreadyExistException("唯一标识码为【{}】的角色信息已经存在了", manage.getCode());
        }
        Authority authority = new Authority();
        BeanUtils.copyProperties(manage, authority);
        authority.setCode(formatCode);
        authority.setState(GlobalConstants.DATA_NORMAL_STATE);
        return authorityRepository.save(authority);
    }

    public Authority update(AuthorityManage manage) {
        Authority authority = findById(manage.getId(), StrUtil.format("数据编号为【{}】的角色信息不存在，无法进行修改操作", manage.getId()));
        String formatCode = StrUtil.format("ROLE_{}", manage.getCode());
        if (GlobalConstants.DATA_KEEP_STATE.equals(authority.getState())) {
            if (!StringUtils.equals(StringUtils.trimToNull(manage.getName()), StringUtils.trimToNull(authority.getName()))) {
                throw new BadRequestException("该角色为系统保留角色，无法进行名称修改操作");
            }
            if (!StringUtils.equals(StringUtils.trimToNull(formatCode), StringUtils.trimToNull(authority.getCode()))) {
                throw new BadRequestException("该角色为系统保留角色，无法进行唯一标识码修改操作");
            }
        }
        if (!authorityRepository.findByNameAndIdNot(manage.getName(), manage.getId()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("名称为【{}】的角色信息已经存在了", manage.getName()));
        }
        if (GlobalConstants.AUTHORITY_ANONYMOUS.equals(formatCode) || !authorityRepository.findByCodeAndIdNot(formatCode, manage.getId()).isEmpty()) {
            throw new DataAlreadyExistException("唯一标识码为【{}】的角色信息已经存在了", manage.getCode());
        }
        BeanUtils.copyProperties(manage, authority);
        authority.setCode(formatCode);
        return authorityRepository.save(authority);
    }

    public Authority update(Long id, Set<Long> permissionIds) {
        Authority authority = findById(id, StrUtil.format("数据编号为【{}】的角色信息不存在，无法进行菜单修改操作", id));
        if (GlobalConstants.DATA_KEEP_STATE.equals(authority.getState())) {
            throw new BadRequestException("该角色为系统保留角色，无法进行菜单修改操作");
        }
        authority.setPermissions(setPermissions(permissionIds));
        return authorityRepository.save(authority);
    }

    public List<Authority> batchDelete(Long[] ids) {
        return Arrays.stream(ids).map(this::delete).collect(Collectors.toList());
    }

    public Authority delete(Long id) {
        Authority authority = findById(id, StrUtil.format("数据编号为【{}】的角色信息不存在，无法进行删除操作", id));
        if (GlobalConstants.DATA_KEEP_STATE.equals(authority.getState())) {
            throw new BadRequestException(StrUtil.format("数据编号为【{}】的角色信息为系统保留数据，无法进行删除操作", id));
        }
        authority.setState(GlobalConstants.DATA_DELETE_STATE);
        return authorityRepository.save(authority);
    }

    private Set<Permission> setPermissions(Set<Long> permissionIds) {
        return permissionIds.stream().map(permissionId -> permissionService.findById(permissionId, StrUtil.format("数据编号为【{}】的菜单信息不存在，无法进行菜单修改操作", permissionId))).collect(Collectors.toSet());
    }
}
