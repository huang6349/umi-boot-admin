package org.umi.boot.service;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umi.boot.commons.exception.BadRequestException;
import org.umi.boot.commons.exception.DataAlreadyExistException;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.domain.*;
import org.umi.boot.repository.UserRepository;
import org.umi.boot.security.SecurityUtils;
import org.umi.boot.web.rest.manage.UserAttribute;
import org.umi.boot.web.rest.manage.UserIdAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private DictService dictService;

    @Transactional(readOnly = true)
    public User findById(Long id, String errorMessage) {
        if (id == null) throw new BadRequestException(errorMessage);
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) throw new BadRequestException(errorMessage);
        return user.get();
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        return SecurityUtils.getCurrentUserUsername().flatMap(userRepository::findByUsername).orElse(null);
    }

    public User create(UserAttribute attribute) {
        if (userRepository.findByUsername(attribute.getUsername()).isPresent()) {
            throw new DataAlreadyExistException(StrUtil.format("帐号为【{}】的用户信息已经存在了", attribute.getUsername()));
        }
        if (attribute.getEmail() != null && userRepository.findByEmail(attribute.getEmail()).isPresent()) {
            throw new DataAlreadyExistException(StrUtil.format("邮箱为【{}】的用户信息已经存在了", attribute.getEmail()));
        }
        if (attribute.getMobilePhone() != null && userRepository.findByMobilePhone(attribute.getMobilePhone()).isPresent()) {
            throw new DataAlreadyExistException(StrUtil.format("手机号为【{}】的用户信息已经存在了", attribute.getMobilePhone()));
        }
        Dict sex = dictService.findById(attribute.getSexId(), 10000L, StrUtil.format("数据编号为【{}】的性别类型不存在，无法进行新增操作", attribute.getSexId()));
        User user = UserAttribute.adapt(attribute);
        user.setPassword("123456"); // TODO: 加密
        UserInfo info = user.getInfo();
        info.setSex(sex);
        user.setInfo(info);
        user.setAuthorities(setAuthorities(attribute.getAuthoritieIds()));
        user.setState(GlobalConstants.DATA_NORMAL_STATE);
        return userRepository.save(user);
    }

    public User update(UserIdAttribute attribute) {
        User user = findById(attribute.getId(), StrUtil.format("数据编号为【{}】的用户信息不存在，无法进行修改操作", attribute.getId()));
        if (GlobalConstants.DATA_KEEP_STATE.equals(user.getState())) {
            Set<Long> authorities = user.getAuthorities().stream().map(Authority::getId).collect(Collectors.toSet());
            if (!CollectionUtils.isEqualCollection(authorities, attribute.getAuthoritieIds())) {
                throw new BadRequestException("该用户为系统保留用户，无法进行角色修改操作");
            }
        }
        if (!StringUtils.equals(attribute.getUsername(), user.getUsername())) {
            throw new BadRequestException("用户帐号不允许修改");
        }
        if (attribute.getEmail() != null && userRepository.findByEmailAndIdNot(attribute.getEmail(), attribute.getId()).isPresent()) {
            throw new DataAlreadyExistException(StrUtil.format("邮箱为【{}】的用户信息已经存在了", attribute.getEmail()));
        }
        if (attribute.getMobilePhone() != null && userRepository.findByMobilePhoneAndIdNot(attribute.getMobilePhone(), attribute.getId()).isPresent()) {
            throw new DataAlreadyExistException(StrUtil.format("手机号为【{}】的用户信息已经存在了", attribute.getMobilePhone()));
        }
        Dict sex = dictService.findById(attribute.getSexId(), 10000L, StrUtil.format("数据编号为【{}】的性别类型不存在，无法进行修改操作", attribute.getSexId()));
        User updateUser = UserIdAttribute.adapt(attribute, user);
        UserInfo info = updateUser.getInfo();
        info.setSex(sex);
        updateUser.setInfo(info);
        updateUser.setAuthorities(setAuthorities(attribute.getAuthoritieIds()));
        return userRepository.save(updateUser);
    }

    public List<User> batchDelete(Long[] ids) {
        return Arrays.stream(ids).map(this::delete).collect(Collectors.toList());
    }

    public User delete(Long id) {
        User user = findById(id, StrUtil.format("数据编号为【{}】的用户信息不存在，无法进行删除操作", id));
        if (GlobalConstants.DATA_KEEP_STATE.equals(user.getState())) {
            throw new BadRequestException("该用户为系统保留用户，无法进行删除操作");
        }
        return updateState(user, GlobalConstants.DATA_DELETE_STATE);
    }

    public List<User> batchEnable(Long[] ids) {
        return Arrays.stream(ids).map(this::enable).collect(Collectors.toList());
    }

    public User enable(Long id) {
        User user = findById(id, StrUtil.format("数据编号为【{}】的用户信息不存在，无法进行启用操作", id));
        if (GlobalConstants.DATA_KEEP_STATE.equals(user.getState())) {
            throw new BadRequestException("该用户为系统保留用户，无法进行启用操作");
        }
        return updateState(user, GlobalConstants.DATA_NORMAL_STATE);
    }

    public List<User> batchDisable(Long[] ids) {
        return Arrays.stream(ids).map(this::disable).collect(Collectors.toList());
    }

    public User disable(Long id) {
        User user = findById(id, StrUtil.format("数据编号为【{}】的用户信息不存在，无法进行禁用操作", id));
        if (GlobalConstants.DATA_KEEP_STATE.equals(user.getState())) {
            throw new BadRequestException("该用户为系统保留用户，无法进行禁用操作");
        }
        return updateState(user, GlobalConstants.DATA_DISABLED_STATE);
    }

    private Set<Authority> setAuthorities(Set<Long> authoritieIds) {
        return authoritieIds.stream().map(authoritieId -> authorityService.findById(authoritieId, StrUtil.format("数据编号为【{}】的角色信息不存在，无法进行角色赋值操作", authoritieId))).collect(Collectors.toSet());
    }

    private User updateState(User user, Byte state) {
        UserInfo info = user.getInfo();
        info.setState(state);
        user.setInfo(info);
        user.setState(state);
        return userRepository.save(user);
    }
}
