package backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RestController
@RequestMapping("/api/auth")

public class AuthController{


    //account details
    @GetMapping("/auth/account")

   public ResponseEntity<AuthModel> getaccount(@RequestParam String name, @RequestParam String email){
        AuthModel auth =new AuthModel();
        auth.setName(name);
        auth.setEmail(email);
        auth.setPassword("password");
        return ResponseEntity.ok(auth);
    


}
    
