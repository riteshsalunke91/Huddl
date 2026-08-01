package backend.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class NotificationController {


    @GetMapping("/getnotification")
    public String getNotification() {
        return "Notification details";
    }

    @GetMapping("/updatenotification")
    public String updateNotification() {
        return "Notification details updated";
    }

    @PostMapping("/deletenotification")
    public String deleteNotification() {
        return "Notification deleted";
    }
}


