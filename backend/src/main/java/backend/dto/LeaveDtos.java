package backend.dto;

import backend.Model.enums.LeaveStatus;

import java.time.LocalDate;

public class LeaveDtos {

    public record ApplyLeaveRequest(
            LocalDate startDate,
            LocalDate endDate,
            String reason
    ) {
    }


    public record DecideLeaveRequest(
            LeaveStatus status
    ) {
    }


    public record LeaveResponse(
            Long id,
            String employeeName,
            LocalDate startDate,
            LocalDate endDate,
            String reason,
            LeaveStatus status
    ) {
    }


    public record LeaveBalanceResponse(
            int taken,
            int remaining
    ) {
    }
}