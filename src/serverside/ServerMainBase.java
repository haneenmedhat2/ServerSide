package serverside;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public  class ServerMainBase extends AnchorPane {

    protected final ImageView backGround;
    protected final Label titleLabel;
    protected final Button startBtn;
    protected final Button stopBtn;
    protected final Button showBtn;
    protected final ImageView imageView;
    protected final ImageView imageView0;
    protected final ImageView imageView1;

    public ServerMainBase() {

        backGround = new ImageView();
        titleLabel = new Label();
        startBtn = new Button();
        stopBtn = new Button();
        showBtn = new Button();
        imageView = new ImageView();
        imageView0 = new ImageView();
        imageView1 = new ImageView();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(703.0);
        setPrefWidth(1016.0);
        
        getStylesheets().add("serverside/serverStyleSheet/Servermain.css");

        backGround.setFitHeight(703.0);
        backGround.setFitWidth(1025.0);
        backGround.setLayoutX(-2.0);
        backGround.setImage(new Image(getClass().getResource("images/gaming-blank-banner-background_23-2150390423.jpg").toExternalForm()));

        titleLabel.setLayoutX(603.0);
        titleLabel.setLayoutY(87.0);
        titleLabel.setText("tic.tac.toe.");
        titleLabel.setTextFill(javafx.scene.paint.Color.valueOf("#c5a0d7"));
        titleLabel.setFont(new Font("Arial Bold", 64.0));

        startBtn.setAlignment(javafx.geometry.Pos.BASELINE_CENTER);
        startBtn.setLayoutX(623.0);
        startBtn.setLayoutY(234.0);
        startBtn.setMnemonicParsing(false);
        startBtn.setPrefHeight(62.0);
        startBtn.setPrefWidth(278.0);
        startBtn.setText("Start");
        startBtn.setFont(new Font("System Bold", 24.0));

        stopBtn.setAlignment(javafx.geometry.Pos.BASELINE_CENTER);
        stopBtn.setLayoutX(623.0);
        stopBtn.setLayoutY(339.0);
        stopBtn.setMnemonicParsing(false);
        stopBtn.setPrefHeight(62.0);
        stopBtn.setPrefWidth(278.0);
        stopBtn.setText("Stop");
        stopBtn.setFont(new Font("System Bold", 24.0));
        
        showBtn.setAlignment(javafx.geometry.Pos.BASELINE_CENTER);
        showBtn.setLayoutX(623.0);
        showBtn.setLayoutY(434.0);
        showBtn.setMnemonicParsing(false);
        showBtn.setPrefHeight(62.0);
        showBtn.setPrefWidth(278.0);
        showBtn.setText("Show Players");
        showBtn.setFont(new Font("System Bold", 24.0));

        imageView.setFitHeight(33.0);
        imageView.setFitWidth(36.0);
        imageView.setLayoutX(639.0);
        imageView.setLayoutY(249.0);
        imageView.setImage(new Image(getClass().getResource("images/server.png").toExternalForm()));

        imageView0.setFitHeight(40.0);
        imageView0.setFitWidth(36.0);
        imageView0.setLayoutX(639.0);
        imageView0.setLayoutY(350.0);
        imageView0.setImage(new Image(getClass().getResource("images/icons8-server-shutdown-80.png").toExternalForm()));

        imageView1.setFitHeight(33.0);
        imageView1.setFitWidth(36.0);
        imageView1.setLayoutX(639.0);
        imageView1.setLayoutY(449.0);
        imageView1.setImage(new Image(getClass().getResource("images/icons8-head-to-head-48.png").toExternalForm()));

        getChildren().add(backGround);
        getChildren().add(titleLabel);
        getChildren().add(startBtn);
        getChildren().add(stopBtn);
        getChildren().add(showBtn);
        getChildren().add(imageView);
        getChildren().add(imageView0);
        getChildren().add(imageView1);

    }
}
