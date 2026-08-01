package backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.AnalyticsDtos.SummaryResponse;



@RestController
@Controller

public class DasbboardController {
    
   private final DashboardService dashboardService;

    @GetMapping("/Summary")
    public ResponseEntity<SummaryResponse> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }
    
}
