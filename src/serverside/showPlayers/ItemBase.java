package serverside.showPlayers;

import java.net.URL;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public  class ItemBase extends AnchorPane {

    protected final Rectangle rectangle;
    protected final Text playerNameId;

    public ItemBase() {

        rectangle = new Rectangle();
        playerNameId = new Text();

        setId("AnchorPane");
        setPrefHeight(53.0);
        setPrefWidth(286.0);
        getStyleClass().add("mainFxmlClass");
        getStylesheets().add("/serverside/showPlayers/item.css");

        rectangle.setArcHeight(20.0);
        rectangle.setArcWidth(20.0);
        rectangle.setFill(javafx.scene.paint.Color.valueOf("#7264B7"));
        rectangle.setHeight(52.0);
        //rectangle.setStroke(javafx.scene.paint.Color.BLACK);
        rectangle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        rectangle.setWidth(300.0);

        playerNameId.setLayoutX(21.0);
        playerNameId.setLayoutY(38.0);
        playerNameId.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        playerNameId.setStrokeWidth(0.0);
        playerNameId.setText("Player");
        playerNameId.setId("txt");
        playerNameId.setFont(new Font(25.0));

        getChildren().add(rectangle);
        getChildren().add(playerNameId);

    }
}
