package org.umi.boot.service.mapper;

import org.springframework.stereotype.Service;
import org.umi.boot.domain.Authority;
import org.umi.boot.web.rest.vm.AuthorityVM;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthorityMapper {

    public List<AuthorityVM> adapt(List<Authority> authorities) {
        return authorities.stream().filter(Objects::nonNull).map(this::adapt).collect(Collectors.toList());
    }

    public AuthorityVM adapt(Authority authority) {
        return AuthorityVM.adapt(authority);
    }
}
