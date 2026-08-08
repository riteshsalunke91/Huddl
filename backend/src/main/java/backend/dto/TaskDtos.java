package backend.dto;

import backend.Model.enums.TaskStatus;

import java.time.LocalDateTime;

public class TaskDtos {

    public record CreateTaskRequest(
            String title,
            String description,
            String assigneeEmail,
            LocalDateTime deadline,
            String photoUrl
    ) {
    }


    public record UpdateTaskStatusRequest(
            TaskStatus status
    ) {
    }


    public record TaskResponse(
            Long id,
            String title,
            String description,
            String assigneeName,
            String assigneeEmail,
            TaskStatus status,
            LocalDateTime deadline,
            String photoUrl,
            LocalDateTime createdAt,
            LocalDateTime completedAt
    ) {
    }
}