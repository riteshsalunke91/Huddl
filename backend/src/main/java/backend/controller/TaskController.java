package backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RestController
public class TaskController {


    @GetMapping("/gettask")
    public String getTask() {
        return "Task details";
    }

    @GetMapping("/updatetask")
    public String updateTask() {
        return "Task details updated";
    }
    
    
}
