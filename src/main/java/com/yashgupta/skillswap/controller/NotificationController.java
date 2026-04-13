package com.yashgupta.skillswap.controller;

import com.yashgupta.skillswap.dto.NotificationResponse;
import com.yashgupta.skillswap.service.NotificationService;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationResponse> list(@RequestParam Long userId,@RequestParam(defaultValue = "30") int limit){
        return notificationService.listForUser(userId, limit);
    }

    @GetMapping("/unread-count")
    public Map<String,Long> unreadCount(@RequestParam Long userId){
        return Map.of("count", notificationService.countUnread(userId));
    }

    @PatchMapping("/{id}/read")
    public String markRead(@PathVariable("id") Long notificationId,@RequestParam Long userId){
        notificationService.markRead(notificationId, userId);
        return "Notification marked as read";
    }

    @PostMapping("/read-all")
    public String markAllRead(@RequestParam Long userId) {
        notificationService.markAllRead(userId);
        return "All notifications marked as read";
    }
}
