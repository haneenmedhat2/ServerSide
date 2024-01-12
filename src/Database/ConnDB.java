package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.derby.jdbc.ClientDriver;

public class ConnDB {

    public DataAccessObject dao;
  
    public ConnDB() {
        dao = new DataAccessObject();

    }

    public static int Signup(PlayersDTO player) throws SQLException { //signup
        int result = 0;
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
        PreparedStatement st = con.prepareStatement("INSERT INTO PLAYERS (ID,USERNAME,EMAIL,PASSWORD,STATUS,SCORE) value (?,?,?,?,?,?)");
        st.setInt(1, player.getId());
        st.setString(2, player.getUserName());
        st.setString(3, player.getEmail());
        st.setString(4, player.getPassword());
        st.setBoolean(5, player.isStatus());

        result = st.executeUpdate();
        con.commit();
        st.close();
        con.close();
        return result;
    }

    public static String validateSignup(PlayersDTO player) throws SQLException {
        String isValid = null;

        DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
        PreparedStatement prepStmt = con.prepareStatement("select * from players where USERNAME = ?");
        PreparedStatement prepStmt2 = con.prepareStatement("select * from players where EMAIL = ?");
        prepStmt.setString(1, player.getUserName());
        prepStmt2.setString(1, player.getEmail());
        ResultSet rs = prepStmt.executeQuery();
        ResultSet res = prepStmt2.executeQuery();
        if (rs.next() || res.next()) {
            isValid = "not valid username or email";
        } else {
            isValid = "valid";
        }

        return isValid;
    }

    public static int Login(PlayersDTO player) throws SQLException {
        int result;

        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Toe", "root", "root");
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

    public String logInCheck(String comingData) {

        String[] allData = comingData.split(",");
        boolean isLoginBeafore = false;
        String res = "login,";
        try {
            ResultSet check = dao.selectPlayer("select STATUS from PLAYERS where Email='" + allData[1] + "' and password='" + allData[2] + "'");
            try {
                if (check.next()) {
                    if (check.getBoolean("STATUS") == true) {
                        isLoginBeafore = true;
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            if (!isLoginBeafore) {
                ResultSet data = dao.selectPlayer("select * from PLAYERS where Email='" + allData[1] + "' and password='" + allData[2] + "'");
                try {
                    while (data.next()) {
                        res += data.getInt("ID") + "," + data.getString("USERNAME") + ","
                                + data.getString("EMAIL") + "," + data.getString("PASSWORD") + "," + data.getBoolean("STATUS") + ","
                                + data.getInt("SCORE");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                res = "lBefo,";
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }
    
    
      public boolean SignUp(String comingData)//data comes as one string with , splitter
   {    boolean result=true;
        
       try //data comes as one string with , splitter
       {
           
           String[]allData=comingData.split(",");
           //check user if exists
           ResultSet check_exists=dao.selectPlayer("select * from PLAYERS where EMAIL='"+allData[2]+"'");
           try {
               if(check_exists.next())
               {
                   result=false;
               }
               
               if(result){
                   //get raws count
                   check_exists=dao.selectPlayer("select count(*) from PLAYERS");
                   check_exists.next();
                   int id=check_exists.getInt(1)+1;
                   String name=allData[1];
                   String email=allData[2];
                   String password=allData[3];
                   PlayersDTO dto=new PlayersDTO(id,name,email,password);   
                   dao.insertNewPlayer(dto);
                   //PlayersScreenBase.setItemOnList(allData[1], 0);
               }
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
          
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
        return result;
   }
      
       public void setStatus(boolean status,int id){
       String stat=status?"true":"false";
   }

}
