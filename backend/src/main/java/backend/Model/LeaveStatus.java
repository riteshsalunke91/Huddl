package backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "leave_status")
public class LeaveStatus {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    PENDING, APPROVED, REJECTED;
}
    
    

