package backend.dto.response;
import backend.Model.enums.Role;

public record ProfileResponse(
        Long id,
        String name,
        String email,
        Role role,
        String destination,
        String managerName,
        int leaveBalance
) {
}