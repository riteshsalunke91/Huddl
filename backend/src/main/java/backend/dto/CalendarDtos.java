package backend.dto;

import java.time.LocalDate;

public class CalendarDtos {
    
    public record CalendarEventResponse(
        String type,
        String title,
        LocalDate startdate,
        LocalDate enddDate,
        String status
    ) {
    }
}
