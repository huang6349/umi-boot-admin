package org.umi.boot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.umi.boot.service.ResourceService;
import org.umi.boot.service.mapper.ResourceMapper;
import org.umi.boot.web.rest.vm.ResourceVM;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component("rbacService")
public class DefaultRbacService implements RbacService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public boolean hasPermission() {
        if (!SecurityUtils.isAuthenticated()) {
            return false;
        }
        for (List<String> rbacIgnoring : securityProperties.getRbacIgnorings()) {
            if (new AntPathRequestMatcher(rbacIgnoring.get(1), rbacIgnoring.get(0)).matches(httpServletRequest)) {
                return true;
            }
        }
        List<ResourceVM> vms = resourceMapper.adapt(resourceService.getCurrentUserResources());
        if (vms.isEmpty()) {
            return false;
        }
        for (ResourceVM vm : vms) {
            if (new AntPathRequestMatcher(vm.getPattern(), vm.getMethodText()).matches(httpServletRequest)) {
                return true;
            }
        }
        return false;
    }
}
