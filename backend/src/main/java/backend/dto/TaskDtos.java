package backend.dto;

import java.time.LocalDate;

import backend.Model.Task;
import backend.Model.TaskStatus;
import lombok.NonNull;

public class TaskDtos {
    
    @NonNull String title;
    String description;
    @NonNull @Email String  assigneeEmail;
     LocalDate deadline;
    private String photoUrl;
    
}  {}

public record UpdateTaskStatusRequest(@NonNull TaskStatus  status) 
{}


public record TaskResponse(
    Long id,
    String title,
    String description,
    String assigneeEmail,
    String assignedByEmail,
    TaskStatus status,
    LocalDate deadline,
    String photoUrl
LocalDateTime createdAt,
    LocalDateTime completedAt
) {}




