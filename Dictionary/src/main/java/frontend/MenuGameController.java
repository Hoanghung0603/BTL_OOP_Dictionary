package frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuGameController implements Initializable {
    @FXML
    Button wordleOption, mulChoiceOption, gameWordOption;
    @FXML
    AnchorPane gamePane;

    @FXML
    private void setNode(Node node) {
        gamePane.getChildren().clear();
        gamePane.getChildren().add(node);
    }

    @FXML
    public void showView(String path) {
        try {
            AnchorPane temp = FXMLLoader.load(getClass().getResource(path));
            AnchorPane.setLeftAnchor(temp, 0.0);
            AnchorPane.setTopAnchor(temp, 0.0);
            setNode(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wordleOption.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showView("main-view.fxml");
                Button quitBtn = new Button();
                quitBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        gamePane.getChildren().clear();
                    }
                });
                AnchorPane temp = (AnchorPane) gamePane.getChildren().getFirst();
                Pane pane = (Pane) temp.getChildren().getLast();
                pane.getChildren().add(quitBtn);

                gamePane.getChildren().getFirst().setTranslateX(-70);
                gamePane.getChildren().getFirst().setTranslateY(-12);
            }
        });

        mulChoiceOption.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showView("trac-nghiem.fxml");
                Button quitBtn = new Button();
                quitBtn.setTranslateX(399);
                quitBtn.setTranslateY(73);
                quitBtn.setPrefSize(44,46);
                quitBtn.setStyle("-fx-background-color: TRANSPARENT;");
                Image image = new Image(getClass().getResource("/Utils/icons/home-button.png").toExternalForm());
                ImageView homes = new ImageView(image);
                homes.setFitHeight(39);
                homes.setFitWidth(37);
                quitBtn.setGraphic(homes);
                quitBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        gamePane.getChildren().clear();
                    }
                });
                AnchorPane temp = (AnchorPane) gamePane.getChildren().getFirst();
                Pane pane = (Pane) temp.getChildren().getLast();
                pane.getChildren().add(quitBtn);

                gamePane.getChildren().getFirst().setTranslateX(0);
                gamePane.getChildren().getFirst().setTranslateY(0);
            }
        });

        gameWordOption.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showView("word-game.fxml");
                Button quitBtn = new Button();
                quitBtn.setTranslateX(175);
                quitBtn.setTranslateY(447);
                quitBtn.setPrefSize(44,46);
                quitBtn.setStyle("-fx-background-color: TRANSPARENT;");
                Image image = new Image(getClass().getResource("/Utils/icons/home-button.png").toExternalForm());
                ImageView homes = new ImageView(image);
                homes.setFitHeight(39);
                homes.setFitWidth(37);
                quitBtn.setGraphic(homes);
                quitBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        gamePane.getChildren().clear();
                    }
                });
                AnchorPane temp = (AnchorPane) gamePane.getChildren().getFirst();
                Pane pane = (Pane) temp.getChildren().getLast();
                pane.getChildren().add(quitBtn);

                gamePane.getChildren().getFirst().setTranslateX(0);
                gamePane.getChildren().getFirst().setTranslateY(0);
            }
        });


    }
}
