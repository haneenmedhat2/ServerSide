/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverside;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author Dell
 */
public class DataAccessObject {
    
    
    public DataAccessObject(){
        
    }
    
    public static int insertNewPlayer(PlayersDTO dto) throws SQLException{  //needed when sign up//
        int result=0;
            DriverManager.registerDriver(new ClientDriver());
           Connection con= DriverManager.getConnection("jdbc:derby://localhost:1527/Toe","root","root");
           PreparedStatement st= con.prepareStatement("INSERT INTO PLAYERS (ID,userName,email,password,status,score) value (?,?,?,?,?,?)");
           st.setInt(1, dto.getId());
           st.setString(2, dto.getUserName());
           st.setString(3,dto.getEmail());
           st.setString(4,dto.getPassword());
           st.setBoolean(5, dto.isStatus());
           st.setInt(6,0); //first time to signup there is no score
          result= st.executeUpdate();
            
        return result;
    }
    
    public static int updatePlayerStatus(PlayersDTO dto) throws SQLException{ //when player logs-in in online mode//
       int result=0; 
       DriverManager.registerDriver(new ClientDriver());
       Connection con= DriverManager.getConnection("jdbc:derby://localhost:1527/Toe","root","root");
       PreparedStatement st= con.prepareStatement("UPDATE PLAYER SET STATUS = ? where email= ? AND password= ?");
           st.setBoolean(1,dto.isStatus());
           st.setString(2,dto.getEmail());
           st.setString(3,dto.getPassword());
           result= st.executeUpdate();
           
        return result;   
    }
    
        public static int updatePlayerScore(PlayersDTO dto) throws SQLException{ //when player wins in online mode//
       int result=0; 
       DriverManager.registerDriver(new ClientDriver());
       Connection con= DriverManager.getConnection("jdbc:derby://localhost:1527/Toe","root","root");
       PreparedStatement st= con.prepareStatement("UPDATE PLAYER SET score = ? where ID = ? ");
       st.setInt(1, dto.getScore());
       st.setInt(2, dto.getId());
       result= st.executeUpdate();
           
        return result;   
    }
    
    
    
    public static ResultSet selectPlayer(String query) throws SQLException{ //Available Players//
       ResultSet result;
       DriverManager.registerDriver(new ClientDriver());
       Connection con= DriverManager.getConnection("jdbc:derby://localhost:1527/Toe","root","root");
       PreparedStatement st= con.prepareStatement(query);
          result= st.executeQuery();
          
        return result;   
    }
    
  
    
    
}
