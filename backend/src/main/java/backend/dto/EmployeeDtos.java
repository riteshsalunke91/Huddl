package backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmployeeDtos {

    public record Employeerequest(

@NotBlank String name,
@NotBlank @Email String email


    ){}

    public record EmployeeResponse
(
            Long id,
            String name,
            String email,
            String designation,
            int leaveBalance,
            String managerName
           // AccountStatus status
) {
    }



    }
    

