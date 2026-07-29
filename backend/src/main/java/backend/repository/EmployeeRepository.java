package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Model.Employee;

import java.util.List;




public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Add custom query methods if needed
    List<Employee> findByName(String name);
    List<Employee>  findByEmail(String email);
    

    
}
