package com.yashgupta.skillswap.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionSummaryResponse {
    private Long transactionId;
    private Long providerId;
    private Long receiverId;
    private Double duration;
    private String status;
    private Integer rating;
    private String feedback;
}
