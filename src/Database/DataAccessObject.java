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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author Dell
 */
public class DataAccessObject {

    public static Map<Integer, PlayersDTO> players;
    public static Map<Integer, GamesDTO> games;
    public static ArrayList<PlayersDTO> playerList;

    //Players Queries//
    public static PlayersDTO ObjectPlayerDTO(ResultSet result) {
        try {
            return new PlayersDTO(
                    result.getInt("id"),
                    result.getString("userName"),
                    result.getString("email"),
                    result.getString("password"),
                    result.getBoolean("status"),
                    result.getInt("score")
            );
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new PlayersDTO();
    }

    public static ArrayList<PlayersDTO> selectPlayer(PlayersDTO player) throws SQLException {
        //Show All Available Players//
        players = new LinkedHashMap<>();
        playerList = new ArrayList<>();
        ResultSet result;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
        PreparedStatement st = con.prepareStatement("SELECT * from PLAYERS ORDERD BY SCORE DESC ");
        result = st.executeQuery();
        while (result.next()) {
            players.put(result.getInt("id"), ObjectPlayerDTO(result));
            playerList.add(ObjectPlayerDTO(result));
        }

        return playerList;
    }

    public static int updatePlayerScore(int playerID, int playerScore) throws SQLException {
        //when player wins in online mode//
        int result = 0;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
        PreparedStatement st = con.prepareStatement("UPDATE PLAYER SET score = ? where ID = ? ");
        st.setInt(1, playerScore);
        st.setInt(2, playerID);
        result = st.executeUpdate();
        if (result > 0) { //query excuted successfully//
            PlayersDTO p;
            p = players.get(playerID);
            p.setScore(playerScore);
            players.replace(playerID, p);
        }

        return result;
    }

    public static int updatePlayerStatus(int ID, boolean status) throws SQLException {
        //when player logs-in in online mode//
        int result = 0;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
        PreparedStatement st = con.prepareStatement("UPDATE PLAYER SET STATUS = ? where ID=?");
        st.setBoolean(1, status);
        st.setInt(2, ID);
        result = st.executeUpdate();
        if (result > 0) {
            PlayersDTO p;
            p = players.get(ID);
            p.setStatus(status);
            players.replace(ID, p);
        }

        return result;
    }

    public static int insertNewPlayer(PlayersDTO dto) throws SQLException {
        //needed when sign up//
        int result = 0;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
        PreparedStatement st = con.prepareStatement("INSERT INTO PLAYERS (ID,userName,email,password,status,score) value (?,?,?,?,?,?)");
        st.setInt(1, dto.getId());
        st.setString(2, dto.getUserName());
        st.setString(3, dto.getEmail());
        st.setString(4, dto.getPassword());
        st.setBoolean(5, dto.isStatus());
        st.setInt(6, 0); //first time to signup there is no score
        result = st.executeUpdate();
        return result;
    }

    //Game queries//
    public void createGameTable() {
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
    
    public static GamesDTO objectGameDTO(ResultSet s){
        try {
            return new GamesDTO(
                    s.getInt("gameID"),
                    s.getInt("playerID"),
                    s.getString("steps"),
                    s.getString("date"),
                    s.getBoolean("win")
            );
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new GamesDTO();
        
    }

    public static void selectGame() {
        try {
           games = new LinkedHashMap<>();
            ResultSet result;
            DriverManager.registerDriver(new ClientDriver());
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
            PreparedStatement st = con.prepareStatement("SELECT * from GAME  ");
            result = st.executeQuery();
            while (result.next()) {
                games.put(result.getInt("gameID"), objectGameDTO(result));
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }

    public static int insertGame(GamesDTO dto) throws SQLException {
        int result = 0;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
        PreparedStatement st = con.prepareStatement("INSERT INTO GAME (gameID,playerID,steps,date,win) value (?,?,?,?,?)");
        st.setInt(1, dto.getGameID());
        st.setInt(2, dto.getPlayerID());
        st.setString(3, dto.getSteps());
        st.setString(4, dto.getDate());
        st.setBoolean(5, dto.isWin());
        result = st.executeUpdate();

        return result;
    }

}