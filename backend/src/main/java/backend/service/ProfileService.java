package backend.service;

import backend.Model.Employee;
import backend.dto.ProfileDtos.*;

import backend.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProfileService {

    private final EmployeeRepository employeeRepository;
    private final CurrentUserProvider currentUserProvider;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(EmployeeRepository employeeRepository, CurrentUserProvider currentUserProvider,
                          PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.currentUserProvider = currentUserProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public ProfileResponse getCurrentProfile() {
        return toResponse(currentUserProvider.getCurrentEmployee());
    }

    public ProfileResponse updateName(UpdateProfileRequest request) {
        Employee current = currentUserProvider.getCurrentEmployee();
        current.setName(request.name());
        employeeRepository.save(current);
        return toResponse(current);
    }

    public ProfileResponse changePassword(ChangePasswordRequest request) {
        Employee current = currentUserProvider.getCurrentEmployee();

        if (!passwordEncoder.matches(request.currentPassword(), current.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        }

        current.setPassword(passwordEncoder.encode(request.newPassword()));
        employeeRepository.save(current);
        return toResponse(current);
    }

    private ProfileResponse toResponse(Employee e) {
        return new ProfileResponse(
                e.getId(), e.getName(), e.getEmail(), e.getRole(), e.getDesignation(),
                e.getManager() != null ? e.getManager().getName() : null,
                e.getLeaveBalance()
        );
    }
}

