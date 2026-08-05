package backend.service;



import backend.Model.Task;
import backend.Model.enums.LeaveStatus;
import backend.Model.enums.Role;
import backend.Model.enums.TaskStatus;

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

    public DashboardService(TaskRepository taskRepository, LeaveRequestRepository leaveRequestRepository,
                             EmployeeRepository employeeRepository, CurrentUserProvider currentUserProvider) {
        this.taskRepository = taskRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public SummaryResponse getSummary() {
        Employee current = currentUserProvider.getCurrentEmployee();
        boolean isManager = current.getRole() == Role.MANAGER;

        List<Task> scopeTasks = isManager
                ? taskRepository.findByAssignedById(current.getId())
                : taskRepository.findByAssigneeId(current.getId());

        int teamSize = isManager ? employeeRepository.findByManagerId(current.getId()).size() : 1;

        long tasksOpen = scopeTasks.stream().filter(t -> t.getStatus() != TaskStatus.DONE).count();

        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        long tasksDoneThisWeek = scopeTasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.DONE && t.getCompletedAt() != null && t.getCompletedAt().isAfter(weekAgo))
                .count();

        long leavePending = isManager
                ? leaveRequestRepository.findByEmployeeId(current.getId()).stream().filter(l -> l.getStatus() == LeaveStatus.PENDING).count()
                : leaveRequestRepository.findByEmployeeId(current.getId()).stream().filter(l -> l.getStatus() == LeaveStatus.PENDING).count();

        List<RecentTask> recentTasks = scopeTasks.stream()
                .sorted(Comparator.comparing(Task::getCreatedAt).reversed())
                .limit(5)
                .map(t -> new RecentTask(t.getId(), t.getTitle(), t.getAssignee().getName(), t.getStatus()))
                .toList();

        return new SummaryResponse(teamSize   , tasksOpen, tasksDoneThisWeek, leavePending, recentTasks);
    }
}