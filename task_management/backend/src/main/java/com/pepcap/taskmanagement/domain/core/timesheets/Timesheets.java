package com.pepcap.taskmanagement.domain.core.timesheets;
import javax.persistence.*;
import java.time.*;
import java.math.BigDecimal;
import com.pepcap.taskmanagement.domain.core.tasks.Tasks;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;
import com.pepcap.taskmanagement.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "timesheetsEntity")
@Table(name = "timesheets")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Timesheets extends AbstractEntity {

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Basic
    @Column(name = "hours_worked", nullable = false)
    private BigDecimal hoursWorked;
    
    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Tasks tasks;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;


}



