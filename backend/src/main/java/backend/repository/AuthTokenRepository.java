package backend.repository;

import backend.Model.Employee;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthTokenRepository extends JpaRepository<Employee, Long> {

    List<Employee> FindByLogin(String Login);
    List<Employee> FindBySignup(String signup);
    List<Employee> FindByForGetpassword(String forgetpassword);
    List<Employee> FindBySetPassword(String setpassword);

}
