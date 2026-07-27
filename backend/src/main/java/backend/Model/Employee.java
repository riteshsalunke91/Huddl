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


    @Column(nullable = false)
    private String name;

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

}
