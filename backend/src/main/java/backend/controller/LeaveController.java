package backend.controller;

import backend.dto.LeaveDtos.*;
import backend.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @GetMapping
    public ResponseEntity<List<LeaveResponse>> list() {
        return ResponseEntity.ok(leaveService.listForCurrentUser());
    }

    @GetMapping("/balance")
    public ResponseEntity<LeaveBalanceResponse> balance() {
        return ResponseEntity.ok(leaveService.getBalance());
    }

    @PostMapping
    public ResponseEntity<LeaveResponse> apply(@Valid @RequestBody ApplyLeaveRequest request) {
        return ResponseEntity.ok(leaveService.apply(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LeaveResponse> decide(@PathVariable Long id, @Valid @RequestBody DecideLeaveRequest request) {
        return ResponseEntity.ok(leaveService.decide(id, request));
    }
}
