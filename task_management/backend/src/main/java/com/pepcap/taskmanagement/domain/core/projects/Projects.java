package com.pepcap.taskmanagement.domain.core.projects;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import com.pepcap.taskmanagement.domain.core.tasks.Tasks;
import com.pepcap.taskmanagement.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "projectsEntity")
@Table(name = "projects")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Projects extends AbstractEntity {

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "description", nullable = true)
    private String description;

    @Basic
    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic
    @Column(name = "name", nullable = false,length =100)
    private String name;

    @Basic
    @Column(name = "start_date", nullable = true)
    private LocalDate startDate;

    @Basic
    @Column(name = "status", nullable = true,length =50)
    private String status;

    @OneToMany(mappedBy = "projects", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tasks> tasksSet = new HashSet<Tasks>();
    
    public void addTasks(Tasks tasks) {        
    	tasksSet.add(tasks);

        tasks.setProjects(this);
    }
    public void removeTasks(Tasks tasks) {
        tasksSet.remove(tasks);
        
        tasks.setProjects(null);
    }
    

}



