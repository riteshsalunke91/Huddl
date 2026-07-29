package backend.dto;

import backend.Model.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusRequest(
    @NotNull TaskStatus status) {
    
}
