package backend.service;

import backend.Model.Employee;
import backend.Model.LeaveRequest;
import backend.Model.Task;
import backend.Model.enums.LeaveStatus;
import backend.Model.enums.Role;
import backend.dto.CalendarDtos.CalendarEventResponse;
import backend.repository.LeaveRequestRepository;
import backend.repository.TaskRepository;

import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final TaskRepository taskRepository;
    private final CurrentUserProvider currentUserProvider;

    public CalendarService(
            LeaveRequestRepository leaveRequestRepository,
            TaskRepository taskRepository,
            CurrentUserProvider currentUserProvider) {

        this.leaveRequestRepository = leaveRequestRepository;
        this.taskRepository = taskRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public List<CalendarEventResponse> getEvents(
            YearMonth month) {

        Employee current =
                currentUserProvider.getCurrentEmployee();

        boolean isManager =
                current.getRole() == Role.MANAGER;

        List<CalendarEventResponse> events =
                new ArrayList<>();

        // ==========================================
        // Leave Events
        // ==========================================

        List<LeaveRequest> leaves =
                isManager
                        ? leaveRequestRepository
                                .findByEmployeeManagerId(
                                        current.getId())
                        : leaveRequestRepository
                                .findByEmployeeId(
                                        current.getId());

        leaves.stream()
                .filter(leave ->
                        leave.getStatus()
                                != LeaveStatus.REJECTED)
                .filter(leave ->
                        YearMonth.from(
                                leave.getStartDate())
                                .equals(month)
                        ||
                        YearMonth.from(
                                leave.getEndDate())
                                .equals(month)
                )
                .forEach(leave -> {

                    String employeeName =
                            leave.getEmployee() != null
                                    ? leave.getEmployee()
                                            .getName()
                                    : "Unknown";

                    events.add(
                            new CalendarEventResponse(
                                    "leave",
                                    employeeName
                                            + " — "
                                            + leave.getReason(),
                                    leave.getStartDate(),
                                    leave.getEndDate(),
                                    leave.getStatus().name()
                            )
                    );
                });

        // ==========================================
        // Task Deadline Events
        // ==========================================

        List<Task> tasks =
                isManager
                        ? taskRepository
                                .findByAssignedById(
                                        current.getId())
                        : taskRepository
                                .findByAssigneeId(
                                        current.getId());

        tasks.stream()
                .filter(task ->
                        task.getDeadline() != null
                        && YearMonth.from(
                                task.getDeadline())
                                .equals(month)
                )
                .forEach(task -> {

                    String assignee =
                            task.getAssignee() != null
                                    ? task.getAssignee()
                                            .getName()
                                    : "Unassigned";

                    events.add(
                            new CalendarEventResponse(
                                    "deadline",
                                    task.getTitle()
                                            + " ("
                                            + assignee
                                            + ")",
                                    task.getDeadline(),
                                    null,
                                    task.getStatus().name()
                            )
                    );
                });

        return events;
    }
}