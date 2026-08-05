package backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.Model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Add custom query methods if needed
    List<Notification> findByRecipientIdOrderByCreatedAtDesc(Long recipientId);
    List<Notification> findByRecipientIdAndReadFalse(Long recipientId);
}
