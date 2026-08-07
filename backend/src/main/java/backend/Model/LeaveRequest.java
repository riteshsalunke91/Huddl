// package backend.Model;

// import backend.Model.enums.LeaveStatus;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;
// import lombok.Getter;
// import lombok.Setter;

// @Entity
// @Getter
// @Setter

// @Table(name = "leave_request")
// public class LeaveRequest {

//     private static final LeaveStatus Status = null;

//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Id
//     private Long id;

//     @ManyToOne(fetch = FetchType.LAZY, optional = false)
//     @JoinColumn(name = "employee_id")
//     private Employee employee;


//     @Column(nullable= false)
//     private String StartDate;

//     @Column(nullable= false)
//     private String endDate;

//     @Column(nullable= false)
//     private String reason;


//     @Enumerated(EnumType.STRING)
//     @Column(nullable= false)
//     private  LeaveStatus status = LeaveStatus.PENDING;
        
    

//     public LeaveRequest() {}

//     //getter 
//     public long getId(){
//         return id;
//     }
//     public Employee getemployee(){
//         return employee;
//     }

//     public String  getstartdate(){
//         return StartDate;
//     }
// public String getenddate(){
//     return endDate;
// }

// public String getreason(){
//     return reason;
// }

// public LeaveStatus getstatus(){
//     return Status;
// }

// //setter

// public void setId(Long id ){
//     this.id = id;

// }

// public void  setemployee(Employee  employee){
//     this.employee = employee;

// }
//     public void setstartdate(String StratDate){
//         this.StartDate = StartDate;
//     }

//     public void setenddate(String Enddate){
//         this.endDate = Enddate;

//     }
//     public void setreason(String reason){
//         this.reason = reason;
//     }

//     public void setstatus(LeaveStatus status){
//         this.status = status;
//     }
// }



package backend.Model;

import backend.Model.enums.LeaveStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "leave_request")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;


    @Column(nullable = false)
    private LocalDate startDate;


    @Column(nullable = false)
    private LocalDate endDate;


    @Column(nullable = false)
    private String reason;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus status;


    private int days;


    public LeaveRequest() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }


    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}