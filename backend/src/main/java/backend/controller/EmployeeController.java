package backend.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {


    @GetMapping("/getemployee")
    public String getEmployee() {
        return "Employee details";
    }

    @GetMapping("/updateemployee")
    public String updateEmployee() {
        return "Employee details updated";
    }
    
    
}
