// package backend.controller;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RestController;

// import backend.dto.TaskDtos.TaskResponse;
// import jakarta.validation.Valid;

// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PatchMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;



// @Controller
// @RestController
// public class TaskController {

//     private final TaskService taskService;

//     @PostMapping
//     public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {
//         return ResponseEntity.ok(HttpStatus.CREATED).body(taskService.createTask(request));
//     }

//     @GetMapping
//     public ResponseEntity<TaskResponse> getMine() {
//         return ResponseEntity.ok(taskService.getMine());
//     }


//     @GetMapping("/{Id}")
//     public ResponseEntity<TaskResponse> getTask(@PathVariable String taskId) {
//         return ResponseEntity.ok(taskService.getTask(taskId));
//     }

//     @PatchMapping("/{Id}/status")
//     public ResponseEntity<TaskResponse> updateTaskStatus(@Valid @PathVariable Long Id, @RequestBody UpdateTaskStatusRequest request) {
//         return ResponseEntity.ok(taskService.updateTaskStatus(Id, request));
//     }



// }


package backend.controller;

import backend.dto.TaskDtos.CreateTaskRequest;
import backend.dto.TaskDtos.TaskResponse;
import backend.dto.TaskDtos.UpdateTaskStatusRequest;
import backend.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> list() {
        return ResponseEntity.ok(taskService.listForCurrentUser());
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
        return ResponseEntity.ok(taskService.create(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskStatusRequest request
    ) {
        return ResponseEntity.ok(taskService.updateStatus(id, request));
    }
}
    
    

