package com.pepcap.ecommerce.domain.core.authorization.userspreference;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserspreferenceManager {
   
    Userspreference create(Userspreference userspreference);

    void delete(Userspreference userspreference);

    Userspreference update(Userspreference userspreference);
    
    Userspreference findById(Integer  id);
    
    Page<Userspreference> findAll(Predicate predicate, Pageable pageable);
  
}



