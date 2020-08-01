package org.umi.boot.security.jwt;

import cn.hutool.core.date.DateTime;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.umi.boot.config.GlobalConstants;
import org.umi.boot.domain.Authority;
import org.umi.boot.repository.UserRepository;
import org.umi.boot.security.SecurityProperties;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";

    private String key;

    private Long tokenValidityInMilliseconds;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void afterPropertiesSet() {
        this.key = securityProperties.getSecret();
        this.tokenValidityInMilliseconds = 1000 * securityProperties.getTokenValidityInSeconds();
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .sorted()
                .collect(Collectors.joining(","));

        long now = DateTime.now().getTime();
        JwtBuilder builder = Jwts.builder();
        builder.setSubject(authentication.getName());
        builder.claim(AUTHORITIES_KEY, authorities);
        builder.signWith(SignatureAlgorithm.HS512, key);
        builder.setExpiration(DateTime.of(now + this.tokenValidityInMilliseconds));
        builder.setNotBefore(DateTime.of(now));
        return builder.compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        Optional<org.umi.boot.domain.User> optional = userRepository.findByUsername(claims.getSubject());
        if (!optional.isPresent()) return null;

        org.umi.boot.domain.User user = optional.get();
        if (GlobalConstants.DATA_DISABLED_STATE.equals(user.getState())) return null;
        String authorities = claims.get(AUTHORITIES_KEY).toString();
        if (!user.getAuthorities().stream().map(Authority::getCode).sorted().collect(Collectors.joining(",")).equals(authorities)) {
            return null;
        }

        Collection<? extends GrantedAuthority> grantedAuthorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", grantedAuthorities);

        return new UsernamePasswordAuthenticationToken(principal, token, grantedAuthorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("无效的 JWT 令牌");
        } catch (ExpiredJwtException e) {
            log.info("已过期的 JWT 令牌");
        } catch (UnsupportedJwtException e) {
            log.info("不支持的 JWT 令牌");
        } catch (IllegalArgumentException e) {
            log.info("参数不存在的 JWT 令牌");
        }
        return false;
    }
}
