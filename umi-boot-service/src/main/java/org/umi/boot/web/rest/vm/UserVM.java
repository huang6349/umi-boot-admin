package org.umi.boot.web.rest.vm;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

@ApiModel("用户视图模型")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVM extends AbstractIdAuditingVM {

    @ApiModelProperty("用户帐号")
    private String username;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户手机号")
    private String mobilePhone;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户真实姓名")
    private String realname;

    @ApiModelProperty("用户性别")
    private Long sexId;

    @ApiModelProperty("用户性别（文字）")
    private String sexText;

    @ApiModelProperty("用户生日")
    private Date birthday;

    @ApiModelProperty("用户身份证号码")
    private String idCard;

    @ApiModelProperty("用户角色编号列表")
    private Set<Long> authoritieIds = Sets.newHashSet();

    @ApiModelProperty("用户角色名称列表")
    private Set<String> authoritieNames = Sets.newHashSet();

    @ApiModelProperty("最后修改人")
    private String lastModifiedBy;

    @ApiModelProperty("最后修改时间")
    private Instant lastModifiedDate;

    public static UserVM adapt(User user) {
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
