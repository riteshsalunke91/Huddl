package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Add custom query methods if needed

    

    
}
