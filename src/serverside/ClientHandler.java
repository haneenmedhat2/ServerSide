/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverside;

import Database.DataAccessObject;
import Database.PlayersDTO;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ak882
 */
public class ClientHandler extends Thread {

    static Vector<ClientHandler> clientList = new Vector<ClientHandler>();

    String clientUserName;
    String opponentUserName;
    PrintStream output;
    BufferedReader input;
    Socket clientSocket;

    String email;
    String gsonMessage;
    Gson gson = new Gson();

    public ClientHandler(Socket clientSocket) {


        try {
            this.clientSocket = clientSocket;
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintStream(clientSocket.getOutputStream());
            clientList.add(this);
            start();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {

        }
    }

    @Override
    public void run() {
        try {
            while (clientSocket.isConnected()) {
                gsonMessage = input.readLine(); //msg is in the form of gson object//
                System.out.println(gsonMessage);
                messageHandler(gsonMessage);
//                getOnlinePlayers();
            }
        } catch (IOException ex) {
            System.out.println("client closed");

//            clientList.remove(this);
        } finally {
        }
    }

    public void messageHandler(String gsonMessage)
    {
        Message msg=gson.fromJson(gsonMessage,Message.class);

        switch(msg.getType())
        {
            case "signup":
                signUp(msg);
                break;
            case "login":
                login(msg);
                break;
            case "getOnline":
                getOnlinePlayers();
                break;
            case "invite":
                playRequest(msg);
                break;
            case "accepted":
                acceptedInvitation(msg);
                break;
            case "rejected":
                rejectedInvitation(msg);
                break;
            case "logOut":
                logOut(msg); 
                //logOutAlert(msg);
                //logOutUserFromDatabase(msg);
                break;
            case "sendMove":
                sendMove(msg);
                break;
            case "PlayerScore":
                playerScore(msg);
                break;
            case "opponentScore":
                opponentScore(msg);
                break;
            case "newGame":
                newGame(msg);
                break;
                case "logOutAvailablePlayers" :
                logOutAvailablePlayers(msg);
                break;

        }
    }

    public void login(Message msg) {
        try {
            Message response = new Message();
            response.setType("login");
            int isValid = DataAccessObject.validatePlayer(msg.email, msg.password);
            if (isValid > 0) {
                response.setValidation("valid");
                response.setEmail(msg.getEmail());
                email = msg.getEmail();
                DataAccessObject.updatePlayerStatus(msg.getEmail(), true);
                System.out.println("first case");
//                output.println(gson.toJson(response));
            } else if (isValid == 0) {
                response.setValidation("invalidPassword");
            } else {
                response.setValidation("emailNotFound");
            }
            output.println(gson.toJson(response));
            output.flush();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    
    /*
    public void logOutAlert(Message msg){     
        String logOutAlert = "logOutShowAlert";
        if(msg.getShowAlertOnLogOut().equals(logOutAlert)){
            sendLogOutAlert(msg);
        }else{
            System.out.println("Server : LogOut Alert doesn't work");
        }
    }
    */
    public void signUp(Message msg)
    {
        Message response=new Message();
        response.setType("signup");
        PlayersDTO player = new PlayersDTO();
        player.setEmail(msg.getEmail());
        player.setPassword(msg.getPassword());
        player.setUserName(msg.getUserName());

        try {
            DataAccessObject.insertNewPlayer(player);
            response.setValidation("true");
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            response.setValidation("false");
        }
        output.println(gson.toJson(response));
        output.flush();
    }
//    public void login(Message msg )
//    {
//        try {
//            Message response= new Message();
//            response.setType("login");
//            int isValid=DataAccessObject.validatePlayer(msg.email,msg.password);
//            if(isValid>0){
//                response.setValidation("valid");
//                response.setEmail(msg.getEmail());
//                email=msg.getEmail();
//                DataAccessObject.updatePlayerStatus(msg.getEmail(),true);
//                System.out.println("first case");
//                
//            }
//            else if (isValid == 0)
//            {
//                response.setValidation("invalidPassword");
//            }
//            else{
//                response.setValidation("emailNotFound");
//            }
//            output.println(gson.toJson(response));
//            output.flush();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
    public void logOut(Message msg)
    {
        try {
            Message message=new Message();
            message.setType("logOut");
            message.setEmail(email);
            for(ClientHandler client:clientList)
            {
                if(msg.getOpponentEmail().equals(client.email))
                {
                    String opponentEmail = client.email;
                    message.setOpponentEmail(opponentEmail);
                    message.setShowAlertOnLogOut("logOutShowAlert");
                    DataAccessObject.updatePlayerStatus(msg.getEmail(),false);
                    client.output.println(gson.toJson(message));
                    client.output.flush();
                }
            }           
            
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void logOutAvailablePlayers(Message msg)
    {
        try {
            Message message=new Message();
            message.setType("logOutAvailablePlayers");
            message.setEmail(email);
            DataAccessObject.updatePlayerStatus(msg.getEmail(),false);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /*
    public void logOutUserFromDatabase(Message msg){
        
        String userEmail = msg.getEmail();
        String type = msg.getType();
        
        if(type=="logOut"){
            try { 
                DataAccessObject.updatePlayerStatus(userEmail, false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    */
    public void getOnlinePlayers()
    {
        try {
            Message response = new Message();
            response.setType("getOnline");
            ArrayList<PlayersDTO> playersList = DataAccessObject.getOnlinePlayers(email);
            response.setPlayersList(playersList);
            System.out.println(gson.toJson(response));
            output.println(gson.toJson(response));
            output.flush();
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playRequest(Message request) {
        for (ClientHandler client : clientList) {
            if (request.getEmail().equals(client.email)) {
                Message response = new Message();
                response.setType("invite");
                response.setEmail(email);
                client.output.println(gson.toJson(response));
                client.output.flush();
            }
        }
    }

    public void acceptedInvitation(Message request) {
        for (ClientHandler client : clientList) {
            if (request.getEmail().equals(client.email)) {
                Message response = new Message();
                response.setType("accepted");
                response.setEmail(email);
                client.output.println(gson.toJson(response));
                client.output.flush();
            }
        }
    }

    public void rejectedInvitation(Message request) {
        for (ClientHandler client : clientList) {
            if (request.getEmail().equals(client.email)) {
                Message response = new Message();
                response.setType("rejected");
                response.setEmail(email);
                client.output.println(gson.toJson(response));
                client.output.flush();
            }
        }
    }

    public void returnAllPlayers() {
        for (ClientHandler client : clientList) {
            System.out.println(client.email);
        }
    }

    public void sendInvite(String message) {
        Message msg = gson.fromJson(message, Message.class);
        for (ClientHandler client : clientList) {
            if (client.email.equals(msg.getOpponentUserName())) {
                client.output.println(message);
            }

        }
    }

    public void sendMove(Message msg) {

        for (ClientHandler client : clientList) {
            if (msg.getOpponentEmail().equals(client.email)) {
                String opponentMail = email;
                int location = msg.getLocation();
                String XO = msg.getXO();
                Message move = new Message();
                move.setType("retriveMove");
                move.setEmail(opponentMail);
                move.setLocation(location);
                move.setXO(XO);

                System.out.println("send move data  ");
                System.out.println(gson.toJson(move));
                client.output.println(gson.toJson(move));
                client.output.flush();
            }
        }

    }

    public void playerScore(Message msg) {
        String mail = msg.getEmail();
        int score = msg.getScore();
        try {
            int result = DataAccessObject.updatePlayerScore(mail);
            if (result > 0) {
                System.out.println("Score is updated");

            } else {
                System.out.println("problem in updating score");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

//        for (ClientHandler client : clientList) {
//            if (msg.getOpponentEmail().equals(client.email)) {
//                Message response = new Message();
//                response.setType("updateOpponentScore");
//                response.setOpponentEmail(email);
//                response.setScore(score);
//                System.out.println(gson.toJson(response));
//                client.output.println(gson.toJson(response));
//                client.output.flush();
//
//            }
//        }

    }

    public void opponentScore(Message msg) {
        
        String mail = msg.getEmail();
        int score = msg.getScore();
        try {
            int result = DataAccessObject.updatePlayerScore(mail);
            if (result > 0) {
                System.out.println("Score is updated");

            } else {
                System.out.println("problem in updating score");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

//        for (ClientHandler client : clientList) {
//            if (msg.getOpponentEmail().equals(client.email)) {
//                Message response = new Message();
//                response.setType("updateScore");
//                response.setOpponentEmail(email);
//                response.setScore(score);
//                System.out.println(gson.toJson(response));
//                client.output.println(gson.toJson(response));
//                client.output.flush();
//
//            }
//        }
        
//        String email = msg.getOpponentEmail();
//        int score = msg.getScore();
//        Message response = new Message();
//        response.setType("updateOpponentScore");
//        response.setOpponentEmail(email);
//        response.setScore(score);
//        System.out.println(gson.toJson(response));
//        output.println(gson.toJson(response));
//        output.flush();

    }
    public void newGame(Message msg)
    {
        for(ClientHandler client:clientList)
        {
            if(client.email.equals(msg.getOpponentEmail()))
            {
                Message response=new Message();
                response.setType("newGame");
                client.output.println(gson.toJson(response));
                client.output.flush();
            }
        }
    }

    
    public void sendMove(Message msg) {

        for (ClientHandler client : clientList) {
            if (msg.getOpponentEmail().equals(client.email)) {
                String opponentMail = email;
                int location = msg.getLocation();
                String XO = msg.getXO();
                Message move = new Message();
                move.setType("retriveMove");
                move.setEmail(opponentMail);                     
                move.setLocation(location);
                move.setXO(XO);

                System.out.println("send move data  ");
                System.out.println(gson.toJson(move));
                client.output.println(gson.toJson(move));
                client.output.flush();
            }
        }
    }
    /*
    public void sendLogOutAlert(Message msg){
        String logOutAlert = "logOutShowAlert";       
        Message message=new Message();
        
        
        
        for(ClientHandler client: clientList)
        {
            if(client.email.equals(msg.getOpponentEmail()))
            {
               message.setShowAlertOnLogOut(logOutAlert);
               client.output.println(gson.toJson(message));
               client.output.flush();
            }
        }

    }
    */
}
