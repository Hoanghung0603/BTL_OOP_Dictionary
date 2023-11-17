package frontend;

import javafx.application.Application;
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

public class Start extends Application {
    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage stage) throws IOException {
        Dictionary dictionary = new Dictionary();
        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        DictionaryManagement.insertFromFile();
        DictionaryManagement.insertFromFileAdded();


        Parent root = FXMLLoader.load(getClass().getResource("/frontend/Menu-view.fxml"));
        root.setStyle("-fx-background-color: TRANSPARENT");

        stage.setTitle("Dictionary Application");
        stage.initStyle(StageStyle.TRANSPARENT);


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