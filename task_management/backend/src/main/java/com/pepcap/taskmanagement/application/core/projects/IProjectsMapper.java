package com.pepcap.taskmanagement.application.core.projects;

import org.mapstruct.Mapper;
import com.pepcap.taskmanagement.application.core.projects.dto.*;
import com.pepcap.taskmanagement.domain.core.projects.Projects;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IProjectsMapper {
   Projects createProjectsInputToProjects(CreateProjectsInput projectsDto);
   CreateProjectsOutput projectsToCreateProjectsOutput(Projects entity);
   
    Projects updateProjectsInputToProjects(UpdateProjectsInput projectsDto);
    
   	UpdateProjectsOutput projectsToUpdateProjectsOutput(Projects entity);
   	FindProjectsByIdOutput projectsToFindProjectsByIdOutput(Projects entity);


}

