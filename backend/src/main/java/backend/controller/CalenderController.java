package backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.micrometer.observation.Observation.Event;

public class CalenderController {

    private final CalenderService calenderService;

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvents(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  to) {
        return ResponseEntity.ok(calenderService.getEvents());
    }

}
