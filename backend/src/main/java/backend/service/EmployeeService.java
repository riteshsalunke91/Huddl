package backend.service;

import backend.Model.AuthToken;
import backend.Model.Employee;
import backend.Model.enums.AccountStatus;
import backend.Model.enums.AuthTokenType;
import backend.Model.enums.Role;
import backend.dto.EmployeeDtos.CreateEmployeeRequest;
import backend.dto.EmployeeDtos.EmployeeResponse;
import backend.repository.AuthTokenRepository;
import backend.repository.EmployeeRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private static final int INVITE_VALID_HOURS = 24;

    private final EmployeeRepository employeeRepository;
    private final AuthTokenRepository authTokenRepository;
    private final CurrentUserProvider currentUserProvider;


    public EmployeeService(
            EmployeeRepository employeeRepository,
            AuthTokenRepository authTokenRepository,
            CurrentUserProvider currentUserProvider
    ) {
        this.employeeRepository = employeeRepository;
        this.authTokenRepository = authTokenRepository;
        this.currentUserProvider = currentUserProvider;
    }


    public List<EmployeeResponse> listMyTeam() {

        Employee manager = currentUserProvider.getCurrentEmployee();

        return employeeRepository.findByManagerId(manager.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }


    public EmployeeResponse invite( CreateEmployeeRequest request) {

        Employee manager = currentUserProvider.getCurrentEmployee();


        if (employeeRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "An account with this email already exists"
            );
        }


        Employee employee = new Employee();

        employee.setName(request.name());
        employee.setEmail(request.email());
        employee.setPassword(null);
        employee.setRole(Role.EMPLOYEE);
        employee.setManager(manager);
        employee.setLeaveBalance(18);
        employee.setStatus(AccountStatus.INVITED);

        employeeRepository.save(employee);


        AuthToken token = new AuthToken();

        token.setEmployee(employee);
        token.setToken(UUID.randomUUID().toString());
        token.setType(AuthTokenType.INVITE);
        token.setExpiresAt(
                LocalDateTime.now().plusHours(INVITE_VALID_HOURS)
        );

        authTokenRepository.save(token);


        return toResponse(employee);
    }


    private EmployeeResponse toResponse(Employee e) {

        return new EmployeeResponse(
                e.getId(),
                e.getName(),
                e.getEmail(),
                e.getDestination(),
                e.getLeaveBalance(),
                e.getManager() != null
                        ? e.getManager().getName()
                        : null,
                e.getStatus()
        );
    }
}