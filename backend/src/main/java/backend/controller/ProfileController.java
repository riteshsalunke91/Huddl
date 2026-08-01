package backend.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.ProfileDtos;
import backend.dto.ProfileDtos.ProfileResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RestController
@RequestMapping("apl/profiles")
public class ProfileController {

    private final ProfileService profileservice;

    //getprofile
    @GetMapping("/getprofile")
    public ResponseEntity<ProfileResponse> getProfile()
     {
        return ResponseEntity.ok(ProfileService.getcurrentprofile()); 
        
    }

    //updateprofile
    @GetMapping("/updateprofile")
        public ResponseEntity<ProfileResponse> updateProfile(){
            return ResponseEntity.ok(profileservice.updateprofile());
        
    }
    //changeprofile
     @GetMapping("/changeprofile")
        
    public ResponseEntity<ProfileResponse> changeprofile(){
        return ResponseEntity.ok(profileservice.changeprofile());
    }



    
}
