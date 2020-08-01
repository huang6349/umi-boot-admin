package org.umi.boot.security;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        return userRepository.findByUsername(login)
                .map(user -> createUserDetails(login, user))
                .orElseThrow(() -> new UsernameNotFoundException(StrUtil.format("User {} was not found in the database", login)));
    }

    private User createUserDetails(String login, org.umi.boot.domain.User user) {
        if (GlobalConstants.DATA_DELETE_STATE.equals(user.getState())) {
            throw new UsernameNotFoundException(StrUtil.format("User {} has been removed from the database", login));
        }
        if (GlobalConstants.DATA_DISABLED_STATE.equals(user.getState())) {
            throw new UserNotActivatedException();
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getCode()))
                .collect(Collectors.toList());
        return new User(login, user.getPassword(), grantedAuthorities);
    }
}
