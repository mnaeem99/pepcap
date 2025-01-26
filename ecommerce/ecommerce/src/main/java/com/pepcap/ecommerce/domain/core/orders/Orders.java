package com.pepcap.ecommerce.domain.core.orders;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import java.math.BigDecimal;
import com.pepcap.ecommerce.domain.core.orderitems.OrderItems;
import com.pepcap.ecommerce.domain.core.authorization.users.Users;
import com.pepcap.ecommerce.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "ordersEntity")
@Table(name = "orders")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Orders extends AbstractEntity {

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic
    @Column(name = "status", nullable = true,length =50)
    private String status;

    @Basic
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;
    
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItems> orderItemsSet = new HashSet<OrderItems>();
    
    public void addOrderItems(OrderItems orderItems) {        
    	orderItemsSet.add(orderItems);

        orderItems.setOrders(this);
    }
    public void removeOrderItems(OrderItems orderItems) {
        orderItemsSet.remove(orderItems);
        
        orderItems.setOrders(null);
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;


}



