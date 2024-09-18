package com.fithub.repository.base;

import com.fithub.model.base.Notification;
import com.fithub.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndReadIsFalse(Long user_id);
    List<Notification> findByUserId(Long userId);
    List<Notification> findByTitle(String title);
    List<Notification> findByMessage(String message);
}
