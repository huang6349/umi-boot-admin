package org.umi.boot.service.mapper;

import org.springframework.stereotype.Service;
import org.umi.boot.domain.Permission;
import org.umi.boot.web.rest.vm.PermissionVM;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PermissionMapper {

    public List<PermissionVM> adapt(List<Permission> permissions) {
        return permissions.stream().filter(Objects::nonNull).map(this::adapt).collect(Collectors.toList());
    }

    public PermissionVM adapt(Permission permission) {
        return PermissionVM.adapt(permission);
    }
}
