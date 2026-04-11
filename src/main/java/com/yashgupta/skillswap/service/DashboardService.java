package com.yashgupta.skillswap.service;

import com.yashgupta.skillswap.dto.DashboardBundleResponse;
import com.yashgupta.skillswap.dto.DashboardSummaryResponse;
import com.yashgupta.skillswap.dto.LedgerEntryResponse;
import com.yashgupta.skillswap.dto.TransactionSummaryResponse;
import com.yashgupta.skillswap.entity.CreditLedger;
import com.yashgupta.skillswap.entity.Transaction;
import com.yashgupta.skillswap.entity.User;
import com.yashgupta.skillswap.repository.CreditLedgerRepository;
import com.yashgupta.skillswap.repository.TransactionRepository;
import com.yashgupta.skillswap.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    private static final int MAX_LIMIT =100;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final CreditLedgerRepository creditLedgerRepository;

    public DashboardService(UserRepository userRepository, TransactionRepository transactionRepository, CreditLedgerRepository creditLedgerRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.creditLedgerRepository = creditLedgerRepository;
    }
    public DashboardSummaryResponse getSummary(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        long asLearner = transactionRepository.countByProviderId(userId);
        long asTeacher = transactionRepository.countByReceiverId(userId);
        Double avgRating = transactionRepository.averageRatingAsProvider(userId);

        return DashboardSummaryResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .credits(user.getCredits())
                .transactionsAsLearnerCount(asLearner)
                .transactionsAsTeacherCount(asTeacher)
                .averageRatingAsTeacher(avgRating)
                .build();
    }
    public List<LedgerEntryResponse>getRecentLedger(Long userId,int limit){
        ensureUserExists(userId);
        int safeLimit=clampLimit(limit);
        List<CreditLedger> rows = creditLedgerRepository.findByUserIdOrderByLedgerIdDesc(userId, PageRequest.of(0, safeLimit));
        return rows.stream().map(this::toLedgerResponse).collect(Collectors.toList());
    }

    public List<TransactionSummaryResponse> getRecentTransactions(Long userId,int limit){
        ensureUserExists(userId);
        int safeLimit=clampLimit(limit);
        List<Transaction> rows = transactionRepository.findRecentForUser(userId, PageRequest.of(0, safeLimit));
        return rows.stream().map(this::toTransactionResponse).collect(Collectors.toList());
    }
    public DashboardBundleResponse getFullDashboard(Long userId, int ledgerLimit, int transactionLimit) {
        return DashboardBundleResponse.builder()
                .summary(getSummary(userId))
                .recentLedger(getRecentLedger(userId, ledgerLimit))
                .recentTransactions(getRecentTransactions(userId, transactionLimit))
                .build();
    }
    private void ensureUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
    }
    private static int clampLimit(int limit) {
        if (limit < 1) {
            return 1;
        }
        return Math.min(limit, MAX_LIMIT);
    }
    private LedgerEntryResponse toLedgerResponse(CreditLedger l) {
        return LedgerEntryResponse.builder()
                .ledgerId(l.getLedgerId())
                .transactionId(l.getTransactionId())
                .changeAmount(l.getChangeAmount())
                .balanceAfterChange(l.getBalanceAfterChange())
                .entryType(l.getEntryType())
                .build();
    }
    private TransactionSummaryResponse toTransactionResponse(Transaction t) {
        return TransactionSummaryResponse.builder()
                .transactionId(t.getTransactionId())
                .providerId(t.getProviderId())
                .receiverId(t.getReceiverId())
                .duration(t.getDuration())
                .status(t.getStatus())
                .rating(t.getRating())
                .feedback(t.getFeedback())
                .build();
    }
}
