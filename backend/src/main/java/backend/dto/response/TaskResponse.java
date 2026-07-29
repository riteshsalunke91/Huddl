package backend.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import backend.Model.TaskStatus;

public record TaskResponse(
    Long id,
    String title,
    String description,
    String assigneeEmail,
    String assignedByEmail,
    TaskStatus status,
    LocalDate deadline,
    String photoUrl,
LocalDateTime createdAt,
    LocalDateTime completedAt
)
{}
