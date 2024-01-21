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
    final static String URL="jdbc:derby://localhost:1527/Tic";
    
    //Players Queries//
    public static PlayersDTO ObjectPlayerDTO(ResultSet result) {
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new PlayersDTO();
    }

    public static ArrayList<PlayersDTO> selectPlayer() throws SQLException {
        //Show All Available Players//
        players = new LinkedHashMap<>();
        playerList = new ArrayList<>();
        ResultSet result;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(URL, "app", "root");
        PreparedStatement st = con.prepareStatement("SELECT * from PLAYERS ORDER BY SCORE DESC ");
        result = st.executeQuery();
        while (result.next()) {
            players.put(result.getInt("id"), ObjectPlayerDTO(result));
            playerList.add(ObjectPlayerDTO(result));
        }

        return playerList;
    }

    public static int updatePlayerScore( String email) throws SQLException {
        //when player wins in online mode//
        int result = 0;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(URL, "app", "root");
        int totalScore =DataAccessObject.playerScore(email);
        totalScore++;
        PreparedStatement st = con.prepareStatement("UPDATE PLAYERS SET score = ? where EMAIL = ? ");
        st.setInt(1, totalScore);
        st.setString(2, email);
        result = st.executeUpdate();
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

        
    }

    public static int insertNewPlayer(PlayersDTO dto) throws SQLException {
        //needed when sign up//
        int result = 0;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(URL, "app", "root");
        PreparedStatement st = con.prepareStatement("INSERT INTO PLAYERS (userName,email,password,status,score,available) values (?,?,?,?,?,?)");
        st.setString(1, dto.getUserName());
        st.setString(2, dto.getEmail());
        st.setString(3, dto.getPassword());
        st.setBoolean(4, dto.isStatus());
        st.setInt(5, 0); //first time to signup there is no score
        st.setBoolean(6, dto.isAvailable());
        result = st.executeUpdate();
        con.commit();
        st.close();
        con.close();
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
            Connection con = DriverManager.getConnection(URL, "app", "root");
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
        Connection con = DriverManager.getConnection(URL, "app", "root");
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
        Connection con = DriverManager.getConnection(URL, "app", "root");
        PreparedStatement st = con.prepareStatement("INSERT INTO GAME (gameID,playerID,steps,date,win) value (?,?,?,?,?)");
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
        Connection con = DriverManager.getConnection(URL, "app", "root");
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
    
    
    public static ResultSet selectOnline() throws SQLException{
          ResultSet result;
            DriverManager.registerDriver(new ClientDriver());
            Connection con = DriverManager.getConnection(URL, "app", "root");
            PreparedStatement st = con.prepareStatement("SELECT * from PLAYERS WHERE STATUS=?");
           st.setBoolean(1, true);
        result = st.executeQuery();
         return result;
    }
    
      public static ResultSet selectOffline() throws SQLException{
          ResultSet result;
            DriverManager.registerDriver(new ClientDriver());
            Connection con = DriverManager.getConnection(URL, "app", "root");
            PreparedStatement st = con.prepareStatement("SELECT * from PLAYERS WHERE STATUS=?");
           st.setBoolean(1, false);
        result = st.executeQuery();
         return result;
    }
      
      
      
      public static int playerScore(String email) throws SQLException{
          ResultSet result;
           int score = 0; 
            DriverManager.registerDriver(new ClientDriver());
            Connection con = DriverManager.getConnection(URL, "app", "root");
            PreparedStatement st = con.prepareStatement("SELECT SCORE from PLAYERS WHERE EMAIL=? ");
           st.setString(1, email);
        result = st.executeQuery();
        if(result.next()){
              score = result.getInt("SCORE");
        }
         return score;
    }

//    public static List<PlayersDTO> getAllPlayers()
//    {
//        
//    }
    
     public static int retriveID(String email) throws SQLException{
           DriverManager.registerDriver(new ClientDriver());
            Connection con = DriverManager.getConnection(URL, "APP", "root");
            PreparedStatement st = con.prepareStatement("Select ID from players where email = ? ",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            st.setString(1, email);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                int id = result.getInt("ID");
                return id;
            } 
            return 0;
         }
     
     public static int insertRecord(int playerId,  String steps) throws SQLException {
                int result = 0;
                DriverManager.registerDriver(new ClientDriver());
                Connection con = DriverManager.getConnection(URL, "APP", "root");
                PreparedStatement st = con.prepareStatement("INSERT INTO GAME (USERID, STEPS, DATE) VALUES (?, ?,?)");
                st.setInt(1, playerId);
                st.setString(2, steps);
                st.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                result = st.executeUpdate();
                con.commit();
                st.close();
                con.close();
                return result;
    }
     
    /* 
    public static boolean isPlayerLoggedIn(String email) throws SQLException {
        boolean isLoggedIn = false;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet result = null;

        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
            prepStmt = con.prepareStatement("SELECT ID FROM players WHERE email = ?");
            prepStmt.setString(1, email);
            result = prepStmt.executeQuery();

            if (result.next()) {
                isLoggedIn = true;
            }
        } finally {
            if (result != null) {
                result.close();
            }
            if (prepStmt != null) {
                prepStmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return isLoggedIn;
    }
       */
     
     public static boolean isPlayerLoggedIn(String email) throws SQLException {
        boolean result = false;

        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(URL, "APP", "root");
        PreparedStatement st = con.prepareStatement("SELECT * FROM PLAYERS WHERE EMAIL=?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        st.setString(1, email);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
           result = rs.getBoolean("STATUS");
        } 
         System.out.println("sssssss"+result);
               con.commit();
                st.close();
                con.close();
                return result;
    }

    
}
    

     

