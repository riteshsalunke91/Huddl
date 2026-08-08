package backend.service;

import backend.Model.Employee;
import backend.Model.Notification;
import backend.Model.enums.NotificationType;
import backend.dto.NotificationDtos.NotificationResponse;
import backend.dto.NotificationDtos.UpdateReadRequest;
import backend.repository.EmployeeRepository;
import backend.repository.NotificationRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;
    private final CurrentUserProvider currentUserProvider;

    public NotificationService(
            NotificationRepository notificationRepository,
            EmployeeRepository employeeRepository,
            CurrentUserProvider currentUserProvider) {

        this.notificationRepository = notificationRepository;
        this.employeeRepository = employeeRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public List<NotificationResponse> listForCurrentUser() {

        Employee current =
                currentUserProvider.getCurrentEmployee();

        return notificationRepository
                .findByRecipientIdOrderByCreatedAtDesc(
                        current.getId()
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public NotificationResponse updateRead(
            Long id,
            UpdateReadRequest request) {

        Employee current =
                currentUserProvider.getCurrentEmployee();

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Notification not found"
                                )
                        );

        if (!notification.getRecipient()
                .getId()
                .equals(current.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Not your notification"
            );
        }

        notification.setRead(request.read());

        notificationRepository.save(notification);

        return toResponse(notification);
    }

    @Transactional
    public void markAllRead() {

        Employee current =
                currentUserProvider.getCurrentEmployee();

        List<Notification> unread =
                notificationRepository
                        .findByRecipientIdAndReadFalse(
                                current.getId()
                        );

        unread.forEach(
                notification ->
                        notification.setRead(true)
        );

        notificationRepository.saveAll(unread);
    }

    public void notify(
            Long recipientId,
            String message,
            NotificationType type) {

        Employee recipient =
                employeeRepository.findById(recipientId)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Recipient not found"
                                )
                        );

        Notification notification = new Notification();

        notification.setRecipient(recipient);
        notification.setMessage(message);
        notification.setType(type);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    private NotificationResponse toResponse(
            Notification notification) {

        return new NotificationResponse(
                notification.getId(),
                notification.getMessage(),
                notification.getType(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}