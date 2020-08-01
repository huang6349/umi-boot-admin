package org.umi.boot.web.rest.vm;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.umi.boot.domain.Authority;
import org.umi.boot.domain.User;
import org.umi.boot.domain.UserInfo;

import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@ApiModel(description = "用户信息（显示模型）", value = "UserVM")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVM extends AbstractIdAuditingVM {

    private String username;

    private String email;

    private String mobilePhone;

    private String nickname;

    private String realname;

    private Long sexId;

    private String sexText;

    private Date birthday;

    private String idCard;

    private Set<Long> authoritieIds = Sets.newHashSet();

    private Set<String> authoritieNames = Sets.newHashSet();

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public static UserVM adapt(User user) {
        if (user == null) return null;
        UserVM vm = new UserVM();
        BeanUtils.copyProperties(user, vm);
        if (user.getInfo() != null) {
            UserInfo info = user.getInfo();
            BeanUtils.copyProperties(info, vm, "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate", "state", "id");
            if (info.getSex() != null) {
                vm.setSexId(info.getSex().getId());
                vm.setSexText(info.getSex().getName());
            }
        }
        vm.setAuthoritieIds(user.getAuthorities().stream().map(Authority::getId).collect(Collectors.toSet()));
        vm.setAuthoritieNames(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
        return vm;
    }
}
