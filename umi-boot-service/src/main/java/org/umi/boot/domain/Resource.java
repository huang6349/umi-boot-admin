package org.umi.boot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "TB_RESOURCE")
@Where(clause = "DATA_STATE <> 0")
public class Resource extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = 7896445883698741826L;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERMISSION_ID")
    @Where(clause = "DATA_STATE <> 0")
    @NotFound(action = NotFoundAction.IGNORE)
    private Permission permission;

    @Column(name = "RESOURCE_PATTERN", nullable = false)
    private String pattern;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "METHOD_ID")
    @Where(clause = "DATA_STATE <> 0")
    @NotFound(action = NotFoundAction.IGNORE)
    private Dict method;

    @Column(name = "RESOURCE_DESC")
    private String desc;
}
