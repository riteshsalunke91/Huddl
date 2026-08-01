package backend.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class AnalyticsController {

    @GetMapping("/getanalytics")
    public String getAnalytics() {
        return "Analytics details";
    }

    @GetMapping("/updateanalytics")
    public String updateAnalytics() {
        return "Analytics details updated";
    }
    
}
