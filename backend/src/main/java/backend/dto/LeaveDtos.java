package backend.dto;

import java.time.LocalDate;

import backend.Model.enums.LeaveStatus;
import jakarta.validation.constraints.NotBlank;

public class LeaveDtos {

    public record ApplyLeaveRequest(
          @NotBlank  LocalDate Startdate,
        @NotBlank LocalDate endDate ,
     @NotBlank String Reason   ) {
    }

    public record DecideLeaveRequest(
        @NotBlank LeaveStatus Status
    ) {
    }

    public record LeaveResponse( 
          long id,
          String employee,
          LocalDate Startdate,
          LocalDate endDate,
          String reason


    ) {
    }

    public record LeaveBalanceResponse(
        int taken,
        int remaining
    ) {
    }







    
}
