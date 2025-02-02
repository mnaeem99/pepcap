package com.pepcap.taskmanagement.application.core.timesheets;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.pepcap.taskmanagement.domain.core.tasks.Tasks;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;
import com.pepcap.taskmanagement.application.core.timesheets.dto.*;
import com.pepcap.taskmanagement.domain.core.timesheets.Timesheets;
import java.time.*;

@Mapper(componentModel = "spring")
public interface ITimesheetsMapper {
   Timesheets createTimesheetsInputToTimesheets(CreateTimesheetsInput timesheetsDto);
   
   @Mappings({ 
   @Mapping(source = "entity.tasks.id", target = "taskId"),                   
   @Mapping(source = "entity.tasks.id", target = "tasksDescriptiveField"),                    
   @Mapping(source = "entity.users.id", target = "userId"),                   
   @Mapping(source = "entity.users.id", target = "usersDescriptiveField"),                    
   }) 
   CreateTimesheetsOutput timesheetsToCreateTimesheetsOutput(Timesheets entity);
   
    Timesheets updateTimesheetsInputToTimesheets(UpdateTimesheetsInput timesheetsDto);
    
    @Mappings({ 
    @Mapping(source = "entity.tasks.id", target = "taskId"),                   
    @Mapping(source = "entity.tasks.id", target = "tasksDescriptiveField"),                    
    @Mapping(source = "entity.users.id", target = "userId"),                   
    @Mapping(source = "entity.users.id", target = "usersDescriptiveField"),                    
   	}) 
   	UpdateTimesheetsOutput timesheetsToUpdateTimesheetsOutput(Timesheets entity);
   	@Mappings({ 
   	@Mapping(source = "entity.tasks.id", target = "taskId"),                   
   	@Mapping(source = "entity.tasks.id", target = "tasksDescriptiveField"),                    
   	@Mapping(source = "entity.users.id", target = "userId"),                   
   	@Mapping(source = "entity.users.id", target = "usersDescriptiveField"),                    
   	}) 
   	FindTimesheetsByIdOutput timesheetsToFindTimesheetsByIdOutput(Timesheets entity);


   @Mappings({
   @Mapping(source = "tasks.createdAt", target = "createdAt"),                  
   @Mapping(source = "tasks.id", target = "id"),                  
   @Mapping(source = "foundTimesheets.id", target = "timesheetsId"),
   })
   GetTasksOutput tasksToGetTasksOutput(Tasks tasks, Timesheets foundTimesheets);
   
   @Mappings({
   @Mapping(source = "users.createdAt", target = "createdAt"),                  
   @Mapping(source = "users.id", target = "id"),                  
   @Mapping(source = "foundTimesheets.id", target = "timesheetsId"),
   })
   GetUsersOutput usersToGetUsersOutput(Users users, Timesheets foundTimesheets);
   
}

