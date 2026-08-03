package backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import backend.dto.NotificationDtos.NotificationResponse;
import backend.service.NotificationService;
import jakarta.validation.Valid;

@Service
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> listtmine() {
        return ResponseEntity.ok(notificationService.listtmine());
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Map<String, Integer>> getUnreadCount() {
        return ResponseEntity.ok(notificationService.getUnreadCount());
    }

    @PatchMapping("/{ID}/read")
    public ResponseEntity<NotificationResponse> markAsRead(@Valid @PathVariable Long ID) {
        return ResponseEntity.ok(notificationService.markAsRead(ID, Request,Read));
    }
}


