/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverside;

import Database.PlayersDTO;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ak882
 */
public class Message {
    String type;
    String userName;
    String email;
    String password;
    String opponentUserName;
    InetAddress ipAddress;
    int step;
    String XO;
    String opponentEmail;
    int location;
    Boolean status;
    //String status;
    String validation;
    ArrayList<PlayersDTO> playersList;
     List<Integer> steps ;
     int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
     
    public List<Integer> getSteps() {
        return steps;
    }

    public void setSteps(List<Integer> steps) {
        this.steps = steps;
    }

    public ArrayList<PlayersDTO> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(ArrayList<PlayersDTO> playersList) {
        this.playersList = playersList;
    }
    
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
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
    

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOpponentUserName() {
        return opponentUserName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOpponentUserName(String opponentUserName) {
        this.opponentUserName = opponentUserName;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getXO() {
        return XO;
    }

    public void setXO(String XO) {
        this.XO = XO;
    }

    public String getOpponentEmail() {
        return opponentEmail;
    }

    public void setOpponentEmail(String opponentEmail) {
        this.opponentEmail = opponentEmail;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Message()
    {}
   public Message(String type, String userName, String opponentUserName, InetAddress ipAddress, int step, String XO,String validation) {
        this.type = type;
        this.userName = userName;
        this.opponentUserName = opponentUserName;
        this.ipAddress = ipAddress;
        this.step = step;
        this.XO = XO;
        this.validation=validation;
    }
   
   

   
    
}
