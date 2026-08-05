


package backend.controller;

import backend.dto.TaskDtos.CreateTaskRequest;
import backend.dto.TaskDtos.TaskResponse;
import backend.dto.TaskDtos.UpdateTaskStatusRequest;
import backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tas;\nks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> list() {
        return ResponseEntity.ok(taskService.listForCurrentUser());
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
        return ResponseEntity.ok(taskService.create(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateTaskStatusRequest request) {
        return ResponseEntity.ok(taskService.updateStatus(id, request));
    }
}
