package backend.service;

import backend.Model.Employee;
import backend.Model.Task;
import backend.Model.enums.LeaveStatus;
import backend.Model.enums.Role;
import backend.Model.enums.TaskStatus;
import backend.dto.DashboardDtos.DashboardSummaryResponse;
import backend.dto.DashboardDtos.RecentTask;
import backend.repository.EmployeeRepository;
import backend.repository.LeaveRequestRepository;
import backend.repository.TaskRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class DashboardService {

    private final TaskRepository taskRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final CurrentUserProvider currentUserProvider;

    public DashboardService(
            TaskRepository taskRepository,
            LeaveRequestRepository leaveRequestRepository,
            EmployeeRepository employeeRepository,
            CurrentUserProvider currentUserProvider) {

        this.taskRepository = taskRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public DashboardSummaryResponse getSummary() {

        Employee current = currentUserProvider.getCurrentEmployee();

        boolean isManager = current.getRole() == Role.MANAGER;

        // Tasks visible to current user
        List<Task> scopeTasks = isManager
                ? taskRepository.findByAssignedById(current.getId())
                : taskRepository.findByAssigneeId(current.getId());

        // Team size
        int teamSize = isManager
                ? employeeRepository.findByManagerId(current.getId()).size()
                : 1;

        // Open tasks
        long tasksOpen = scopeTasks.stream()
                .filter(task -> task.getStatus() != TaskStatus.DONE)
                .count();

        // Last 7 days
        LocalDateTime weekAgo =
                LocalDateTime.now().minusDays(7);

        // Completed this week
        long tasksDoneThisWeek = scopeTasks.stream()
                .filter(task ->
                        task.getStatus() == TaskStatus.DONE
                        && task.getCompletedAt() != null
                        && task.getCompletedAt().isAfter(weekAgo)
                )
                .count();

        // Pending leave requests
        long leavePending;

        if (isManager) {

            leavePending = leaveRequestRepository
                    .findByEmployeeManagerId(current.getId())
                    .stream()
                    .filter(leave ->
                            leave.getStatus() == LeaveStatus.PENDING)
                    .count();

        } else {

            leavePending = leaveRequestRepository
                    .findByEmployeeId(current.getId())
                    .stream()
                    .filter(leave ->
                            leave.getStatus() == LeaveStatus.PENDING)
                    .count();
        }

        // Recent 5 tasks
        List<RecentTask> recentTasks = scopeTasks.stream()
                .sorted(
                        Comparator.comparing(Task::getCreatedAt)
                                .reversed()
                )
                .limit(5)
                .map(task -> new RecentTask(
                        task.getId(),
                        task.getTitle(),
                        task.getAssignee() != null
                                ? task.getAssignee().getName()
                                : "Unassigned",
                        task.getStatus()
                ))
                .toList();

        return new DashboardSummaryResponse(
                teamSize,
                tasksOpen,
                tasksDoneThisWeek,
                leavePending,
                recentTasks
        );
    }
}