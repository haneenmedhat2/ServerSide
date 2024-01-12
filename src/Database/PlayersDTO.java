/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author Dell
 */
public class PlayersDTO {
    //DTO -> Data Transfer Object//
    //Java Bean//
    //POJO-> Plain Old Java Object//
    private int id;
    private String userName;
    private String email;
    private String password;
    private boolean status;
    private int score;
    
    public PlayersDTO(){
    
    }  

    public PlayersDTO(int id, String userName, String email, String password, boolean status, int score) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.status = status;
        this.score = score;
    }

    
     public PlayersDTO(int id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;

    }
     
     
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    


    
    
}
