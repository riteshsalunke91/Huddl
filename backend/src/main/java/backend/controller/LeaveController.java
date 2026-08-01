package backend.controller;

import java.util.HashSet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import backend.dto.LeaveDtos.LeaveResponse;
import jakarta.validation.Valid;


public class LeaveController {

    private final LeaveService leaveService ;

    @PostMapping
    public ResponseEntity<LeaveResponse> apply(@Valid @RequestBody ApplyLeaveRequest Request) {
        return ResponseEntity.ok(HttpStatus.CREATED).body(leaveService.applyLeave(Request));
    }
    
}
