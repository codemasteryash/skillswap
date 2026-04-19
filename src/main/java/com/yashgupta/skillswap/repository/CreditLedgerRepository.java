package com.yashgupta.skillswap.repository;

import com.yashgupta.skillswap.entity.CreditLedger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditLedgerRepository extends JpaRepository<CreditLedger,Long> {

    List<CreditLedger> findByUserIdOrderByLedgerIdDesc(Long userId, Pageable pageable);

    @Query("SELECT COALESCE(SUM(ABS(c.changeAmount)), 0.0) FROM CreditLedger c")
    Double sumAbsChangeAmount();
}
