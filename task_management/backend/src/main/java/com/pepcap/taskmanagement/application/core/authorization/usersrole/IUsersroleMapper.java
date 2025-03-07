package com.pepcap.taskmanagement.application.core.authorization.usersrole;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.pepcap.taskmanagement.domain.core.authorization.role.Role;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;
import com.pepcap.taskmanagement.application.core.authorization.usersrole.dto.*;
import com.pepcap.taskmanagement.domain.core.authorization.usersrole.Usersrole;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IUsersroleMapper {
   Usersrole createUsersroleInputToUsersrole(CreateUsersroleInput usersroleDto);
   
   @Mappings({ 
   @Mapping(source = "users.id", target = "usersId"),  
   @Mapping(source = "users.username", target = "usersDescriptiveField"),
   @Mapping(source = "role.displayName", target = "roleDescriptiveField"),                   
   @Mapping(source = "role.id", target = "roleId")                   
   }) 
   CreateUsersroleOutput usersroleToCreateUsersroleOutput(Usersrole entity);
   
    Usersrole updateUsersroleInputToUsersrole(UpdateUsersroleInput usersroleDto);
    
    @Mappings({ 
    @Mapping(source = "entity.role.displayName", target = "roleDescriptiveField"),                    
    @Mapping(source = "entity.users.id", target = "usersDescriptiveField"),                    
   	}) 
   	UpdateUsersroleOutput usersroleToUpdateUsersroleOutput(Usersrole entity);
   	@Mappings({ 
   	@Mapping(source = "entity.role.displayName", target = "roleDescriptiveField"),                    
   	@Mapping(source = "entity.users.id", target = "usersDescriptiveField"),                    
   	}) 
   	FindUsersroleByIdOutput usersroleToFindUsersroleByIdOutput(Usersrole entity);


   @Mappings({
   @Mapping(source = "foundUsersrole.roleId", target = "usersroleRoleId"),
   @Mapping(source = "foundUsersrole.usersId", target = "usersroleUsersId"),
   })
   GetRoleOutput roleToGetRoleOutput(Role role, Usersrole foundUsersrole);
   
   @Mappings({
   @Mapping(source = "users.role", target = "role"),                  
   @Mapping(source = "foundUsersrole.roleId", target = "usersroleRoleId"),
   @Mapping(source = "foundUsersrole.usersId", target = "usersroleUsersId"),
   })
   GetUsersOutput usersToGetUsersOutput(Users users, Usersrole foundUsersrole);
   
}

