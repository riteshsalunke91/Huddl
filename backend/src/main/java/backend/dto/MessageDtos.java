package backend.dto;

import java.time.LocalDateTime;

public class MessageDtos {

    public record SendMessageRequest(
            String text,
            String imageUrl
    ) {
    }


    public record MessageResponse(
            Long id,
            Long senderId,
            Long recipientId,
            String text,
            String imageUrl,
            boolean read,
            LocalDateTime createdAt
    ) {
    }


    public record ThreadSummaryResponse(
            Long contactId,
            String contactName,
            String lastMessage,
            LocalDateTime lastMessageAt,
            int unread
    ) {
    }
}