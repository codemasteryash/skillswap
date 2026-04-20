package com.yashgupta.skillswap.service;

import com.yashgupta.skillswap.entity.CreditLedger;
import com.yashgupta.skillswap.entity.Skill;
import com.yashgupta.skillswap.entity.Transaction;
import com.yashgupta.skillswap.entity.User;
import com.yashgupta.skillswap.repository.CreditLedgerRepository;
import com.yashgupta.skillswap.repository.SkillRepository;
import com.yashgupta.skillswap.repository.TransactionRepository;
import com.yashgupta.skillswap.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    private final CreditLedgerRepository creditLedgerRepository;

    private final SkillRepository skillRepository;

    private final NotificationService notificationService;

    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository, CreditLedgerRepository creditLedgerRepository, SkillRepository skillRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.creditLedgerRepository = creditLedgerRepository;
        this.skillRepository = skillRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public String completeSwap(Long learnerId, Long teacherId, Double hours, Long skillId ) {
        if (learnerId == null || teacherId == null) {
            throw new RuntimeException("Learner and teacher IDs are required");
        }
        if (hours == null || hours <= 0) {
            throw new RuntimeException("Swap hours must be greater than zero");
        }
        if (learnerId.equals(teacherId)) {
            throw new RuntimeException("Learner and teacher must be different users");
        }

        User learner = userRepository.findById(learnerId).orElseThrow(() -> new RuntimeException("Learner not found"));
        User teacher = userRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Teacher not found"));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        double totalCost = hours * skill.getPricePerHour();

        if(learner.getCredits() < totalCost){
            throw new RuntimeException("Insufficient credits");
        }
        learner.setCredits(learner.getCredits() - totalCost);
        teacher.setCredits(teacher.getCredits() + totalCost);

        userRepository.save(learner);
        userRepository.save(teacher);

        Transaction transaction=new Transaction();
        transaction.setProviderId(teacherId);
        transaction.setReceiverId(learnerId);
        transaction.setDuration(hours);
        transaction.setStatus("COMPLETED");
        Transaction savedTransaction = transactionRepository.save(transaction);

        creditLedgerRepository.save(createLedger(learner, savedTransaction, -totalCost, "DEBIT"));
        creditLedgerRepository.save(createLedger(teacher, savedTransaction, totalCost, "CREDIT"));
        notificationService.notifySwapCompleted(
                learnerId,
                teacherId,
                savedTransaction.getTransactionId(),
                skill.getName());
        return "Swap completed successfully";
}

    private CreditLedger createLedger(User user, Transaction transaction, Double amount, String type) {
        CreditLedger ledger = new CreditLedger();
        ledger.setUserId(user.getUserId());
        ledger.setTransactionId(transaction.getTransactionId());
        ledger.setChangeAmount(amount);
        ledger.setBalanceAfterChange(user.getCredits());
        ledger.setEntryType(type);
        return ledger;
    }

    public String rateTransaction(Long transactionId, int rating, String feedback) {
        Transaction txn= transactionRepository.findById(transactionId).orElseThrow(()->new RuntimeException("Transaction not found"));
        if (!txn.getStatus().equals("COMPLETED")) {
            throw new RuntimeException("Cannot rate incomplete transaction");
        }
        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }
        txn.setRating(rating);
        txn.setFeedback(feedback);

        transactionRepository.save(txn);
        notificationService.notifyTeacherRated(txn.getProviderId(), transactionId, rating);

        return "Rating submitted!";
    }
}
