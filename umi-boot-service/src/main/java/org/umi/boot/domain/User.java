package org.umi.boot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "TB_USER")
@Where(clause = "DATA_STATE <> 0")
public class User extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = -5873184873496716826L;

    @Column(length = 50, nullable = false)
    private String username;

    private String email;

    private String mobilePhone;

    @JsonIgnore
    @Column(length = 60, nullable = false)
    private String password;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "USER_INFO_ID")
    @Where(clause = "DATA_STATE <> 0")
    @NotFound(action = NotFoundAction.IGNORE)
    private UserInfo info;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TB_USER_AUTHORITY",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID"))
    @Where(clause = "DATA_STATE <> 0")
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Authority> authorities = Sets.newHashSet();
}
