package com.pepcap.ecommerce.domain.core.products;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import java.math.BigDecimal;
import com.pepcap.ecommerce.domain.core.categories.Categories;
import com.pepcap.ecommerce.domain.core.orderitems.OrderItems;
import com.pepcap.ecommerce.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "productsEntity")
@Table(name = "products")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Products extends AbstractEntity {

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "description", nullable = true)
    private String description;

    @Id
    @EqualsAndHashCode.Include() 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic
    @Column(name = "name", nullable = false,length =100)
    private String name;

    @Basic
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    
    @Basic
    @Column(name = "stock", nullable = true)
    private Integer stock;
    
    @Basic
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Categories categories;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItems> orderItemsSet = new HashSet<OrderItems>();
    
    public void addOrderItems(OrderItems orderItems) {        
    	orderItemsSet.add(orderItems);

        orderItems.setProducts(this);
    }
    public void removeOrderItems(OrderItems orderItems) {
        orderItemsSet.remove(orderItems);
        
        orderItems.setProducts(null);
    }
    

}



