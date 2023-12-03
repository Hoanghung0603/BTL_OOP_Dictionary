package frontend;

import game.javafxwordle.Toast;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Dictionary;
import models.DictionaryCommandline;
import models.DictionaryManagement;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage stage) throws IOException {
        DictionaryManagement.insertData();

        Parent root = FXMLLoader.load(getClass().getResource("/frontend/Menu-view.fxml"));
        root.setStyle("-fx-background-color: TRANSPARENT");

        stage.setTitle("Dictionary Application");
        stage.initStyle(StageStyle.TRANSPARENT);

        Toast.setStage(stage);

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}