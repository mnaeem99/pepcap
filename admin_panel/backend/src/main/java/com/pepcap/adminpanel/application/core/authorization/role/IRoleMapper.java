package com.pepcap.adminpanel.application.core.authorization.role;

import org.mapstruct.Mapper;
import com.pepcap.adminpanel.application.core.authorization.role.dto.*;
import com.pepcap.adminpanel.domain.core.authorization.role.Role;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IRoleMapper {
   Role createRoleInputToRole(CreateRoleInput roleDto);
   CreateRoleOutput roleToCreateRoleOutput(Role entity);
   
    Role updateRoleInputToRole(UpdateRoleInput roleDto);
    
   	UpdateRoleOutput roleToUpdateRoleOutput(Role entity);
   	FindRoleByIdOutput roleToFindRoleByIdOutput(Role entity);


 	FindRoleByNameOutput roleToFindRoleByNameOutput(Role entity);
 	
}

