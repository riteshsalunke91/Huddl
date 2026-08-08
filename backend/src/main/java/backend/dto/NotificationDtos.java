package backend.dto;

import backend.Model.enums.NotificationType;

import java.time.LocalDateTime;

public class NotificationDtos {

    public record NotificationResponse(
            Long id,
            String message,
            NotificationType type,
            boolean read,
            LocalDateTime createdAt
    ) {
    }


    public record UpdateReadRequest(
            boolean read
    ) {
    }
}