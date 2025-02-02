package com.pepcap.taskmanagement.domain.core.projects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;

@Repository("projectsRepository")
public interface IProjectsRepository extends JpaRepository<Projects, Integer>,QuerydslPredicateExecutor<Projects> {

    
}

