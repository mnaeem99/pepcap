package com.pepcap.inventorymanagement.domain.core.suppliers;
import javax.persistence.*;
import java.time.*;
import com.pepcap.inventorymanagement.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "suppliersEntity")
@Table(name = "suppliers")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Suppliers extends AbstractEntity {

    @Basic
    @Column(name = "contact_info", nullable = true)
    private String contactInfo;

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic
    @Column(name = "name", nullable = false,length =100)
    private String name;


}



