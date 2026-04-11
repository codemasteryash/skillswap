package com.yashgupta.skillswap.controller;

import com.yashgupta.skillswap.dto.DashboardBundleResponse;
import com.yashgupta.skillswap.dto.DashboardSummaryResponse;
import com.yashgupta.skillswap.dto.LedgerEntryResponse;
import com.yashgupta.skillswap.dto.TransactionSummaryResponse;
import com.yashgupta.skillswap.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
    @GetMapping("/summary")
    public DashboardSummaryResponse summary(@RequestParam Long userId) {
        return dashboardService.getSummary(userId);
    }
    @GetMapping("/ledger")
    public List<LedgerEntryResponse> ledger(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "20") int limit){
        return dashboardService.getRecentLedger(userId, limit);
    }
    @GetMapping("/transactions")
    public List<TransactionSummaryResponse> transactions(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "20") int limit){
        return dashboardService.getRecentTransactions(userId, limit);
    }
    @GetMapping
    public DashboardBundleResponse full(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "20") int ledgerLimit,
            @RequestParam(defaultValue = "20") int transactionLimit){
        return dashboardService.getFullDashboard(userId, ledgerLimit, transactionLimit);
    }
}
