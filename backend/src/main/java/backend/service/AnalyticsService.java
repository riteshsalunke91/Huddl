package backend.service;

import backend.Model.Employee;
import backend.Model.Task;

import backend.Model.enums.TaskStatus;
import backend.dto.AnalyticsDtos.*;
import backend.repository.EmployeeRepository;
import backend.repository.LeaveRequestRepository;
import backend.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AnalyticsService {

    private final TaskRepository taskRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final CurrentUserProvider currentUserProvider;

    public AnalyticsService(TaskRepository taskRepository, LeaveRequestRepository leaveRequestRepository,
                             EmployeeRepository employeeRepository, CurrentUserProvider currentUserProvider) {
        this.taskRepository = taskRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public SummaryResponse getSummary() {
        Employee manager = currentUserProvider.getCurrentEmployee();
        if (manager.getRole() != Role.MANAGER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Manager access required");
        }

        List<Task> teamTasks = taskRepository.findByAssignedById(manager.getId());
        List<Employee> team = employeeRepository.findByManagerId(manager.getId());

        Map<TaskStatus, Long> counts = new EnumMap<>(TaskStatus.class);
        for (TaskStatus s : TaskStatus.values()) counts.put(s, 0L);
        teamTasks.forEach(t -> counts.merge(t.getStatus(), 1L, Long::sum));

        List<NameValue> tasksByStatus = List.of(
                new NameValue("To do", counts.get(TaskStatus.TODO).intValue()),
                new NameValue("In progress", counts.get(TaskStatus.IN_PROGRESS).intValue()),
                new NameValue("Done", counts.get(TaskStatus.DONE).intValue())
        );

        List<WeeklyCompletion> completedPerWeek = new ArrayList<>();
        for (int w = 5; w >= 0; w--) {
            LocalDateTime weekStart = LocalDateTime.now().minusDays((w + 1) * 7L);
            LocalDateTime weekEnd = LocalDateTime.now().minusDays(w * 7L);
            long count = teamTasks.stream()
                    .filter(t -> t.getStatus() == TaskStatus.DONE && t.getCompletedAt() != null
                            && !t.getCompletedAt().isBefore(weekStart) && t.getCompletedAt().isBefore(weekEnd))
                    .count();
            completedPerWeek.add(new WeeklyCompletion(w == 0 ? "This week" : w + "w ago", (int) count));
        }

        List<EmployeeWorkload> tasksByEmployee = team.stream()
                .map(e -> new EmployeeWorkload(
                        e.getName(),
                        (int) teamTasks.stream().filter(t -> t.getAssignee().getId().equals(e.getId()) && t.getStatus() != TaskStatus.DONE).count(),
                        (int) teamTasks.stream().filter(t -> t.getAssignee().getId().equals(e.getId()) && t.getStatus() == TaskStatus.DONE).count()
                ))
                .toList();

        Map<String, Integer> leaveByReason = new HashMap<>();
        leaveRequestRepository.findByEmployeeManagerId(manager.getId()).stream()
                .filter(l -> l.getStatus() == LeaveStatus.APPROVED)
                .forEach(l -> leaveByReason.merge(l.getReason(), (int) l.getDays(), Integer::sum));

        List<NameValue> leaveBreakdown = leaveByReason.entrySet().stream()
                .map(e -> new NameValue(e.getKey(), e.getValue()))
                .toList();

        return new SummaryResponse(tasksByStatus, completedPerWeek, tasksByEmployee, leaveBreakdown);
    }
}