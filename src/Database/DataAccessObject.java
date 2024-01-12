/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author Dell
 */
public class DataAccessObject {
     private Connection con;

    public DataAccessObject() {
         try {
             DriverManager.registerDriver(new ClientDriver());
             con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
         } catch (SQLException ex) {
            ex.printStackTrace();
         }
    }
    
   
    public static ResultSet selectPlayer(String query) throws SQLException {
        //Show All Available Players//
        ResultSet result;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
        PreparedStatement st = con.prepareStatement(query);
        result = st.executeQuery();

        return result;
    }

    public static int updatePlayerScore(int playerID, int playerScore) throws SQLException {
        //when player wins in online mode//
        int result = 0;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
        PreparedStatement st = con.prepareStatement("UPDATE PLAYERS SET SCORE = ? where ID = ? ");
        st.setInt(1, playerScore);
        st.setInt(2, playerID);
        result = st.executeUpdate();
        return result;
    }

    public static int updatePlayerStatus(int ID, boolean status) {
        
             //when player logs-in in online mode//
             int result = 0;
              try {
             DriverManager.registerDriver(new ClientDriver());
             Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
             PreparedStatement st = con.prepareStatement("UPDATE PLAYERS SET STATUS = ? where ID=?");
             st.setBoolean(1, status);
             st.setInt(2, ID);
             result = st.executeUpdate();
             
         } catch (SQLException ex) {
             Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
         }
              return result;
    }

    public static int insertNewPlayer(PlayersDTO dto) throws SQLException {
        //needed when sign up//
        int result = 0;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
        PreparedStatement st = con.prepareStatement("INSERT INTO PLAYERS (ID,USERNAME,EMAIL"
                + ",PASSWORD,STATUS,SCORE) VALUES (?,?,?,?,?,?)");
        st.setInt(1, dto.getId());
        st.setString(2, dto.getUserName());
        st.setString(3, dto.getEmail());
        st.setString(4, dto.getPassword());
        st.setBoolean(5, false);
        st.setInt(6, 0); //first time to signup there is no score
        result = st.executeUpdate();
        return result;
    }

    //Game queries//
    public static void createGameTable() {
        try {
            String createTableSQL = "CREATE TABLE Game ( "
                    + "gameID INT, "
                    + "playerID INT, "
                    + "steps VARCHAR(50), "
                    + "date DATE, "
                    + "win BOOLEAN, "
                    + "PRIMARY KEY (gameID, playerID), "
                    + "FOREIGN KEY (playerID) REFERENCES players(ID) )";
            DriverManager.registerDriver(new ClientDriver());
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
            Statement s = con.createStatement();
            s.executeUpdate(createTableSQL);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void selectGame() throws SQLException{
            ResultSet result;
            DriverManager.registerDriver(new ClientDriver());
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
            PreparedStatement st = con.prepareStatement("SELECT * from GAME ");
            result = st.executeQuery();
  
    }

    public static int insertGame(GamesDTO dto) throws SQLException {
        int result = 0;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
        PreparedStatement st = con.prepareStatement("INSERT INTO GAME (gameID,playerID,steps,date,win) values (?,?,?,?,?)");
        st.setInt(1, dto.getGameID());
        st.setInt(2, dto.getPlayerID());
        st.setString(3, dto.getSteps());
        st.setString(4, dto.getDate());
        st.setBoolean(5, dto.isWin());
        result = st.executeUpdate();

        return result;
    }
    
    
    public static void createPlayerTable() {
        try {
            String createTableSQL = "CREATE TABLE PLAYERS ( "
                    + "ID INTEGER PRIMARY KEY AUTO_INCREMENT,"
                    + "USERNAME VARCHAR(40) not null, "
                    + "EMAIL VARCHAR(40) not null, "
                    + "PASSWORD VARCHAR(20) not null, "
                    + "STATUS BOOLEAN not null, "
                    + "SCORE INTEGER not null),"
                    + "PRIMARY KEY (ID)";
 
            DriverManager.registerDriver(new ClientDriver());
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
            Statement s = con.createStatement();
            s.executeUpdate(createTableSQL);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    
     public void close(){
    
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
    }
    

}
