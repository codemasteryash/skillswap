package com.yashgupta.skillswap.controller;

import com.yashgupta.skillswap.dto.SwapRequest;
import com.yashgupta.skillswap.entity.User;
import com.yashgupta.skillswap.service.TransactionService;
import com.yashgupta.skillswap.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @PostMapping("/complete")
    public String completeSwap(@RequestBody SwapRequest request) {
        return transactionService.completeSwap(
                request.getLearnerId(),
                request.getTeacherId(),
                request.getHours(),
                request.getSkillId()
        );
    }

    @PostMapping("/rate")
    public String rateTransaction(@RequestParam Long transactionId, @RequestParam int rating,@RequestParam String feedback) {
        return transactionService.rateTransaction(transactionId, rating,feedback);
    }

    @GetMapping("/search")
    public List<User> search(@RequestParam String skill) {
        return userService.findBySkill(skill);
    }
}
