package com.pepcap.inventorymanagement.domain.core.inventorytransactions;
import javax.persistence.*;
import java.time.*;
import com.pepcap.inventorymanagement.domain.core.products.Products;
import com.pepcap.inventorymanagement.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "inventoryTransactionsEntity")
@Table(name = "inventory_transactions")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class InventoryTransactions extends AbstractEntity {

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Basic
    @Column(name = "transaction_type", nullable = false,length =50)
    private String transactionType;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;


}



