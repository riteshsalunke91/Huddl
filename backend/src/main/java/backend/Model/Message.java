// package backend.Model;

// import java.time.LocalTime;

// import jakarta.persistence.*;


// @Entity
// @Table(name= "message")
// public class Message {


//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long Id;

//     @ManyToOne(fetch = FetchType.LAZY, optional = false)
//         @JoinColumn(name  = "sender_name")
//         private String Sender;


//         @ManyToOne(fetch = FetchType.LAZY, optional = false)
//     @JoinColumn(name = "recipient _id")
//     private String recipient ;

//     @Column(nullable = false)
//     private String text;

//     @Column(nullable = false)
//     private String imageurl;

//     @Column (nullable = false)
//     private String read;


//     private LocalTime createAt;


    
//     public Long getId() {
//         return Id;
//     }

//     public void setId(Long id) {
//         Id = id;
//     }

//     public String getSender() {
//         return Sender;
//     }

//     public void setSender(String sender) {
//         Sender = sender;
//     }

//     public String getRecipient() {
//         return recipient;
//     }

//     public void setRecipient(String recipient) {
//         this.recipient = recipient;
//     }

//     public String getText() {
//         return text;
//     }

//     public void setText(String text) {
//         this.text = text;
//     }

//     public String getImageurl() {
//         return imageurl;
//     }

//     public void setImageurl(String imageurl) {
//         this.imageurl = imageurl;
//     }

//     public String getRead() {
//         return read;
//     }

//     public void setRead(String read) {
//         this.read = read;
//     }

//     public LocalTime getCreateAt() {
//         return createAt;
//     }

//     public void setCreateAt(LocalTime createAt) {
//         this.createAt = createAt;
//     }


    

    
// }


package backend.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Employee sender;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private Employee recipient;


    private String text;

    private String imageUrl;

    private boolean read;

    private LocalDateTime createdAt;


    public Message() {
    }


    public Long getId() {
        return id;
    }

    public Employee getSender() {
        return sender;
    }

    public Employee getRecipient() {
        return recipient;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isRead() {
        return read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setSender(Employee sender) {
        this.sender = sender;
    }

    public void setRecipient(Employee recipient) {
        this.recipient = recipient;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}