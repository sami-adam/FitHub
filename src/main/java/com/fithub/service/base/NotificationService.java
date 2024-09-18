package com.fithub.service.base;

import com.fithub.dto.base.NotificationDTO;
import com.fithub.dto.user.UserDTO;

import java.util.List;
import java.util.Map;

public interface NotificationService {
    List<NotificationDTO> getUnreadNotifications(Long userId);
    List<NotificationDTO> getNotifications(Long userId);
    List<NotificationDTO> getAllNotifications();
    NotificationDTO addNotification(NotificationDTO notificationDTO);
    NotificationDTO updateNotification(Long id, NotificationDTO notificationDTO);
    Map<String, String> deleteNotification(Long id);
    NotificationDTO markNotificationAsRead(Long id);
    NotificationDTO markNotificationAsUnread(Long id);
    void sendNotification(UserDTO userDTO, String title, String message);
    List<NotificationDTO> findByTitle(String title);
    List<NotificationDTO> findByMessage(String message);
}
