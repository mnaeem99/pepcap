package com.pepcap.taskmanagement.domain.core.tasks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;

@Repository("tasksRepository")
public interface ITasksRepository extends JpaRepository<Tasks, Integer>,QuerydslPredicateExecutor<Tasks> {

    
}

