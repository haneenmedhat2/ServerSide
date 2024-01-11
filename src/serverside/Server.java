/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverside;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ak882
 */
public class Server {
    
        Socket client;
        ServerSocket server ;
        public Server()
        {
            try {
                // TODO code application logic here
                server = new ServerSocket(6000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                Socket socket = server.accept();
                                new ClientHandler(socket);
                            }
                            } catch (Exception e) {
                                try {
                                    server.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                        }
                }   
            }).start();
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    
}


