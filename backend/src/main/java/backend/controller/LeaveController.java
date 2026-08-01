package backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import backend.dto.LeaveDtos.LeaveResponse;

public class LeaveController {

    private final LeaveService leaveService;

    @GetMapping("/getleave")
    public ResponseEntity<LeaveResponse> getLeave() {
        return ResponseEntity.ok(leaveService.getLeave());

    }

    @GetMapping("/updateleave")
    public ResponseEntity<LeaveResponse> updateLeave() {
        return ResponseEntity.ok(leaveService.updateLeave());
    }

    @GetMapping("/changeleave")
    public ResponseEntity<LeaveResponse> changeLeave() {
        return ResponseEntity.ok(leaveService.changeLeave());
    }
    
}
