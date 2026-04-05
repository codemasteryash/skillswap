package com.yashgupta.skillswap.repository;

import com.yashgupta.skillswap.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
