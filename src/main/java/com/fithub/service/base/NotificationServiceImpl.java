package com.fithub.service.base;

import com.fithub.dto.base.NotificationDTO;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.base.Notification;
import com.fithub.repository.base.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<NotificationDTO> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndReadIsFalse(userId).stream()
                .map(notification -> mapper.map(notification, NotificationDTO.class))
                .toList();
    }

    @Override
    public List<NotificationDTO> getNotifications(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(notification -> mapper.map(notification, NotificationDTO.class))
                .toList();
    }

    @Override
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(notification -> mapper.map(notification, NotificationDTO.class))
                .toList();
    }

    @Override
    public NotificationDTO addNotification(NotificationDTO notificationDTO) {
        Notification notification = mapper.map(notificationDTO, Notification.class);
        return mapper.map(notificationRepository.save(notification), NotificationDTO.class);
    }

    @Override
    public NotificationDTO updateNotification(Long id, NotificationDTO notificationDTO) {
        // To be implemented
        return null;
    }

    @Override
    public Map<String, String> deleteNotification(Long id) {
        // To be implemented
        return Map.of();
    }

    @Override
    public NotificationDTO markNotificationAsRead(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Notification not found with id: " + id));
        notification.setRead(true);
        return mapper.map(notificationRepository.save(notification), NotificationDTO.class);
    }

    @Override
    public NotificationDTO markNotificationAsUnread(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Notification not found with id: " + id));
        notification.setRead(false);
        return mapper.map(notificationRepository.save(notification), NotificationDTO.class);
    }

    @Override
    public List<NotificationDTO> findByTitle(String title) {
        return notificationRepository.findByTitle(title).stream()
                .map(notification -> mapper.map(notification, NotificationDTO.class))
                .toList();
    }

    @Override
    public List<NotificationDTO> findByMessage(String message) {
        return notificationRepository.findByMessage(message).stream()
                .map(notification -> mapper.map(notification, NotificationDTO.class))
                .toList();
    }
}
