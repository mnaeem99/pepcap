package com.pepcap.ecommerce.domain.core.categories;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import com.pepcap.ecommerce.domain.core.products.Products;
import com.pepcap.ecommerce.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.TypeDefs;


@Entity
@Config(defaultVariableName = "categoriesEntity")
@Table(name = "categories")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeDefs({
}) 
public class Categories extends AbstractEntity {

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
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Products> productsSet = new HashSet<Products>();
    
    public void addProducts(Products products) {        
    	productsSet.add(products);

        products.setCategories(this);
    }
    public void removeProducts(Products products) {
        productsSet.remove(products);
        
        products.setCategories(null);
    }
    

}



