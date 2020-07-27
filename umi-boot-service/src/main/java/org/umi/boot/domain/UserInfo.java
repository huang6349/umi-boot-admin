package org.umi.boot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "TB_USER_INFO")
@Where(clause = "DATA_STATE <> 0")
public class UserInfo extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = -995875514792405433L;

    @Column(length = 50, nullable = false)
    private String nickname;

    private String realname;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SEX_ID")
    @Where(clause = "DATA_STATE <> 0")
    @NotFound(action = NotFoundAction.IGNORE)
    private Dict sex;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    private String idCard;

    private String email;

    private String mobilePhone;
}
