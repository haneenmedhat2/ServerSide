/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverside;

/**
 *
 * @author Dell
 */
public class GamesDTO {
    private int gameID;
    private int playerID;
    private String steps;
    private String date;
    private boolean win;

    public GamesDTO(int gameID, int playerID, String steps, String date, boolean win) {
        this.gameID = gameID;
        this.playerID = playerID;
        this.steps = steps;
        this.date = date;
        this.win = win;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
    
    
}
