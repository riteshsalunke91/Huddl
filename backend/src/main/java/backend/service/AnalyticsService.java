package backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import backend.Model.Employee;
import backend.Model.Task;
import backend.Model.enums.Role;
import backend.Model.enums.TaskStatus;
import backend.dto.AnalyticsDtos.EmployeeWorkload;
import backend.dto.AnalyticsDtos.NameValue;
import backend.dto.AnalyticsDtos.SummaryResponse;
import backend.dto.AnalyticsDtos.WeeklyCompletion;
import backend.repository.EmployeeRepository;
import backend.repository.LeaveRequestRepository;
import backend.repository.TaskRepository;


@Service
public class AnalyticsService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final CurrentUserProvider currentUserProvider;

    public AnalyticsService(EmployeeRepository employeeRepository,
                            TaskRepository taskRepository,
                            CurrentUserProvider currentUserProvider) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public SummaryResponse getSummary() {
        Employee manager = currentUserProvider.getCurrentEmployee();

        if (manager.getRole() != Role.MANAGER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Manager access required");
        }

        List<Task> teamTasks = taskRepository.findByAssignedById(manager.getId());
        List<Employee> team = employeeRepository.findByManagerId(manager.getId());

    // -------------------------
    // Tasks by Status
    // -------------------------
    Map<TaskStatus, Long> statusCounts = Arrays.stream(TaskStatus.values())
            .collect(Collectors.toMap(
                    status -> status,
                    status -> 0L,
                    (a, b) -> a,
                    () -> new EnumMap<>(TaskStatus.class)
            ));

    teamTasks.forEach(task ->
            statusCounts.merge(task.getStatus(), 1L, Long::sum)
    );

    List<NameValue> tasksByStatus = List.of(
            new NameValue("To Do", statusCounts.get(TaskStatus.TODO).intValue()),
            new NameValue("In Progress", statusCounts.get(TaskStatus.IN_PROGRESS).intValue()),
            new NameValue("Done", statusCounts.get(TaskStatus.DONE).intValue())
    );

    // -------------------------
    // Weekly Completed Tasks
    // -------------------------
    LocalDateTime now = LocalDateTime.now();
    List<WeeklyCompletion> completedPerWeek = new ArrayList<>();

    for (int w = 5; w >= 0; w--) {

        LocalDateTime weekStart = now.minusDays((w + 1L) * 7);
        LocalDateTime weekEnd = now.minusDays(w * 7L);

        int completed = (int) teamTasks.stream()
                .filter(task ->
                        task.getStatus() == TaskStatus.DONE &&
                        task.getCompletedAt() != null &&
                        !task.getCompletedAt().isBefore(weekStart) &&
                        task.getCompletedAt().isBefore(weekEnd))
                .count();

        String label = (w == 0) ? "This Week" : w + " Weeks Ago";

        completedPerWeek.add(new WeeklyCompletion(label, completed));
    }

    // -------------------------
    // Group Tasks by Employee
    // -------------------------
    Map<Long, List<Task>> tasksByEmployeeMap = teamTasks.stream()
            .collect(Collectors.groupingBy(
                    task -> task.getAssignee().getId()
            ));

    List<EmployeeWorkload> tasksByEmployee = team.stream()
            .map(employee -> {

                List<Task> employeeTasks = tasksByEmployeeMap.getOrDefault(
                        employee.getId(),
                        Collections.emptyList()
                );

                int active = (int) employeeTasks.stream()
                        .filter(task -> task.getStatus() != TaskStatus.DONE)
                        .count();

                int completed = (int) employeeTasks.stream()
                        .filter(task -> task.getStatus() == TaskStatus.DONE)
                        .count();

                return new EmployeeWorkload(
                        employee.getName(),
                        active,
                        completed
                );
            })
            .toList();

    // -------------------------
    // Leave Breakdown
    // -------------------------
    Map<String, Integer> leaveReasonMap = new HashMap<>();

    LeaveRequestRepository.findByEmployeeManagerId(manager.getId())
            .stream()
            .filter(leave -> leave.getStatus() == LeaveStatus.APPROVED)
            .forEach(leave -> {

                String reason = leave.getReason() == null || leave.getReason().isBlank()
                        ? "Other"
                        : leave.getReason();

                leaveReasonMap.merge(
                        reason,
                        (int) leave.getDays(),
                        Integer::sum
                );
            });

    List<NameValue> leaveBreakdown = leaveReasonMap.entrySet()
            .stream()
            .map(entry -> new NameValue(
                    entry.getKey(),
                    entry.getValue()
            ))
            .toList();

    return new SummaryResponse(
            tasksByStatus,
            completedPerWeek,
            tasksByEmployee,
            leaveBreakdown
    );
}
}