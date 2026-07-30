package backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column(nullable = false,  unique = true)
    private String name;

    @Column (nullable = false)
    private String password;


    @Column(nullable = false)
    private String email;

 @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String role;

    private String Destination;

    private String leaveStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private String manager;


    private Employee ()  {};


    //getter 

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getpassword(){
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getDestination() {
        return Destination;
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public String getManager() {
        return manager;
    }


    //setter

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setpassword(String password)
    {
        this.password= password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
