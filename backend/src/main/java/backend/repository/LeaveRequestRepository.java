package backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.Model.LeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    // Add custom query methods if needed
List<LeaveRequest> findByEmployeeId(Long employeeId);
List<LeaveRequest> findByStatus(String status);
List<LeaveRequest> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

List<LeaveRequest> findByEndDateBetween(LocalDate startDate, LocalDate endDate);
List<LeaveRequest> findByName(String name);

    
}
