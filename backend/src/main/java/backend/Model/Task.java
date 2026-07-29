package backend.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignee_id")
    private Employee assignee;      

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assigned_by_id")
    private Employee assignedBy;    

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.TODO;

    private LocalDate deadline;         
    private String photoUrl;            
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime completedAt; 

    public Task() {}

    // getters and setters for every field

    //gettter
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Employee getAssignee() {
        return assignee;
    }
    public Employee getAssignedBy() {
        return assignedBy;
    }
    public TaskStatus getStatus() {
        return status;
    }
    public LocalDate getDeadline() {
        return deadline;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    //setter

    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setAssignee(Employee assignee) {
        this.assignee = assignee;
    }
    public void setAssignedBy(Employee assignedBy) {
        this.assignedBy = assignedBy;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
    public void markAsCompleted() {
        this.status = TaskStatus.DONE;
        this.completedAt = LocalDateTime.now();
    }
}