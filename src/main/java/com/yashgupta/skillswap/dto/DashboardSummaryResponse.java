package com.yashgupta.skillswap.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardSummaryResponse {
    private Long userId;
    private String userName;
    private String email;
    private Double credits;
    private long transactionsAsLearnerCount;
    private long transactionsAsTeacherCount;
    private Double averageRatingAsTeacher;
}
