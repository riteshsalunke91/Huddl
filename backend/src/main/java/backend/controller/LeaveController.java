package backend.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import backend.dto.LeaveDtos.ApplyLeaveRequest;
import backend.dto.LeaveDtos.DecideLeaveRequest;
import backend.dto.LeaveDtos.LeaveResponse;
import backend.service.LeaveService;
import jakarta.validation.Valid;


public class LeaveController {

    private final LeaveService leaveService = new LeaveService();

    @PostMapping
    public ResponseEntity<LeaveResponse> apply(@Valid @RequestBody ApplyLeaveRequest Request) {
        return ResponseEntity.ok(HttpStatus.CREATED).body(leaveService.applyLeave(Request));
    }

    @GetMapping
    public ResponseEntity<List<LeaveResponse>> LastMine() {
        return ResponseEntity.ok(leaveService.lastMine());
    }

    @GetMapping("/balance")
    public ResponseEntity<LeaveResponse> getBalance() {
        return ResponseEntity.ok(leaveService.getBalance());
    }

    @PostMapping("/{id}/decision")
    public ResponseEntity<LeaveResponse> Decide(@Valid @RequestBody DecideLeaveRequest Request) {
        return ResponseEntity.ok(leaveService.Decide(Request));
    }
    
}
