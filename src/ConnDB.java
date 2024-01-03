

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.util.Vector;
import static javax.swing.UIManager.getString;
import org.apache.derby.jdbc.ClientDriver;


/**
 *
 * @author hadia
 */

public class ConnDB {
    
    public static int Signup (PlayersDTO player) throws SQLException{ //signup
        int result=0;
    DriverManager.registerDriver(new ClientDriver());
    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe","root","root");
     PreparedStatement st= con.prepareStatement("INSERT INTO PLAYERS (ID,USERNAME,EMAIL,PASSWORD,STATUS,SCORE) value (?,?,?,?,?,?)");
           st.setInt(1, player.getId());
           st.setString(2, player.getUserName());
           st.setString(3,player.getEmail());
           st.setString(4,player.getPassword());
           st.setBoolean(5, player.isStatus());
     
     result =st.executeUpdate();
     con.commit();
     st.close();
     con.close();
    return result;
    }


    
   public static String validateSignup(PlayersDTO player) throws SQLException{
       String isValid = null;
            
    DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe","root","root");
    PreparedStatement prepStmt = con.prepareStatement("select * from players where USERNAME = ?");
    PreparedStatement prepStmt2 = con.prepareStatement("select * from players where EMAIL = ?");
            prepStmt.setString(1, player.getUserName());
            prepStmt2.setString(1, player.getEmail());
            ResultSet rs = prepStmt.executeQuery();
            ResultSet res = prepStmt2.executeQuery();
            if(rs.next()||res.next()){
                isValid="not valid username or email";
            }
            else{
            isValid="valid";
            }
        
        return isValid;
            }
   
         public static int Login (PlayersDTO player) throws SQLException{
           int result; 
        
            try {
    DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe","root","root");
    PreparedStatement prepStmt = con.prepareStatement("SELECT USERNAME,PASSWORD FROM PLAYERS WHERE USERNAME=? AND PASSWORD=?");
            prepStmt.setString(1, player.getUserName());
            prepStmt.setString(2, player.getPassword());
            ResultSet rs = prepStmt.executeQuery();
            if (rs.next()) {
                return 1;
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return 0;  
        }
        }

   



