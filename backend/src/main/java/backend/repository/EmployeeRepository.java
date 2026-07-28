package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.Model.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Add custom query methods if needed

    

    
}
