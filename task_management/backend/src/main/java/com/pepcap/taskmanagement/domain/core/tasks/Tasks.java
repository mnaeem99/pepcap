package com.pepcap.taskmanagement.domain.core.tasks;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import com.pepcap.taskmanagement.domain.core.projects.Projects;
import com.pepcap.taskmanagement.domain.core.timesheets.Timesheets;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;
import com.pepcap.taskmanagement.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "tasksEntity")
@Table(name = "tasks")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Tasks extends AbstractEntity {

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "description", nullable = true)
    private String description;

    @Basic
    @Column(name = "due_date", nullable = true)
    private LocalDate dueDate;

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic
    @Column(name = "name", nullable = false,length =100)
    private String name;

    @Basic
    @Column(name = "status", nullable = true,length =50)
    private String status;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Projects projects;

    @OneToMany(mappedBy = "tasks", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Timesheets> timesheetsSet = new HashSet<Timesheets>();
    
    public void addTimesheets(Timesheets timesheets) {        
    	timesheetsSet.add(timesheets);

        timesheets.setTasks(this);
    }
    public void removeTimesheets(Timesheets timesheets) {
        timesheetsSet.remove(timesheets);
        
        timesheets.setTasks(null);
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private Users users;


}



