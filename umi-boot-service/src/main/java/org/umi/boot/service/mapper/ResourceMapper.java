package org.umi.boot.service.mapper;

import org.springframework.stereotype.Service;
import org.umi.boot.domain.Resource;
import org.umi.boot.web.rest.vm.ResourceVM;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ResourceMapper {

    public List<ResourceVM> adapt(List<Resource> resources) {
        return resources.stream().filter(Objects::nonNull).map(this::adapt).collect(Collectors.toList());
    }

    public ResourceVM adapt(Resource resource) {
        return ResourceVM.adapt(resource);
    }
}
