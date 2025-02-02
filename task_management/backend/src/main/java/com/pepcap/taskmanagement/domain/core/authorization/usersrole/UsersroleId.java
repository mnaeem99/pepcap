package com.pepcap.taskmanagement.domain.core.authorization.usersrole;

import java.time.*;
import javax.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter
@NoArgsConstructor
public class UsersroleId implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Long roleId;
    private Integer usersId;
    
    public UsersroleId(Long roleId,Integer usersId) {
 	this.roleId = roleId;
 	this.usersId = usersId;
    }
    
}
