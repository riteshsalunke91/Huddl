package backend.dto;

import jakarta.validation.constraints.NotNull;

public class EmployeeDtos {

    @NotNull String name;
    @NotNull @Email String email;
    
}
