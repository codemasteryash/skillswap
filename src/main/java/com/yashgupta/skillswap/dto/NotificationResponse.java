package com.yashgupta.skillswap.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private Long notificationId;
    private String title;
    private String body;
    private String type;
    private boolean read;
    private Long relatedTransactionId;
    private LocalDateTime createdAt;
}
