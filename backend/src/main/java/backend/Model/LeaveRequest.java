package backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "leave_request")
public class LeaveRequest {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable= false)
    private String employeename;

    @Column(nullable= false)
    private String StartDate;

    @Column(nullable= false)
    private String endDate;

    @Column(nullable= false)
    private String reason;


    @Enumerated(EnumType.STRING)
    @Column(nullable= false)
    private  LeaveStatus status; leaveStatus.PENDING. ;
        
    

    public LeaveRequest() {}



    
}
