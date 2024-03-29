package serverside;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import serverside.showPlayers.ShowPlayerBase;

public class ServerMainBase extends AnchorPane {

    protected final ImageView backGround;
    protected final Text titleLabel;
    protected final Button startBtn;
    protected final Button showBtn;
    protected final ImageView imageView;
    protected final ImageView imageView0;

    public ServerMainBase(Stage stage) {

        getStylesheets().add("/serverside/serverStyleSheet/Servermain.css");

        backGround = new ImageView();
        titleLabel = new Text();
        startBtn = new Button();
        showBtn = new Button();
        imageView = new ImageView();
        imageView0 = new ImageView();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(703.0);
        setPrefWidth(1016.0);

        backGround.setFitHeight(703.0);
        backGround.setFitWidth(1025.0);
        backGround.setImage(new Image(getClass().getResource("/serverside/images/image.gif").toExternalForm()));


        titleLabel.setLayoutX(590.0);
        titleLabel.setLayoutY(190.0);
        titleLabel.setText("Tic.Tac.Toe.");
//        titleLabel.setFill(javafx.scene.paint.Color.valueOf("#B575D4"));
//        titleLabel.setStroke(Color.ANTIQUEWHITE);  
//        titleLabel.setStrokeWidth(1); 
//        titleLabel.setFont(new Font("System Bold", 64.0));
        titleLabel.setFill(javafx.scene.paint.Color.valueOf("#BE8FD5"));
        titleLabel.setStroke(Color.web("#C1ADCB"));
        titleLabel.setStrokeWidth(2);
        titleLabel.setFont(new Font("System Bold", 64.0));
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        titleLabel.setEffect(dropShadow);

        startBtn.setAlignment(javafx.geometry.Pos.BASELINE_CENTER);
        startBtn.setLayoutX(623.0);
        startBtn.setLayoutY(300.0);
        startBtn.setMnemonicParsing(false);
        startBtn.setPrefHeight(62.0);
        startBtn.setPrefWidth(278.0);
        startBtn.setText("Start");

        startBtn.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(Event event) {
                if (startBtn.getText().equalsIgnoreCase("Start")) {
                    startBtn.setText("Stop");
                    new Server();
                } else {

                    startBtn.setText("Start");

                }

            }
        });

        startBtn.setFont(new Font("System Bold", 24.0));

        showBtn.setAlignment(javafx.geometry.Pos.BASELINE_CENTER);
        showBtn.setLayoutX(623.0);
        showBtn.setLayoutY(400.0);
        showBtn.setMnemonicParsing(false);
        showBtn.setPrefHeight(62.0);
        showBtn.setPrefWidth(278.0);
        showBtn.setText("Show Players");
        showBtn.setFont(new Font("System Bold", 24.0));
        showBtn.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(Event event) {
                Parent root = new ShowPlayerBase(stage);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }
        });

        imageView.setFitHeight(33.0);
        imageView.setFitWidth(36.0);
        imageView.setLayoutX(639.0);
        imageView.setLayoutY(310.0);
        imageView.setImage(new Image(getClass().getResource("/serverside/images/server.png").toExternalForm()));


        imageView0.setFitHeight(33.0);
        imageView0.setFitWidth(36.0);
        imageView0.setLayoutX(639.0);
        imageView0.setLayoutY(415.0);
        imageView0.setImage(new Image(getClass().getResource("/serverside/images/icons8-head-to-head-48.png").toExternalForm()));


        getChildren().add(backGround);
        getChildren().add(titleLabel);
        getChildren().add(startBtn);
        getChildren().add(showBtn);
        getChildren().add(imageView);
        getChildren().add(imageView0);

    }
}
