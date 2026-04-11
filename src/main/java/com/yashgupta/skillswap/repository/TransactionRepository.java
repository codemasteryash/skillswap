package com.yashgupta.skillswap.repository;

import com.yashgupta.skillswap.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    long countByProviderId(Long providerId);
    long countByReceiverId(Long receiverId);
    @Query("SELECT AVG(t.rating) FROM Transaction t WHERE t.providerId = :uid AND t.rating IS NOT NULL")
    Double averageRatingAsProvider(@Param("uid") Long userId);
    @Query("SELECT t FROM Transaction t WHERE t.providerId = :uid OR t.receiverId = :uid ORDER BY t.transactionId DESC")
    List<Transaction> findRecentForUser(@Param("uid") Long userId, Pageable pageable);
}
