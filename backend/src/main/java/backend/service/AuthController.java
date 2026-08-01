package backend.service;





import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.dto.AuthDtos.ForgotPasswordRequest;
import backend.dto.AuthDtos.LoginRequest;
import backend.dto.AuthDtos.LoginResponse;

import backend.dto.AuthDtos.SetPasswordRequest;
import backend.dto.AuthDtos.SignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final AuthService authService;

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // =========================
    // SIGNUP
    // =========================
    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(
            @Valid @RequestBody SignupRequest request) {

        LoginResponse response = authService.signup(request);
        return ResponseEntity.ok(response);
    }

    // =========================
    // FORGOT PASSWORD
    // =========================
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {

        authService.forgotPassword(request);

        return ResponseEntity.ok(
                Map.of("message",
                        "If the email exists, a reset link has been sent.")
        );
    }

    // =========================
    // SET PASSWORD
    // =========================
    @PostMapping("/set-password")
    public ResponseEntity<Map<String, String>> setPassword(
            @Valid @RequestBody SetPasswordRequest request) {

        authService.setPassword(request);

        return ResponseEntity.ok(
                Map.of("message",
                        "Password updated successfully.")
        );
    }

    // =========================
    // LOGOUT
    // =========================
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            @Valid @RequestBody LogoutRequest request) {

        // If using JWT, invalidate the token here.

        return ResponseEntity.ok(
                Map.of(
                        "message", "Logged out successfully",
                        "email", request.getEmail()
                )
        );
    }
}