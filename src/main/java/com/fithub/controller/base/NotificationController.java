package com.fithub.controller.base;

import com.fithub.dto.base.NotificationDTO;
import com.fithub.service.base.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin( origins = "*" )
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/v1/notifications/unread/{userId}")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(userId));
    }

    @GetMapping("/v1/notifications/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotifications(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(notificationService.getNotifications(userId));
    }

    @GetMapping("/v1/notifications")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @PostMapping("/v1/notification")
    public ResponseEntity<NotificationDTO> addNotification(@RequestBody NotificationDTO notificationDTO) {
        return ResponseEntity.ok(notificationService.addNotification(notificationDTO));
    }

    @PutMapping("/v1/notification/{id}/mark-read")
    public ResponseEntity<NotificationDTO> markNotificationAsRead(@PathVariable("id") Long id) {
        return ResponseEntity.ok(notificationService.markNotificationAsRead(id));
    }

    @PutMapping("/v1/notification/{id}/mark-unread")
    public ResponseEntity<NotificationDTO> markNotificationAsUnread(@PathVariable("id") Long id) {
        return ResponseEntity.ok(notificationService.markNotificationAsUnread(id));
    }
}
