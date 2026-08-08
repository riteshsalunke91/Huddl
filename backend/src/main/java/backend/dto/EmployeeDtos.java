package backend.dto;

import backend.Model.enums.AccountStatus;

public class EmployeeDtos {

    public record CreateEmployeeRequest(
            String name,
            String email
    ) {
    }


    public record EmployeeResponse(
            Long id,
            String name,
            String email,
            String destination,
            int leaveBalance,
            String managerName,
            AccountStatus status
    ) {
    }
}