package backend.Model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@Column(nullable = false)
    private String message;

    // @Column(nullable = false)
    // private String recipient;

     @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipient _id")
    private String recipient ;

    @Column(nullable = false)
    private boolean read;

    public Notification() {
        this.read = false;
    }


    private LocalTime createAt;
    
    public Notification(String message, String recipient) {
        this.message = message;
        this.recipient = recipient;
        this.read = false;
    }

    // Getters 

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecipient() {
        return recipient;
    }



    //setters


    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
    
    
}
