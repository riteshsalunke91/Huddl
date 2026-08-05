package backend.controller;

import backend.dto.MessageDtos.*;
import backend.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/threads")
    public ResponseEntity<List<ThreadSummaryResponse>> listThreads() {
        return ResponseEntity.ok(messageService.listThreads());
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<List<MessageResponse>> getThread(@PathVariable Long contactId) {
        return ResponseEntity.ok(messageService.getThread(contactId));
    }

    @PostMapping("/{contactId}")
    public ResponseEntity<MessageResponse> send(@PathVariable Long contactId, @Valid @RequestBody SendMessageRequest request) {
        return ResponseEntity.ok(messageService.send(contactId, request));
    }

    @PatchMapping("/{contactId}/read")
    public ResponseEntity<Map<String, Boolean>> markThreadRead(@PathVariable Long contactId) {
        messageService.markThreadRead(contactId);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
