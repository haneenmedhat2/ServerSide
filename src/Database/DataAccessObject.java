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

    final static String URL = "jdbc:derby://localhost:1527/Toe";

    //Players Queries//
    public static PlayersDTO ObjectPlayerDTO(ResultSet result) throws SQLException {

        return new PlayersDTO(
                result.getInt("id"),
                result.getString("userName"),
                result.getString("email"),
                result.getString("password"),
                result.getBoolean("status"),
                result.getInt("score")
        );
    }

    public static ResultSet selectPlayer() throws SQLException {
        //Show All Available Players//
        ResultSet result;
        DriverManager.registerDriver(new ClientDriver());

        Connection con = DriverManager.getConnection(URL, "root", "root");
        PreparedStatement st = con.prepareStatement("SELECT * from PLAYERS ORDERD BY SCORE DESC ");

        result = st.executeQuery();

        return result;
    }

    public static String loginInfo(PlayersDTO dto) throws SQLException {
        //Show All Available Players//
        ResultSet result;
        String valid; 
        DriverManager.registerDriver(new ClientDriver());

        Connection con = DriverManager.getConnection(URL, "root", "root");
        PreparedStatement st = con.prepareStatement("SELECT * from PLAYERS WHERE EMAIL=? AND PASSWORD=? ");
        st.setString(1, dto.getEmail());
        st.setString(2, dto.getPassword());
        result = st.executeQuery();
        if (result.next()) {
            valid="true";
        }
        else{
             valid="false";
        }

        return valid;
    }

    public static int updatePlayerScore(int playerID, int playerScore) throws SQLException {
        //when player wins in online mode//
        int result = 0;
        DriverManager.registerDriver(new ClientDriver());

        Connection con = DriverManager.getConnection(URL, "root", "root");
        PreparedStatement st = con.prepareStatement("UPDATE PLAYER SET score = ? where ID = ? ");

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
        Connection con = DriverManager.getConnection(URL, "root", "root");
        PreparedStatement st = con.prepareStatement("INSERT INTO PLAYERS (userName,email,password,status,score) values (?,?,?,?,?)");
        st.setString(1, dto.getUserName());
        st.setString(2, dto.getEmail());
        st.setString(3, dto.getPassword());
        st.setBoolean(4, false);
        st.setInt(5, 0); //first time to signup there is no score
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
            Connection con = DriverManager.getConnection(URL, "root", "root");
            Statement s = con.createStatement();
            s.executeUpdate(createTableSQL);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void selectGame() throws SQLException {
        ResultSet result;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(URL, "root", "root");
        PreparedStatement st = con.prepareStatement("SELECT * from GAME  ");
        result = st.executeQuery();

    }

    public static int insertGame(GamesDTO dto) throws SQLException {
        int result = 0;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(URL, "root", "root");
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
            String createTableSQL = "CREATE TABLE players ("
                    + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "username VARCHAR(255) NOT NULL,"
                    + "email VARCHAR(255) NOT NULL,"
                    + "password VARCHAR(255) NOT NULL,"
                    + "status BOOLEAN NOT NULL,"
                    + "score INT NOT NULL"
                    + ")";
            DriverManager.registerDriver(new ClientDriver());
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
            Statement s = con.createStatement();
            s.executeUpdate(createTableSQL);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void close() {

        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
