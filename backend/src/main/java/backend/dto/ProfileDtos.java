package backend.dto;

import backend.Model.enums.Role;

public class ProfileDtos {

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


    public record UpdateProfileRequest(
            String name
    ) {
    }


    public record ChangePasswordRequest(
            String currentPassword,
            String newPassword
    ) {
    }
}