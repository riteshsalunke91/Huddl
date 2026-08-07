package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Model.Employee;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Add custom query methods if needed
    Optional<Employee> findByEmail(String email);
    // boolean existsByEmail(String email);
    // List<Employee> findByManagerId(Long managerId);

    
}
