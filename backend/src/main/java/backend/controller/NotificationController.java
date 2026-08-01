package backend.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class NotificationController {


    @GetMapping("/getnotification")
    public String getNotification() {
        return "Notification details";
    }
}
