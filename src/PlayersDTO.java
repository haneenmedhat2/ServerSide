
public class PlayersDTO {
    //DTO -> Data Transfer Object//
    //Java Bean//
    //POJO-> Plain Old Java Object//
    private int id;
    private String userName;
    private String email;
    private String password;
    private boolean status;
    private int score;

    public PlayersDTO(int id, String userName, String email, String password, boolean status, int score) {
     /*   this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.status = status;
        this.score = score;
*/
      this.id = 1;
        this.userName = "somia";
        this.email = "sok@kh";
        this.password = "*******";
        this.status = true;
        this.score = 1;
    }

    PlayersDTO(String string, String string0, String string1, int aInt, String string2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    


    
    
}
