package backend.service;

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

    public CalendarService(LeaveRequestRepository leaveRequestRepository, TaskRepository taskRepository,
                           CurrentUserProvider currentUserProvider) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.taskRepository = taskRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public List<CalendarEventResponse> getEvents(YearMonth month) {
        Employee current = currentUserProvider.getCurrentEmployee();
        boolean isManager = current.getRole() == Role.MANAGER;
        List<CalendarEventResponse> events = new ArrayList<>();

        List<LeaveRequest> leaves = isManager
                ? leaveRequestRepository.findByEmployeeManagerId(current.getId())
                : leaveRequestRepository.findByEmployeeId(current.getId());

        leaves.stream()
                .filter(l -> l.getStatus() != LeaveStatus.REJECTED)
                .filter(l -> YearMonth.from(l.getStartDate()).equals(month) || YearMonth.from(l.getEndDate()).equals(month))
                .forEach(l -> events.add(new CalendarEventResponse(
                        "leave",
                        l.getEmployee().getName() + " — " + l.getReason(),
                        l.getStartDate(), l.getEndDate(), l.getStatus().name()
                )));

        List<Task> tasks = isManager
                ? taskRepository.findByAssignedById(current.getId())
                : taskRepository.findByAssigneeId(current.getId());

        tasks.stream()
                .filter(t -> t.getDeadline() != null && YearMonth.from(t.getDeadline()).equals(month))
                .forEach(t -> events.add(new CalendarEventResponse(
                        "deadline",
                        t.getTitle() + " (" + t.getAssignee().getName() + ")",
                        t.getDeadline(), null, t.getStatus().name()
                )));

        return events;
    }
}
