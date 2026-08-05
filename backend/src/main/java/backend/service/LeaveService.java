package backend.service;

import backend.Model.Employee;
import backend.Model.LeaveRequest;
import backend.Model.enums.LeaveStatus;
import backend.Model.enums.NotificationType;
import backend.Model.enums.Role;
import backend.dto.LeaveDtos.*;

import backend.repository.EmployeeRepository;
import backend.repository.LeaveRequestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LeaveService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final CurrentUserProvider currentUserProvider;
    private final NotificationService notificationService;

    public LeaveService(LeaveRequestRepository leaveRequestRepository, EmployeeRepository employeeRepository,
                        CurrentUserProvider currentUserProvider, NotificationService notificationService) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
        this.currentUserProvider = currentUserProvider;
        this.notificationService = notificationService;
    }

    public List<LeaveResponse> listForCurrentUser() {
        Employee current = currentUserProvider.getCurrentEmployee();

        List<LeaveRequest> requests = current.getRole() == Role.MANAGER
                ? leaveRequestRepository.findByEmployeeManagerId(current.getId())
                : leaveRequestRepository.findByEmployeeId(current.getId());

        return requests.stream().map(this::toResponse).toList();
    }

    public LeaveBalanceResponse getBalance() {
        Employee current = currentUserProvider.getCurrentEmployee();
        int taken = 18 - current.getLeaveBalance();
        return new LeaveBalanceResponse(Math.max(taken, 0), current.getLeaveBalance());
    }

    public LeaveResponse apply(ApplyLeaveRequest request) {
        Employee current = currentUserProvider.getCurrentEmployee();

        if (request.endDate().isBefore(request.startDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date cannot be before start date");
        }

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(current);
        leave.setStartDate(request.startDate());
        leave.setEndDate(request.endDate());
        leave.setReason(request.reason());
        leave.setStatus(LeaveStatus.PENDING);
        leaveRequestRepository.save(leave);

        if (current.getManager() != null) {
            notificationService.notify(
                    current.getManager().getId(),
                    current.getName() + " applied for leave (" + request.reason() + ")",
                    NotificationType.LEAVE
            );
        }

        return toResponse(leave);
    }

    public LeaveResponse decide(Long leaveId, DecideLeaveRequest request) {
        Employee manager = currentUserProvider.getCurrentEmployee();

        if (manager.getRole() != Role.MANAGER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only managers can decide leave requests");
        }

        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave request not found"));

        Employee employee = leave.getEmployee();
        if (employee.getManager() == null || !employee.getManager().getId().equals(manager.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only decide leave for your own team");
        }

        leave.setStatus(request.status());

        if (request.status() == LeaveStatus.APPROVED) {
            long days = leave.getDays();
            employee.setLeaveBalance((int) Math.max(employee.getLeaveBalance() - days, 0));
            employeeRepository.save(employee);
        }

        leaveRequestRepository.save(leave);

        String verb = request.status() == LeaveStatus.APPROVED ? "approved" : "rejected";
        notificationService.notify(employee.getId(), "Your leave request (" + leave.getReason() + ") was " + verb, NotificationType.LEAVE);

        return toResponse(leave);
    }

    private LeaveResponse toResponse(LeaveRequest l) {
        return new LeaveResponse(l.getId(), l.getEmployee().getName(), l.getStartDate(), l.getEndDate(), l.getReason(), l.getStatus());
    }
}
