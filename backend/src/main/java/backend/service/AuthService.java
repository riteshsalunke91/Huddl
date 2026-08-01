package backend.service;


import org.springframework.stereotype.Service;

@Service
public class AuthService{

    public boolean authenticate(String username, String password) {
        println("Email from frontend: ["+ Request.getEmail() +"]");
    }

        public LoginResponseDtos login(String username, String password) {
            System.out.println("Email from frontend: ["+ Request.getEmail() +"]");
        // Implement your authentication logic here
        // For example, you can check the username and password against a database or an external service
        // Return true if authentication is successful, false otherwise
        return "admin".equals(username) && "password".equals(password);
    
}
}

