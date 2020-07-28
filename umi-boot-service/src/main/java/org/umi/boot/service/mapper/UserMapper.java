package org.umi.boot.service.mapper;

import org.springframework.stereotype.Service;
import org.umi.boot.domain.User;
import org.umi.boot.web.rest.vm.UserVM;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public List<UserVM> adapt(List<User> users) {
        return users.stream().filter(Objects::nonNull).map(this::adapt).collect(Collectors.toList());
    }

    public UserVM adapt(User user) {
        return UserVM.adapt(user);
    }
}
