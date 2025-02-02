export interface ITasks {  
	createdAt?: Date;
	description?: string;
	dueDate?: Date;
	id: number;
	name: string;
	status?: string;

	projectsDescriptiveField?: number;
	projectId?: number;
	usersDescriptiveField?: number;
	assigneeId?: number;
}


