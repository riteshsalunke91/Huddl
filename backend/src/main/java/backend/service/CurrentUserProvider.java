package backend.service;

import backend.Model.Employee;
import backend.repository.EmployeeRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserProvider {

    private final EmployeeRepository employeeRepository;

    public CurrentUserProvider(
            EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    public Employee getCurrentEmployee() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()) {

            throw new RuntimeException(
                    "User is not authenticated"
            );
        }

        String email = authentication.getName();

        return employeeRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Current employee not found"
                        )
                );
    }
}