package com.pepcap.inventorymanagement.domain.core.inventorytransactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;

@Repository("inventoryTransactionsRepository")
public interface IInventoryTransactionsRepository extends JpaRepository<InventoryTransactions, Integer>,QuerydslPredicateExecutor<InventoryTransactions> {

    
}

