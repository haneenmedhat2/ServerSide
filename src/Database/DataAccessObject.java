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

            return new PlayersDTO(
                    result.getInt("id"),
                    result.getString("userName"),
                    result.getString("email"),
                    result.getString("password"),
                    result.getBoolean("status"),
                    result.getInt("score"),
                    result.getBoolean("available")
            );

            DriverManager.registerDriver(new ClientDriver());
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public static ArrayList<PlayersDTO> selectPlayer() throws SQLException {

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
    
    public static ResultSet selectOnline() throws SQLException {
        //Show All Available Players//
        ResultSet result;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(URL, "root", "root");
        PreparedStatement st = con.prepareStatement("SELECT USERNAME from PLAYERS WHERE STATUS=? ");
        st.setBoolean(1, true);
        result = st.executeQuery();
        return result;
    }
    
    public static ResultSet selectOffline() throws SQLException {

        //Show All Available Players//
        ResultSet result;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(URL, "root", "root");
        PreparedStatement st = con.prepareStatement("SELECT USERNAME from PLAYERS WHERE STATUS=? ");
        st.setBoolean(1, false);
        result = st.executeQuery();
        return result;
    }
    
    
    

    public static ResultSet selectEmail() throws SQLException {
        //Show All Available Players//
        ResultSet result;
        DriverManager.registerDriver(new ClientDriver());

        Connection con = DriverManager.getConnection(URL, "root", "root");
        PreparedStatement st = con.prepareStatement("SELECT EMAIL from PLAYERS WHERE STATUS=? ");
        st.setBoolean(1, true);
        result = st.executeQuery();
        return result;
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
            valid = "true";
        } else {
            valid = "false";
        }

        con.commit();
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
        con.commit();
        return result;
    }

    public static void updatePlayerStatus(String email, boolean status) throws SQLException {
        //when player logs-in in online mode//
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(URL, "app", "root");
        PreparedStatement st = con.prepareStatement("UPDATE players SET STATUS = ? where email=?");
        st.setBoolean(1, status);
        st.setString(2, email);
        int result=st.executeUpdate();
        System.out.println(result);
        con.commit();
        st.close();
        con.close();//        if (result > 0) {
//            PlayersDTO p;
//            p = players.get(email);
//            p.setStatus(status);
//            players.replace(ID, p);
//        }

    public static int updatePlayerStatus(String email) {

        //when player logs-in in online mode//
        int result = 0;
        try {
            DriverManager.registerDriver(new ClientDriver());
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
            PreparedStatement st = con.prepareStatement("UPDATE PLAYERS SET STATUS = ? where email=?");
            st.setBoolean(1, true);
            st.setString(2, email);
            result = st.executeUpdate();
            con.commit();

        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        }


        
    }

    public static int insertNewPlayer(PlayersDTO dto) throws SQLException {
        //needed when sign up//
        int result = 0;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(URL, "root", "root");
        PreparedStatement st = con.prepareStatement("INSERT INTO PLAYERS (userName,email,password,status,score,available) values (?,?,?,?,?,?)");
        st.setString(1, dto.getUserName());
        st.setString(2, dto.getEmail());
        st.setString(3, dto.getPassword());
        st.setBoolean(4, false);
        st.setInt(5, 0); //first time to signup there is no score
        st.setBoolean(6, dto.isAvailable());
        result = st.executeUpdate();
        con.commit();
        st.close();
        con.close();
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


    public static void selectGame() {
        try {
           games = new LinkedHashMap<>();
            ResultSet result;
            DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(URL, "root", "root");
            PreparedStatement st = con.prepareStatement("SELECT * from GAME  ");
            result = st.executeQuery();
            while (result.next()) {
                games.put(result.getInt("gameID"), objectGameDTO(result));
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public static int validatePlayer(String email,String password) throws SQLException
    {
        boolean flag=false;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(URL, "APP", "root");
        PreparedStatement st = con.prepareStatement("Select * from players where email = ? ",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        st.setString(1, email);
         //first time to signup there is no score
        ResultSet result = st.executeQuery();
        int validation=-1;
        if(result.first())
        {
            if(result.getString("password").equals(password))
            {
                validation=1;
                System.out.println("valid");
            }
            else
            {
                validation=0;
                System.out.println("wrong password");
            }
        }
        else
            System.out.println("email not found");
        con.commit();
        st.close();
        con.close();
        return validation;
    }
    public static ArrayList getOnlinePlayers(String email) throws SQLException
    {
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(URL, "app", "root");
        PreparedStatement st = con.prepareStatement("select * from players where status = true and email != ?");
        st.setString(1, email);
        ResultSet result= st.executeQuery();
        ArrayList<PlayersDTO> playersList=new ArrayList<>();
        while(result.next())
        {
            playersList.add(new PlayersDTO(result.getInt("id"),result.getString("username"),
                    result.getString("email"),result.getString("password"),
                    result.getBoolean("status"),result.getInt("score"),result.getBoolean("available")));
        }
        con.commit();
        st.close();
        con.close();
        return playersList;
    }
//    public static List<PlayersDTO> getAllPlayers()
//    {
//        
//    }

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
