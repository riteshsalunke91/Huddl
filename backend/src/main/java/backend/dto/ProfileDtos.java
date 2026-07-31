package backend.dto;

import backend.Model.enums.Role;
import jakarta.validation.constraints.NotBlank;

public class ProfileDtos {
    
public record ProfileResponse(
    long id,
    String name,
    String email,
    Role role,
    String manegername,
    int leavebalance
) {
}

public record UpdateProfileRequest
(
    @NotBlank String name
) {
}


}
