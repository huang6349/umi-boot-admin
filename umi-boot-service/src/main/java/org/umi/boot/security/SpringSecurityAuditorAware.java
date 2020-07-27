package org.umi.boot.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.umi.boot.config.GlobalConstants;

import java.util.Optional;

@Component("springSecurityAuditorAware")
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        // TODO: 获取当前用户的账号信息
        return Optional.of(GlobalConstants.SYSTEM_ACCOUNT);
    }
}
