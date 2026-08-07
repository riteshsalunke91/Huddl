package backend.controller;

import backend.dto.EmployeeDtos.EmployeeResponse;
import backend.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> listMyTeam() {
        return ResponseEntity.ok(employeeService.listMyTeam());
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> invite(@Valid @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.invite(request));
    }
