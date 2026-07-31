package backend.dto;

public class NotificationDtos {
 

    public record NotificationResponse(
        Long id,
        String message,
        String read,
        String createAt
    ) {
    }


    public record UpdateReadRequest(

    boolean read
    ) {
    }
    
}
