package serverside.showPlayers;

import Database.DataAccessObject;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import serverside.ServerMainBase;

public class ShowPlayerBase extends AnchorPane {

    protected final ImageView backGround;
    protected final Rectangle rect1;
    protected final Rectangle rect2;
    protected final Text onlineTxt;
    protected final Text offlineTxt;
    protected static ListView onlineListView;
    protected static ListView offlineListView;
    protected final Button menuBtn;
    public static int onlineCount = 0;
    public static int offlineCount = 0;

    public ShowPlayerBase(Stage stage) {

        backGround = new ImageView();
        rect1 = new Rectangle();
        rect2 = new Rectangle();
        onlineTxt = new Text();
        offlineTxt = new Text();
        onlineListView = new ListView();
        offlineListView = new ListView();
        menuBtn = new Button();

        setId("AnchorPane");
        setPrefHeight(600.0);
        setPrefWidth(1000.0);
        getStyleClass().add("mainFxmlClass");
        getStylesheets().add("/serverside/showPlayers/showplayerbase.css");

        backGround.setFitHeight(600.0);
        backGround.setFitWidth(1000.0);
        backGround.setLayoutY(-1.0);
        backGround.setImage(new Image(getClass().getResource("/serverside/images/gaming-blank-banner-background_23-2150390423.jpg").toExternalForm()));

        rect1.setArcHeight(5.0);
        rect1.setArcWidth(5.0);
        rect1.setFill(javafx.scene.paint.Color.valueOf("#7264B7"));
        rect1.setHeight(393.0);
        rect1.setLayoutX(80.0);
        rect1.setLayoutY(108.0);
        rect1.setStroke(javafx.scene.paint.Color.BLACK);
        rect1.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        rect1.getStyleClass().add("boardCorner");
        rect1.setWidth(331.0);

        rect2.setArcHeight(5.0);
        rect2.setArcWidth(5.0);
        rect2.setFill(javafx.scene.paint.Color.valueOf("#7264B7"));
        rect2.setHeight(393.0);
        rect2.setLayoutX(510.0);
        rect2.setLayoutY(108.0);
        rect2.setStroke(javafx.scene.paint.Color.BLACK);
        rect2.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        rect2.getStyleClass().add("boardCorner");
        rect2.setWidth(331.0);

        onlineTxt.setLayoutX(125.0);
        onlineTxt.setLayoutY(145.0);
        onlineTxt.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        onlineTxt.setStrokeWidth(0.0);
        onlineTxt.setText("Online Players");
        onlineTxt.setFill(Color.WHITE);
        onlineTxt.setFont(new Font("System Bold", 30.0));
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.BLACK);
        onlineTxt.setEffect(dropShadow);

        offlineTxt.setLayoutX(550.0);
        offlineTxt.setLayoutY(145.0);
        offlineTxt.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        offlineTxt.setText("Offline Players");
        offlineTxt.setFill(Color.WHITE);
        offlineTxt.setFont(new Font("System Bold", 30.0));
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.BLACK);
        offlineTxt.setEffect(dropShadow);

        onlineListView.setLayoutX(100.0);
        onlineListView.setLayoutY(177.0);
        onlineListView.setPrefHeight(301.0);
        onlineListView.setPrefWidth(291.0);
        onlineListView.getStyleClass().add("mylistview");
        onlineListView.setStyle("-fx-background-color: transparent;");

        offlineListView.setLayoutX(530.0);
        offlineListView.setLayoutY(177.0);
        offlineListView.setPrefHeight(301.0);
        offlineListView.setPrefWidth(291.0);
        offlineListView.getStyleClass().add("mylistview");
        offlineListView.setStyle("-fx-background-color: transparent;");

        menuBtn.setLayoutX(350.0);
        menuBtn.setLayoutY(514.0);
        menuBtn.setPrefHeight(50.0);
        menuBtn.setPrefWidth(220.0);
        menuBtn.setMnemonicParsing(false);
        menuBtn.setText("Main Menu");
        //menuBtn.setFont(new Font("System Bold", 20.0));
        menuBtn.setId("btn");
        menuBtn.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(Event event) {
                Parent root = new ServerMainBase(stage);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }
        });

        getChildren().add(backGround);
        getChildren().add(rect1);
        getChildren().add(rect2);
        getChildren().add(onlineTxt);
        getChildren().add(offlineTxt);
        getChildren().add(onlineListView);
        getChildren().add(offlineListView);
        getChildren().add(menuBtn);

        PrepareScreen();
        onlineListView.refresh();
        offlineListView.refresh();

    }

    private void PrepareScreen() {
        String query = "";
        ResultSet Ides;
        onlineListView.getItems().clear();
        offlineListView.getItems().clear();

        try {
            Ides = DataAccessObject.selectOnline();
            while (Ides.next()) {
                setItemOnList("" + Ides.getString("USERNAME"), 1);
                onlineCount++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            Ides = DataAccessObject.selectOffline();
            while (Ides.next()) {
                setItemOnList("" + Ides.getString("USERNAME"), 0);
                offlineCount++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void setItemOnList(String name, int list) {
        ItemBase item = new ItemBase();
        item.playerNameId.setText(name);
        if (list == 0)//offline
        {
            offlineListView.getItems().add(item);
        } else if (list == 1)//online
        {
            onlineListView.getItems().add(item);
        }
        onlineListView.refresh();
        offlineListView.refresh();
    }

    public static void fromOnToOff(String name)//logout,connection lost
    {
        for (int i = 0; i < onlineListView.getItems().size(); i++) {
            String text = ((ItemBase) onlineListView.getItems().get(i)).playerNameId.getText();
            if (text.equals(name)) {
                offlineListView.getItems().add(onlineListView.getItems().get(i));
                onlineListView.getItems().remove(i);
                onlineListView.refresh();
                break;
            }
        }
    }

    public static void fromOffToOn(String name)//login
    {
        for (int i = 0; i < offlineListView.getItems().size(); i++) {
            String text = ((ItemBase) offlineListView.getItems().get(i)).playerNameId.getText();
            if (text.equals(name)) {
                onlineListView.getItems().add(offlineListView.getItems().get(i));
                offlineListView.getItems().remove(i);
                onlineListView.refresh();
                break;
            }
        }
    }
}
