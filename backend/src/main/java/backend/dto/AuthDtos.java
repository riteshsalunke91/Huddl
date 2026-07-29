package backend.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthDtos {

    @NotNull String name;
    @NotNull @Email String email;
    private String password;
    //getter
    public  String getname(){
        return name;

    }
    public String getemail(){
        return  email;
        
    }
    public String getpassword(){
        return password;
    }

    //setter
public void setname(String name){
        this.name = name;
    }

    public void setemail(String email){
        this.email = email;
    }

    public void setpassword(String password){
        this.password = password;
    }
  
}
