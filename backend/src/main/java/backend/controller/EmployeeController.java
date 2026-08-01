package backend.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.EmployeeDtos.EmployeeResponse;
import jakarta.validation.Valid;

@Controller
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

   @PostMapping("/invite")
    public ResponseEntity<EmployeeResponse> invite(@Valid @RequestBody createEmployeeRequest request) {
        return ResponseEntity.ok(HttpStatus.CREATED).
        body(employeeService.createEmployee(request));
    }
  
    @GetMapping
    public ResponseEntity<EmployeeResponse> listTeams() {
        return ResponseEntity.ok(employeeService.listEmployees());
    }


    @GetMapping("/{Id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable String Id) {
        return ResponseEntity.ok(employeeService.getEmployee(Id));
    }

}
