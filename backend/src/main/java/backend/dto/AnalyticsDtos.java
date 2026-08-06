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

//         public SummaryResponse (int teamSize, long tasksOpen, long tasksDoneThisWeek, long leavePending,
//                 List<RecentTask> recentTasks) {
//             //TODO Auto-generated constructor stub
//         }}
// }

}