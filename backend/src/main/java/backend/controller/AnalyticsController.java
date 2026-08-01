package backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/summary")
 public ResponseEntity<Summaryresponse> getSummary(){
        return ResponseEntity.ok(analyticsService.getSummary());
    }

  
 }

