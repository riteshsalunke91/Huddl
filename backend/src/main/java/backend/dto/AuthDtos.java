package backend.dto;

public class AuthDtos {

    public record LoginRequest(
            String email,
            String password
    ) {
    }


    public record LoginResponse(
            String token
    ) {
    }


    public record SignupRequest(
            String name,
            String email,
            String password
    ) {
    }


    public record ForgotPasswordRequest(
            String email
    ) {
    }


    public record SetPasswordRequest(
            String token,
            String password
    ) {
    }
}