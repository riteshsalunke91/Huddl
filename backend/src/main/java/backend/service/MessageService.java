package backend.service;

import backend.Model.Employee;
import backend.Model.Message;
import backend.Model.enums.Role;
import backend.dto.MessageDtos.*;

import backend.repository.EmployeeRepository;
import backend.repository.MessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final EmployeeRepository employeeRepository;
    private final CurrentUserProvider currentUserProvider;
    private final NotificationService notificationService;

    public MessageService(MessageRepository messageRepository, EmployeeRepository employeeRepository,
                          CurrentUserProvider currentUserProvider, NotificationService notificationService) {
        this.messageRepository = messageRepository;
        this.employeeRepository = employeeRepository;
        this.currentUserProvider = currentUserProvider;
        this.notificationService = notificationService;
    }

    private List<Employee> contactsFor(Employee user) {
        if (user.getRole() == Role.MANAGER) {
            return employeeRepository.findByManagerId(user.getId());
        }
        return user.getManager() != null ? List.of(user.getManager()) : List.of();
    }

    public List<ThreadSummaryResponse> listThreads() {
        Employee current = currentUserProvider.getCurrentEmployee();

        return contactsFor(current).stream().map(contact -> {
            List<Message> thread = messageRepository
                    .findBySenderIdAndRecipientIdOrRecipientIdAndSenderId(
                            current.getId(), contact.getId(), current.getId(), contact.getId())
                    .stream()
                    .sorted(Comparator.comparing(Message::getCreatedAt).reversed())
                    .toList();

            Message last = thread.isEmpty() ? null : thread.get(0);
            long unread = thread.stream()
                    .filter(m -> m.getRecipient().getId().equals(current.getId()) && !m.isRead())
                    .count();

            return new ThreadSummaryResponse(
                    contact.getId(), contact.getName(),
                    last != null ? last.getText() : null,
                    last != null ? last.getCreatedAt() : null,
                    (int) unread
            );
        }).toList();
    }

    public List<MessageResponse> getThread(Long contactId) {
        Employee current = currentUserProvider.getCurrentEmployee();
        assertAllowedContact(current, contactId);

        return messageRepository
                .findBySenderIdAndRecipientIdOrRecipientIdAndSenderId(current.getId(), contactId, current.getId(), contactId)
                .stream()
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .map(this::toResponse)
                .toList();
    }

    public MessageResponse send(Long contactId, SendMessageRequest request) {
        Employee current = currentUserProvider.getCurrentEmployee();
        Employee contact = assertAllowedContact(current, contactId);

        boolean noText = request.text() == null || request.text().isBlank();
        boolean noImage = request.imageUrl() == null || request.imageUrl().isBlank();
        if (noText && noImage) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message cannot be empty");
        }

        Message message = new Message();
        message.setSender(current);
        message.setRecipient(contact);
        message.setText(request.text());
        message.setImageUrl(request.imageUrl());
        message.setRead(false);
        message.setCreatedAt(LocalDateTime.now());
        messageRepository.save(message);

        notificationService.notify(contact.getId(), "New message from " + current.getName(), NotificationType.MESSAGE);

        return toResponse(message);
    }

    @Transactional
    public void markThreadRead(Long contactId) {
        Employee current = currentUserProvider.getCurrentEmployee();
        List<Message> incoming = messageRepository.findBySenderIdAndRecipientId(contactId, current.getId());
        incoming.forEach(m -> m.setRead(true));
        messageRepository.saveAll(incoming);
    }

    private Employee assertAllowedContact(Employee current, Long contactId) {
        return contactsFor(current).stream()
                .filter(c -> c.getId().equals(contactId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only message your manager or your own team"));
    }

    private MessageResponse toResponse(Message m) {
        return new MessageResponse(m.getId(), m.getSender().getId(), m.getRecipient().getId(), m.getText(), m.getImageUrl(), m.isRead(), m.getCreatedAt());
    }
}
