package backend.controller;

import backend.dto.NotificationDtos.NotificationResponse;
import backend.dto.NotificationDtos.UpdateReadRequest;
import backend.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> list() {
        return ResponseEntity.ok(notificationService.listForCurrentUser());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NotificationResponse> updateRead(@PathVariable Long id, @Valid @RequestBody UpdateReadRequest request) {
        return ResponseEntity.ok(notificationService.updateRead(id, request));
    }

    @PostMapping("/read-all")
    public ResponseEntity<Map<String, Boolean>> markAllRead() {
        notificationService.markAllRead();
        return ResponseEntity.ok(Map.of("success", true));
    }
}


