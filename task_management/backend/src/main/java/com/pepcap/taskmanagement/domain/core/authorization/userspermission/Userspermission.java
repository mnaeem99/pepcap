package com.pepcap.taskmanagement.domain.core.authorization.userspermission;
import javax.persistence.*;
import java.time.*;
import com.pepcap.taskmanagement.domain.core.authorization.permission.Permission;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;
import com.pepcap.taskmanagement.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "userspermissionEntity")
@Table(name = "userspermission")
@IdClass(UserspermissionId.class)
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Userspermission extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include() 
    @Column(name = "permission_id", nullable = false)
    private Long permissionId;
    
    @Basic
    @Column(name = "revoked", nullable = true)
    private Boolean revoked;
    
    @Id
    @EqualsAndHashCode.Include() 
    @Column(name = "users_id", nullable = false)
    private Integer usersId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "permission_id", insertable=false, updatable=false)
    private Permission permission;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "users_id", insertable=false, updatable=false)
    private Users users;


}



