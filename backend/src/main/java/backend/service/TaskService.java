package backend.service;

import backend.Model.Employee;
import backend.Model.Task;
import backend.Model.enums.NotificationType;
import backend.Model.enums.Role;
import backend.Model.enums.TaskStatus;
import backend.dto.TaskDtos.CreateTaskRequest;
import backend.dto.TaskDtos.TaskResponse;
import backend.dto.TaskDtos.UpdateTaskStatusRequest;

import backend.repository.EmployeeRepository;
import backend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class TaskService {

    private final TaskRepository taskRepository= null;
    private final EmployeeRepository employeeRepository = null;
    private final CurrentUserProvider currentUserProvider = null;
    private final NotificationService notificationService = null;

    public List<TaskResponse> listForCurrentUser() {
        Employee current = currentUserProvider.getCurrentEmployee();

        List<Task> tasks = current.getRole() == Role.MANAGER
                ? taskRepository.findByAssignedById(current.getId())
                : taskRepository.findByAssigneeId(current.getId());

        return tasks.stream().map(this::toResponse).toList();
    }

    public TaskResponse create(CreateTaskRequest request) {
        Employee manager = currentUserProvider.getCurrentEmployee();

        if (manager.getRole() != Role.MANAGER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only managers can assign tasks");
        }

        Employee assignee = employeeRepository.findByEmail(request.assigneeEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No employee found with that email"));

        if (assignee.getManager() == null || !assignee.getManager().getId().equals(manager.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only assign tasks to your own team");
        }

        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setAssignee(assignee);
        task.setAssignedBy(manager);
        task.setStatus(TaskStatus.TODO);
        task.setDeadline(request.deadline());
        task.setPhotoUrl(request.photoUrl());
        taskRepository.save(task);

        notificationService.notify(assignee.getId(), "You were assigned \"" + task.getTitle() + "\"", NotificationType.TASK);

        return toResponse(task);
    }

    public TaskResponse updateStatus(Long taskId, UpdateTaskStatusRequest request) {
        Employee current = currentUserProvider.getCurrentEmployee();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        boolean isOwner = task.getAssignee().getId().equals(current.getId());
        boolean isAssigningManager = task.getAssignedBy().getId().equals(current.getId());

        if (!isOwner && !isAssigningManager) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only update your own tasks");
        }

        task.setStatus(request.status());
        task.setCompletedAt(request.status() == TaskStatus.DONE ? LocalDateTime.now() : null);
        taskRepository.save(task);

        return toResponse(task);
    }

    private TaskResponse toResponse(Task t) {
        return new TaskResponse(
                t.getId(), t.getTitle(), t.getDescription(),
                t.getAssignee().getName(), t.getAssignee().getEmail(),
                t.getStatus(), t.getDeadline(), t.getPhotoUrl(),
                t.getCreatedAt(), t.getCompletedAt()
        );
    }
}


