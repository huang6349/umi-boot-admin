package org.umi.boot.service.mapper;

import org.springframework.stereotype.Service;
import org.umi.boot.commons.utils.LevelUtil;
import org.umi.boot.commons.utils.impl.DefaultLevelUtil;
import org.umi.boot.domain.Permission;
import org.umi.boot.web.rest.vm.PermissionLevelVM;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PermissionLevelMapper {

    private LevelUtil<PermissionLevelVM> levelUtil = new DefaultLevelUtil<>();

    public List<PermissionLevelVM> adaptToTree(List<Permission> permissions) {
        return levelUtil.listToTree(this.adapt(permissions));
    }

    public List<PermissionLevelVM> adapt(List<Permission> permissions) {
        return permissions.stream().filter(Objects::nonNull).map(this::adapt).collect(Collectors.toList());
    }

    public PermissionLevelVM adapt(Permission permission) {
        return PermissionLevelVM.adapt(permission);
    }
}
