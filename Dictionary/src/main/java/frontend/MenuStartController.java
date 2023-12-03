package GameApplication;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuStartController implements Initializable {
    private Label gameMenu = new Label("Game Menu");

    private Label pleaseSelectYourGame = new Label("Chọn game mà bạn muốn chơ");

    private Label game1 = new Label("Game 2 người chơi từ vựng");

    private Button soloGame = new Button("Challenge game!");

    private Label game2 = new Label("Game trắc nghiệm tiếng Anh");

    private Button MPGame = new Button("Game trắc nghiệm tiếng Anh");

    private Label game3 = new Label("Game flash card học từ vựng");

    private Button flashCardGame = new Button("Flash Card");

    private AnchorPane container;


    private void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource(path));
            setNode(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
