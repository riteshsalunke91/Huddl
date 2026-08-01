package backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Controller



public class DasbboardController {
    
    @GetMapping("/getdashboard")
    public String getDashboard() {
        return "Dashboard details";
    }

    @GetMapping("/updatedashboard")
    public String updateDashboard() {
        return "Dashboard details updated";
    }
    
}
