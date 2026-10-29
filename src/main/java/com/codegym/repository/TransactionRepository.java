package com.codegym.repository;

import com.codegym.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Query("SELECT t FROM Transaction t WHERE " +
            "(:customerName IS NULL OR :customerName = '' OR LOWER(t.customer.name) LIKE LOWER(CONCAT('%', :customerName, '%'))) AND " +
            "(:serviceType IS NULL OR :serviceType = '' OR t.serviceType = :serviceType)")
    Page<Transaction> searchTransactions(@Param("customerName") String customerName,
                                         @Param("serviceType") String serviceType,
                                         Pageable pageable);
}
