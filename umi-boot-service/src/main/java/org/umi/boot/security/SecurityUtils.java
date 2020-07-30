package org.umi.boot.security;

import java.util.Optional;

public interface SecurityUtils {

    static Optional<String> getCurrentUserUsername() {
        // TODO: 获取当前用户的账号信息
        return Optional.of("admin");
    }
}
