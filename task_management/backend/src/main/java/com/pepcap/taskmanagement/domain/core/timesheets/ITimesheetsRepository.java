package com.pepcap.taskmanagement.domain.core.timesheets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;

@Repository("timesheetsRepository")
public interface ITimesheetsRepository extends JpaRepository<Timesheets, Integer>,QuerydslPredicateExecutor<Timesheets> {

    
}

