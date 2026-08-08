package backend.dto;

import backend.Model.enums.TaskStatus;

import java.util.List;

public class DashboardDtos {

    public record DashboardSummaryResponse(
            int teamSize,
            long tasksOpen,
            long tasksDoneThisWeek,
            long leavePending,
            List<RecentTask> recentTasks
    ) {
    }


    public record RecentTask(
            Long id,
            String title,
            String assigneeName,
            TaskStatus status
    ) {
    }
}