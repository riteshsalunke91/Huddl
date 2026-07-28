package backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import backend.Model.Task;
import backend.Model.TaskStatus;
import jakarta.validation.constraints.NotNull;


public class TaskDtos  {
    
    @NotNull String title;
    String description;
    @NotNull @Email String  assigneeEmail;
     LocalDate deadline;
    private String photoUrl;


    //getter 

    public String gettitle(){
       return title;


    }

     public String getdescription(){
       return description;

     }

      public String getassignemail(){
       return assigneeEmail;

      }
       public LocalDate getdedline (){
       return deadline;

       }

        public String getphotourl(){
       return photoUrl;

        }
       //  setter 

        public void settitle(String tital){
       this.title = tital;
    
}  

public void setdescription(String description){
    this.description = description;

        }

public void setassigneeEmail(String assigneeEmail){
    this.assigneeEmail = assigneeEmail;

}
public void setdeadline(LocalDate deadline){
    this.deadline = deadline;

}

public void setphotoUrl(String photoUrl){
    this.photoUrl = photoUrl;

}
    }




