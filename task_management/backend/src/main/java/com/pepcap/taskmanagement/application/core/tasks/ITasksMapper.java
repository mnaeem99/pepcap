package com.pepcap.taskmanagement.application.core.tasks;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.pepcap.taskmanagement.domain.core.projects.Projects;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;
import com.pepcap.taskmanagement.application.core.tasks.dto.*;
import com.pepcap.taskmanagement.domain.core.tasks.Tasks;
import java.time.*;

@Mapper(componentModel = "spring")
public interface ITasksMapper {
   Tasks createTasksInputToTasks(CreateTasksInput tasksDto);
   
   @Mappings({ 
   @Mapping(source = "entity.projects.id", target = "projectId"),                   
   @Mapping(source = "entity.projects.id", target = "projectsDescriptiveField"),                    
   @Mapping(source = "entity.users.id", target = "assigneeId"),                   
   @Mapping(source = "entity.users.id", target = "usersDescriptiveField"),                    
   }) 
   CreateTasksOutput tasksToCreateTasksOutput(Tasks entity);
   
    Tasks updateTasksInputToTasks(UpdateTasksInput tasksDto);
    
    @Mappings({ 
    @Mapping(source = "entity.projects.id", target = "projectId"),                   
    @Mapping(source = "entity.projects.id", target = "projectsDescriptiveField"),                    
    @Mapping(source = "entity.users.id", target = "assigneeId"),                   
    @Mapping(source = "entity.users.id", target = "usersDescriptiveField"),                    
   	}) 
   	UpdateTasksOutput tasksToUpdateTasksOutput(Tasks entity);
   	@Mappings({ 
   	@Mapping(source = "entity.projects.id", target = "projectId"),                   
   	@Mapping(source = "entity.projects.id", target = "projectsDescriptiveField"),                    
   	@Mapping(source = "entity.users.id", target = "assigneeId"),                   
   	@Mapping(source = "entity.users.id", target = "usersDescriptiveField"),                    
   	}) 
   	FindTasksByIdOutput tasksToFindTasksByIdOutput(Tasks entity);


   @Mappings({
   @Mapping(source = "projects.createdAt", target = "createdAt"),                  
   @Mapping(source = "projects.description", target = "description"),                  
   @Mapping(source = "projects.id", target = "id"),                  
   @Mapping(source = "projects.name", target = "name"),                  
   @Mapping(source = "projects.status", target = "status"),                  
   @Mapping(source = "foundTasks.id", target = "tasksId"),
   })
   GetProjectsOutput projectsToGetProjectsOutput(Projects projects, Tasks foundTasks);
   
   @Mappings({
   @Mapping(source = "users.createdAt", target = "createdAt"),                  
   @Mapping(source = "users.id", target = "id"),                  
   @Mapping(source = "foundTasks.id", target = "tasksId"),
   })
   GetUsersOutput usersToGetUsersOutput(Users users, Tasks foundTasks);
   
}

