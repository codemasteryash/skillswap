package com.yashgupta.skillswap.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerEntryResponse {
    private Long ledgerId;
    private Long transactionId;
    private Double changeAmount;
    private Double balanceAfterChange;
    private String entryType;
}
