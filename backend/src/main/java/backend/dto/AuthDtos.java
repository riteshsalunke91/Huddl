

package backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDtos {

    // What the client sends to log in
    public record LoginRequest(
            @NotBlank @Email String email,
            @NotBlank String password
    ) {}

    // What the server returns after a successful login (or signup)
    public record LoginResponse(
            String token
    ) {}

    // What the client sends to self-register as a manager
    public record SignupRequest(
            @NotBlank String name,
            @NotBlank @Email String email,
            @NotBlank @Size(min = 6, message = "Password must be at least 6 characters") String password
    ) {}

    // What the client sends to request a password reset link
    public record ForgotPasswordRequest(
            @NotBlank @Email String email
    ) {}

    // What the client sends to actually set a new password —
    // used for BOTH invite activation and forgot-password reset
    public record SetPasswordRequest(
            @NotBlank String token,
            @NotBlank @Size(min = 6, message = "Password must be at least 6 characters") String password
    ) {}
}


