package backend.dto;

import java.util.List;

public class DashboardDtos {

    public record SummerResponse(   int teamSize,
            long tasksOpen,
            long tasksDoneThisWeek,
            long leavePending,
            List<RecentTask> recentTasks) {
  
           


    }
    
    public record RecentTask(

        long id,
        String title,
         String assignname,
        String status


    ) {
    }
}
