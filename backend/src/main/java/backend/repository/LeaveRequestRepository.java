package backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.Model.LeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployeeId(Long employeeId);

    // Derived query joining through employee.manager.id — all leave requests
    // belonging to any employee who reports to this manager.
    List<LeaveRequest> findByEmployeeManagerId(Long managerId);

    
}
