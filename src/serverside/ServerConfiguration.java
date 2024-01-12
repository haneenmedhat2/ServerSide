/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverside;

import java.net.ServerSocket;

/**
 *
 * @author Dell
 */
public class ServerConfiguration {
    private static ServerSocket serverSocket;

    public static ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static void setServerSocket(ServerSocket serverSocket) {
        ServerConfiguration.serverSocket = serverSocket;
    }
    
    
    
}
