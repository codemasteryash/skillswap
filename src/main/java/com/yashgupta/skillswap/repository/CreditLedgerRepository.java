package com.yashgupta.skillswap.repository;

import com.yashgupta.skillswap.entity.CreditLedger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditLedgerRepository extends JpaRepository<CreditLedger,Long> {

    List<CreditLedger> findByUserIdOrderByLedgerIdDesc(Long userId, Pageable pageable);
}
