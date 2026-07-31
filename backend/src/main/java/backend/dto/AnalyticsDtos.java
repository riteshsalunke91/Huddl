package backend.dto;

import java.util.List;

public class AnalyticsDtos {

    
    public record NameValue(
            String name,
            int value
    ) {}

    public record WeeklyCompletion(
            String week,
            int value   
    ) {}

    public record EmployeeWorkload(
            String name,
            int open,
            int done
    ) {}

    public record SummaryResponse(
            List<NameValue> tasksByStatus,
            List<WeeklyCompletion> completedPerWeek,
            List<EmployeeWorkload> tasksByEmployee,
            List<NameValue> leaveBreakdown
    ) {}
}