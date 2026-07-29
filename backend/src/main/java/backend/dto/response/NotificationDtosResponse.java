package backend.dto.response;

import lombok.Data;

@Data
public class NotificationDtosResponse {

long id;
 String recipient;
 String type;
 boolean read;
 java.time.LocalDateTime createdAt;
   //getter 
public long getId(){
    return id;
   }
   public String getRecipient(){
    return recipient;
   }
   public String gettype(){
    return type;
   }
   public boolean getRead(){
    return read;
   }

   //setter

   public void setid (Long id){
    this.id = id;
   }
    public void setRecipient(String recipient){
        this.recipient= recipient;
    }

    public void settype(String type){
        this.type = type;
    }
    public void setRead(boolean read){
        this.read= read;
    }

    
    
}
