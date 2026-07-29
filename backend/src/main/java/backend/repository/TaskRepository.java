package backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.Model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Add custom query methods if needed

    List<Task> findByEmployeeId(Long employeeId);
    List<Task> findByTitale(Long employeeId, String title);

    List<Task> FindBydescription(Long employeeId, String description);

    List<Task> FindByStatus(Long employeeId, String status);
    List<Task> FindByDeadlineBetween(Long employeeId, String startDate, String endDate);

}
