package com.pepcap.taskmanagement.domain.core.authorization.tokenverification;

import java.time.*;
import javax.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter
@NoArgsConstructor
public class TokenverificationId implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private String tokenType;
    private Integer usersId;
    
    public TokenverificationId(String tokenType,Integer usersId) {
 	this.tokenType = tokenType;
 	this.usersId = usersId;
    }
    
}
