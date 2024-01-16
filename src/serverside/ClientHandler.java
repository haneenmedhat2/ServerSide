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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ak882
 */
public class ClientHandler extends Thread{
    static Vector<ClientHandler> clientList=new Vector<ClientHandler>();
    String clientUserName;
    String opponentUserName;
    PrintStream output;
    BufferedReader input;
    Socket clientSocket;
    Gson gson =new Gson();
    String email;
    public ClientHandler(Socket clientSocket)
    {
        try {
            this.clientSocket=clientSocket;
            input = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintStream(clientSocket.getOutputStream());
//            this.clientUserName=input.readLine();
//            this.opponentUserName=gson.fromJson(input.readLine(),Message.class).opponentUserName;
            clientList.add(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{

        }
    }
    @Override
    public void run()
    {
        try{
            while(clientSocket.isConnected())
            {
                String gsonMessage=input.readLine();
                System.out.println(gsonMessage);
                messageHandler(gsonMessage);
//                getOnlinePlayers();
            }
        }
        catch(IOException ex)
        {
            System.out.println("client closed");
//            clientList.remove(this);

        }
        finally{
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
        }
    }
    public void login(Message msg)
    {
        try {
            Message response=new Message();
            response.setType("login");
            int isValid = DataAccessObject.validatePlayer(msg.email, msg.password);
            if(isValid > 0)
            {
                response.setValidation("valid");
                response.setEmail(msg.getEmail());
                email=msg.getEmail();
                DataAccessObject.updatePlayerStatus(msg.getEmail(),true);
                System.out.println("first case");
//                output.println(gson.toJson(response));
            }
            else if(isValid==0)
            {
                response.setValidation("invalidPassword");
            }
            else{
                response.setValidation("emailNotFound");
            }
            output.println(gson.toJson(response));
            output.flush();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void signUp(Message msg)
    {
        Message response=new Message();
        response.setType("signup");
        PlayersDTO player=new PlayersDTO();
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
    public void getOnlinePlayers()
    {
        try {
            Message response=new Message();
            response.setType("getOnline");
            ArrayList<PlayersDTO> playersList=DataAccessObject.getOnlinePlayers(email);
            response.setPlayersList(playersList);
            System.out.println(gson.toJson(response));
            output.println(gson.toJson(response));
            output.flush();
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void playRequest(Message request)
    {
        for(ClientHandler client:clientList)
        {
            if(request.getEmail().equals(client.email))
            {
                Message response=new Message();
                response.setType("invite");
                response.setEmail(email);
                client.output.println(gson.toJson(response));
                client.output.flush();
            }
        }
    }
    public void acceptedInvitation(Message request)
    {
        for(ClientHandler client:clientList)
        {
            if(request.getEmail().equals(client.email))
            {
                Message response= new Message();
                response.setType("accepted");
                response.setEmail(email);
                client.output.println(gson.toJson(response));
                client.output.flush();
            }
        }
    }
    public void rejectedInvitation(Message request)
    {
        for(ClientHandler client:clientList)
        {
            if(request.getEmail().equals(client.email))
            {
                Message response= new Message();
                response.setType("rejected");
                response.setEmail(email);
                client.output.println(gson.toJson(response));
                client.output.flush();
            }
        }
    }
    public void returnAllPlayers()
    {
        for(ClientHandler client: clientList)
        {
            System.out.println(client.email);
        }
//        try {
//            ArrayList<PlayersDTO> onlinePlayers=new ArrayList<>();
//            ArrayList<PlayersDTO> players=DataAccessObject.selectPlayer();
//            for(ClientHandler client : clientList)
//            {
//                for(PlayersDTO player:players)
//                {
//                    if(client.email.equals(player.getEmail()))
//                    {
//                        onlinePlayers.add(player);
//                    }
//                }
//            }
//            String online=gson.toJson(onlinePlayers);
//            System.out.println(online);
//        } catch (SQLException ex) {
//            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    public void sendInvite(String message)
    {
        Message msg=gson.fromJson(message, Message.class);
        for(ClientHandler client: clientList)
        {
            if(client.email.equals(msg.getOpponentUserName()))
            {
               client.output.println(message);
            }
        }
    }
}
