package backend.service;

import backend.Model.AuthToken;
import backend.Model.Employee;
import backend.Model.enums.Role;
import backend.dto.AuthDtos.*;
import backend.repository.AuthTokenRepository;
import backend.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private static final int TOKEN_VALID_HOURS = 24;

    private final EmployeeRepository employeeRepository;
    private final AuthTokenRepository authTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(EmployeeRepository employeeRepository, AuthTokenRepository authTokenRepository,
                        PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.employeeRepository = employeeRepository;
        this.authTokenRepository = authTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {
        Employee employee = employeeRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (employee.getStatus() == AccountStatus.INVITED) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "This account hasn't been activated yet — use your invite link to set a password");
        }

        if (!passwordEncoder.matches(request.password(), employee.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        return new LoginResponse(jwtUtil.generateToken(employee));
    }

    public LoginResponse signup(SignupRequest request) {
        if (employeeRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An account with this email already exists");
        }

        Employee employee = new Employee();
        employee.setName(request.name());
        employee.setEmail(request.email());
        employee.setPassword(passwordEncoder.encode(request.password()));
        employee.setRole(Role.MANAGER);
        employee.setManager(null);
        employee.setLeaveBalance(18);
        employee.setStatus(AccountStatus.ACTIVE);
        employeeRepository.save(employee);

        return new LoginResponse(jwtUtil.generateToken(employee));
    }

    public void forgotPassword(ForgotPasswordRequest request) {
        employeeRepository.findByEmail(request.email()).ifPresent(employee -> {
            if (employee.getStatus() == AccountStatus.INVITED) return;

            authTokenRepository.deleteByEmployeeIdAndType(employee.getId(), AuthTokenType.RESET);

            AuthToken token = new AuthToken();
            token.setEmployee(employee);
            token.setToken(UUID.randomUUID().toString());
            token.setType(AuthTokenType.RESET);
            token.setExpiresAt(LocalDateTime.now().plusHours(TOKEN_VALID_HOURS));
            authTokenRepository.save(token);

            // TODO: send token.getToken() via email. Never return it in the API response.
        });
        // Always appears to succeed to the caller, regardless of whether the email exists.
    }

    public void setPassword(SetPasswordRequest request) {
        AuthToken authToken = authTokenRepository.findByToken(request.token())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "This link is invalid or has already been used"));

        if (authToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            authTokenRepository.delete(authToken);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This link has expired");
        }

        Employee employee = authToken.getEmployee();
        employee.setPassword(passwordEncoder.encode(request.password()));
        employee.setStatus(AccountStatus.ACTIVE);
        employeeRepository.save(employee);

        authTokenRepository.delete(authToken);
    }
}
