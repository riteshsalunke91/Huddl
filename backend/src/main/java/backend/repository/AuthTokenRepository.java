package backend.repository;

import backend.Model.AuthToken;
import backend.Model.Employee;
import backend.Model.enums.AuthTokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findByToken(String token);
    void deleteByEmployeeIdAndType(Long employeeId, AuthTokenType type);

}
