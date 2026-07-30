package backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import backend.Model.enums.TaskStatus;

public class TaskDtos {

    public record CreateTaskRequest(
            @NotBlank String title,
            String description,
            @NotBlank @Email String assigneeEmail,
            LocalDate deadline,
            String photoUrl
    ) {}

    public record UpdateTaskStatusRequest(
            @NotNull TaskStatus status
    ) {}

    public record TaskResponse(
            Long id,
            String title,
            String description,
            String assigneeName,
            String assigneeEmail,
            TaskStatus status,
            LocalDate deadline,
            String photoUrl,
            LocalDateTime createdAt,
            LocalDateTime completedAt
    ) {}
}