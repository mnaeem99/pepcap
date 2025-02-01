package com.pepcap.adminpanel.domain.core.authorization.users;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import com.pepcap.adminpanel.domain.core.authorization.tokenverification.Tokenverification;
import com.pepcap.adminpanel.domain.core.authorization.userspermission.Userspermission;
import com.pepcap.adminpanel.domain.core.authorization.userspreference.Userspreference;
import com.pepcap.adminpanel.domain.core.authorization.usersrole.Usersrole;
import com.pepcap.adminpanel.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "usersEntity")
@Table(name = "users")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Users extends AbstractEntity {

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "email", nullable = false,length =100)
    private String email;

    @Basic
    @Column(name = "first_name", nullable = false,length =50)
    private String firstName;

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    @Basic
    @Column(name = "is_email_confirmed", nullable = true)
    private Boolean isEmailConfirmed;
    
    @Basic
    @Column(name = "last_name", nullable = false,length =50)
    private String lastName;

    @Basic
    @Column(name = "password", nullable = false,length =255)
    private String password;

    @Basic
    @Column(name = "phone_number", nullable = true,length =50)
    private String phoneNumber;

    @Basic
    @Column(name = "role", nullable = false,length =50)
    private String role;

    @Basic
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Basic
    @Column(name = "username", nullable = false,length =50)
    private String username;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tokenverification> tokenverificationsSet = new HashSet<Tokenverification>();
    
    public void addTokenverifications(Tokenverification tokenverifications) {        
    	tokenverificationsSet.add(tokenverifications);

        tokenverifications.setUsers(this);
    }
    public void removeTokenverifications(Tokenverification tokenverifications) {
        tokenverificationsSet.remove(tokenverifications);
        
        tokenverifications.setUsers(null);

    }
    
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Userspermission> userspermissionsSet = new HashSet<Userspermission>();
    
    public void addUserspermissions(Userspermission userspermissions) {        
    	userspermissionsSet.add(userspermissions);

        userspermissions.setUsers(this);
    }
    public void removeUserspermissions(Userspermission userspermissions) {
        userspermissionsSet.remove(userspermissions);
        
        userspermissions.setUsers(null);

    }
    

    @OneToOne(mappedBy = "users", cascade=CascadeType.MERGE)
    private Userspreference userspreference;

    public void setUserspreference(Userspreference userspreference) {
    	if (userspreference == null) {
            if (this.userspreference != null) {
                this.userspreference.setUsers(null);
            }
        }
        else {
            userspreference.setUsers(this);
        }
        this.userspreference = userspreference;
    }
    
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Usersrole> usersrolesSet = new HashSet<Usersrole>();
    
    public void addUsersroles(Usersrole usersroles) {        
    	usersrolesSet.add(usersroles);

        usersroles.setUsers(this);
    }
    public void removeUsersroles(Usersrole usersroles) {
        usersrolesSet.remove(usersroles);
        
        usersroles.setUsers(null);

    }
    

}



