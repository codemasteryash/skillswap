package com.yashgupta.skillswap.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 160)
    private String title;

    @Column(length = 600)
    private String body;

    @Column(nullable = false, length = 40)
    private String type;

    @Column(name = "is_read", nullable = false)
    @Builder.Default
    private boolean read = false;

    private Long relatedTransactionId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
