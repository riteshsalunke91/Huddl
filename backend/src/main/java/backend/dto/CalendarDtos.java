package backend.dto;

import java.time.LocalDate;

public class CalendarDtos {

    public record CalendarEventResponse(
            String type,
            String title,
            LocalDate startDate,
            LocalDate endDate,
            String status
    ) {
    }
}