package com.yashgupta.skillswap.controller;

import com.yashgupta.skillswap.dto.PublicStatsResponse;
import com.yashgupta.skillswap.repository.CreditLedgerRepository;
import com.yashgupta.skillswap.repository.TransactionRepository;
import com.yashgupta.skillswap.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final CreditLedgerRepository creditLedgerRepository;

    public PublicController(
            UserRepository userRepository,
            TransactionRepository transactionRepository,
            CreditLedgerRepository creditLedgerRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.creditLedgerRepository = creditLedgerRepository;
    }

    @GetMapping("/stats")
    public PublicStatsResponse stats() {
        long users = userRepository.count();
        long txs = transactionRepository.count();
        Double sum = creditLedgerRepository.sumAbsChangeAmount();
        long credits = sum == null ? 0L : Math.round(sum);
        return new PublicStatsResponse(users, txs, credits);
    }
}
