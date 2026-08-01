// package backend.controller;

// import java.util.Map;

// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import backend.dto.AuthDtos.LoginRequest;
// import backend.dto.AuthDtos.LoginResponse;
// import backend.dto.AuthDtos.SetPasswordRequest;
// import backend.dto.AuthDtos.SignupRequest;
// import jakarta.validation.Valid;
// import lombok.val;

// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.PostMapping;



// @Controller
// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

// //login
// @PostMapping("/Login")
// public ResponseEntity<LoginResponse> Login(@Valid @RequestBody LoginRequest request){
//    return ResponseEntity.ok(AuthService.(Login)request);
// }


// //signup
// @PostMapping("/signup")
// public ResponseEntity<LoginResponse> Signup(@Valid @RequestBody SignupRequest request) {
//     return ResponseEntity.ok(AuthService.(Signup) request);
    

// }

// //forgetpassword

// @PostMapping("/ForGetPassword")
// public ResponseEntity<Map<String, String>> Forgetpassword( @Valid  @RequestBody ForGetPasswordRequest request) {
//    AuthService.forgotPassword(request);
//     return ResponseEntity.ok(map.of("message", "f that email exists, a reset link has been sent")
//    );

// }

// //setpassword
// @PostMapping("/setpasswoed")
//     public ResponseEntity<Map<String, String>> SetPassword(@Valid @RequestBody SetPasswordRequest request){
//         AuthService.setpassword(request);
//         return ResponseEntity.ok(Map.of("message", "Password set Successfully"));
//     }


//     //logout

//      @PostMapping("/logout")
//     public ResponseEntity<LoginResponse> logout(@Valid @RequestBody LogoutRequest request) {
//         return ResponseEntity.ok(Map.of(
//             "message", "Logged out successfully",
//             "email",   request.getEmail()
//         ));
//     }

    
// }


    

package backend.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import backend.dto.AuthDtos.ForgotPasswordRequest;
import backend.dto.AuthDtos.LoginRequest;
import backend.dto.AuthDtos.LoginResponse;
import backend.dto.AuthDtos.SetPasswordRequest;
import backend.dto.AuthDtos.SignupRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {
return ResponseEntity.ok(authService.login(request));
    }

    // =========================
    // SIGNUP
    // =========================
    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(
            @Valid @RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
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
                        "If that email exists, a reset link has been sent.")
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
