package com.pepcap.taskmanagement.application.extended.projects;

import org.springframework.stereotype.Service;
import com.pepcap.taskmanagement.application.core.projects.ProjectsAppService;

import com.pepcap.taskmanagement.domain.extended.projects.IProjectsRepositoryExtended;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@Service("projectsAppServiceExtended")
public class ProjectsAppServiceExtended extends ProjectsAppService implements IProjectsAppServiceExtended {

	public ProjectsAppServiceExtended(IProjectsRepositoryExtended projectsRepositoryExtended,
				IProjectsMapperExtended mapper,LoggingHelper logHelper) {

		super(projectsRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

