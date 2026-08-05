package backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.Model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Add custom query methods if needed

    List<Task> findByAssigneeId(Long assigneeId);
    List<Task> findByAssignedById(Long assignedById);

}
