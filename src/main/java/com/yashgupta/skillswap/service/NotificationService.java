package com.yashgupta.skillswap.service;

import com.yashgupta.skillswap.dto.NotificationResponse;
import com.yashgupta.skillswap.entity.Notification;
import com.yashgupta.skillswap.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    public static final String TYPE_SWAP_COMPLETED = "SWAP_COMPLETED";
    public static final String TYPE_RATED = "RATED";

    private static final int MAX_LIMIT=100;

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void notifySwapCompleted(Long learnerId,Long teacherId,Long transactionId,String skillName){
        String skillPart = skillName != null ? skillName : "a skill";
        notificationRepository.save(Notification.builder()
                .userId(learnerId)
                .title("Session completed")
                .body("You completed a learning session for " + skillPart + ". Credits were updated.")
                .type(TYPE_SWAP_COMPLETED)
                .relatedTransactionId(transactionId)
                .build());
        notificationRepository.save(Notification.builder()
                .userId(teacherId)
                .title("Session completed")
                .body("You taught " + skillPart + " and received credits.")
                .type(TYPE_SWAP_COMPLETED)
                .relatedTransactionId(transactionId)
                .build());
    }
    @Transactional
    public void notifyTeacherRated(Long teacherId, Long transactionId, int rating) {
        notificationRepository.save(Notification.builder()
                .userId(teacherId)
                .title("New rating")
                .body("You received a " + rating + "-star rating for a completed session.")
                .type(TYPE_RATED)
                .relatedTransactionId(transactionId)
                .build());
    }
    public List<NotificationResponse> listForUser(Long userId, int limit) {
        int safe = Math.max(1, Math.min(limit, MAX_LIMIT));
        return notificationRepository
                .findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(0, safe))
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    public long countUnread(Long userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }
    @Transactional
    public void markRead(Long notificationId, Long userId) {
        Notification n = notificationRepository
                .findByNotificationIdAndUserId(notificationId, userId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        n.setRead(true);
        notificationRepository.save(n);
    }
    @Transactional
    public void markAllRead(Long userId) {
        List<Notification> all = notificationRepository.findByUserIdOrderByCreatedAtDesc(
                userId, PageRequest.of(0, MAX_LIMIT));
        for (Notification n : all) {
            if (!n.isRead()) {
                n.setRead(true);
            }
        }
        notificationRepository.saveAll(all);
    }
    private NotificationResponse toResponse(Notification n) {
        return NotificationResponse.builder()
                .notificationId(n.getNotificationId())
                .title(n.getTitle())
                .body(n.getBody())
                .type(n.getType())
                .read(n.isRead())
                .relatedTransactionId(n.getRelatedTransactionId())
                .createdAt(n.getCreatedAt())
                .build();
    }
}
