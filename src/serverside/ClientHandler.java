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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ak882
 */
public class ClientHandler extends Thread {
    
       //every time new client connects to the server,is added to the list//
    static List<ClientHandler> clientList = new ArrayList<ClientHandler>();
    String clientUserName;
    String opponentUserName;
    PrintStream output;
    BufferedReader input;
    Socket clientSocket;
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
                String gsonMessage = input.readLine(); //msg is in the form of gson object//
                System.out.println(gsonMessage);
                messageHandler(gsonMessage);
            }
        } catch (IOException ex) {
            System.out.println("client closed");
        } finally {
            clientList.remove(this);
        }
    }

    
    //This method converts json string into object of Message class//
    public void messageHandler(String gsonMessage) {  
        Message msg = gson.fromJson(gsonMessage, Message.class);
        switch (msg.getType()) {
            case "signup":
                signUp(msg);
                break;
            case "login":
                login(msg);
                break;
        }
    }

    public void login(Message msg) {
        String valid="true";
        try {
            PlayersDTO player = new PlayersDTO();
            player.setEmail(msg.getEmail());
            player.setPassword(msg.getPassword());
            valid=DataAccessObject.loginInfo(player);
            if(valid.equalsIgnoreCase("true")){
                 output.println("true");  
            }
            else{
                output.println("false");  
            }
             
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    public void signUp(Message msg) {
        PlayersDTO player = new PlayersDTO();
        player.setEmail(msg.getEmail());
        player.setPassword(msg.getPassword());
        player.setUserName(msg.getUserName());
        String response;
        try {
            DataAccessObject.insertNewPlayer(player);
            response = "true";
        } catch (SQLException ex) {
            ex.printStackTrace();
            response = "false";
        }
        output.println(response);
    }

    public void broadCastMessage(String message) {
        for (ClientHandler client : clientList) {
            client.output.println(message);
            System.out.println(clientList.size());

        }
    }
}
