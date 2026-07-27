package backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RestController
public class TaskController {

    @GetMapping("/")
    public String getMethodName(@RequestParam String param ) {
        return new String();
    }
    
    
}
