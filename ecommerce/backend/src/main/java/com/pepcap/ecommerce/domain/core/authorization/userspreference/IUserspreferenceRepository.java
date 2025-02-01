package com.pepcap.ecommerce.domain.core.authorization.userspreference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


@Repository("userspreferenceRepository")
public interface IUserspreferenceRepository extends JpaRepository<Userspreference, Integer>,QuerydslPredicateExecutor<Userspreference> {

}

