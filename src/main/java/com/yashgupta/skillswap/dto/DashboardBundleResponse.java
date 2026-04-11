package com.yashgupta.skillswap.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardBundleResponse {
    private DashboardSummaryResponse summary;
    private List<TransactionSummaryResponse> recentTransactions;
    private List<LedgerEntryResponse> recentLedgerEntries;
}
