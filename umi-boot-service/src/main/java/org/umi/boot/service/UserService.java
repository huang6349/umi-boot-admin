package org.umi.boot.service;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umi.boot.commons.exception.BadRequestException;
import org.umi.boot.commons.exception.DataAlreadyExistException;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.domain.*;
import org.umi.boot.repository.UserRepository;
import org.umi.boot.web.rest.manage.UserManage;

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

    public User create(UserManage manage) {
        if (!userRepository.findByUsername(manage.getUsername()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("帐号为【{}】的用户信息已经存在了", manage.getUsername()));
        }
        if (manage.getEmail() != null && !userRepository.findByEmail(manage.getEmail()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("邮箱为【{}】的用户信息已经存在了", manage.getEmail()));
        }
        if (manage.getMobilePhone() != null && !userRepository.findByMobilePhone(manage.getMobilePhone()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("手机号为【{}】的用户信息已经存在了", manage.getMobilePhone()));
        }
        Dict sex = dictService.findById(manage.getSexId(), 10000L, StrUtil.format("数据编号为【{}】的性别类型不存在，无法进行新增操作", manage.getSexId()));
        UserInfo info = new UserInfo();
        BeanUtils.copyProperties(manage, info, "id");
        info.setSex(sex);
        User user = new User();
        BeanUtils.copyProperties(manage, user);
        user.setPassword("123456"); // TODO: 加密
        user.setInfo(info);
        user.setAuthorities(setAuthorities(manage.getAuthoritieIds()));
        user.setState(GlobalConstants.DATA_NORMAL_STATE);
        return userRepository.save(user);
    }

    public User update(UserManage manage) {
        User user = findById(manage.getId(), StrUtil.format("数据编号为【{}】的用户信息不存在，无法进行修改操作", manage.getId()));
        if (GlobalConstants.DATA_KEEP_STATE.equals(user.getState())) {
            Set<Long> authorities = user.getAuthorities().stream().map(Authority::getId).collect(Collectors.toSet());
            if (!CollectionUtils.isEqualCollection(authorities, manage.getAuthoritieIds())) {
                throw new BadRequestException("该用户为系统保留用户，无法进行角色修改操作");
            }
        }
        if (!StringUtils.equals(manage.getUsername(), user.getUsername())) {
            throw new BadRequestException("用户帐号不允许修改");
        }
        if (manage.getEmail() != null && !userRepository.findByEmailAndIdNot(manage.getEmail(), manage.getId()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("邮箱为【{}】的用户信息已经存在了", manage.getEmail()));
        }
        if (manage.getMobilePhone() != null && !userRepository.findByMobilePhoneAndIdNot(manage.getMobilePhone(), manage.getId()).isEmpty()) {
            throw new DataAlreadyExistException(StrUtil.format("手机号为【{}】的用户信息已经存在了", manage.getMobilePhone()));
        }
        Dict sex = dictService.findById(manage.getSexId(), 10000L, StrUtil.format("数据编号为【{}】的性别类型不存在，无法进行修改操作", manage.getSexId()));
        UserInfo info = user.getInfo();
        BeanUtils.copyProperties(manage, info, "id");
        info.setSex(sex);
        BeanUtils.copyProperties(manage, user);
        user.setInfo(info);
        user.setAuthorities(setAuthorities(manage.getAuthoritieIds()));
        return userRepository.save(user);
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
