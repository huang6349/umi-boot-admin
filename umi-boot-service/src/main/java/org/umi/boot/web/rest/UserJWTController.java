package org.umi.boot.web.rest;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.umi.boot.commons.info.Info;
import org.umi.boot.commons.info.InfoStructure;
import org.umi.boot.security.jwt.JWTFilter;
import org.umi.boot.security.jwt.TokenProvider;
import org.umi.boot.web.rest.query.LoginQuery;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "用户管理", value = "UserJWTController")
@RestController
@RequestMapping("/api")
public class UserJWTController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @ApiOperation(value = "获取授权令牌")
    @PostMapping("/authenticate")
    public InfoStructure authorize(@Valid @RequestBody LoginQuery query, HttpServletResponse httpServletResponse) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(query.getUsername(), query.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        httpServletResponse.setHeader(JWTFilter.AUTHORIZATION_HEADER, StrUtil.format("Bearer {}", jwt));
        return Info.success(new JWTToken(jwt));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class JWTToken {

        @JsonProperty("id_token")
        private String idToken;
    }
}
