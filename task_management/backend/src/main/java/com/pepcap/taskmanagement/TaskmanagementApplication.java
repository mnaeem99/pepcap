package com.pepcap.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.pepcap.taskmanagement"})
public class TaskmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagementApplication.class, args);
	}

}

