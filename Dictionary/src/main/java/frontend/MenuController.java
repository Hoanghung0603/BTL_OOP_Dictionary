package frontend;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class MenuController implements Initializable{

    @FXML
    private AnchorPane mainPain, supportPane;
    @FXML
    private Pane efPane, efPanefather;

    @FXML
    private Button searchBtn, AddBtn, TranslateBtn, GameBtn, QuitBtn;

    @FXML
    private void setNode(Node node) {
        supportPane.getChildren().clear();
        mainPain.getChildren().clear();
        mainPain.getChildren().add(node);
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

    private void setAllNodevisible(AnchorPane pane, boolean visible) {
        for (Node node : pane.getChildren()) {
            node.setVisible(visible);
        }
    }

    private boolean changeWidth = false;
    private boolean isInSearchFunction = true;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ScaleTransition scaleTrans= new ScaleTransition(Duration.seconds(1.0), efPane);
        TranslateTransition tranTrans = new TranslateTransition(Duration.seconds(1.0), efPanefather);

        ParallelTransition parallelT1 = new ParallelTransition(scaleTrans, tranTrans);

        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!isInSearchFunction) {
                    showView("Search-view.fxml");
                    isInSearchFunction = true;
                }
                if (changeWidth) {
                    isInSearchFunction = true;
                    showView("Search-view.fxml");
                    AddBtn.setDisable(true);
                    TranslateBtn.setDisable(true);
                    GameBtn.setDisable(true);

                    scaleTrans.setToX(1);
                    tranTrans.setByX(1.0 * efPane.getPrefWidth() * 0.25);
                    parallelT1.play();

                    changeWidth = false;

                    parallelT1.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            AddBtn.setDisable(false);
                            TranslateBtn.setDisable(false);
                            GameBtn.setDisable(false);
                        }
                    });

                }
            }
        });

        AddBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!changeWidth) {
                    isInSearchFunction = false;
                    showView("AddW-view.fxml");

                    Node node = null;
                    try {
                        AnchorPane temp = FXMLLoader.load(getClass().getResource("/frontend/AddWsugg-view.fxml"));
                        node = temp;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    supportPane.getChildren().add(node);

                    searchBtn.setDisable(true);
                    TranslateBtn.setDisable(true);
                    GameBtn.setDisable(true);

                    scaleTrans.setToX(0.5);
                    tranTrans.setByX(-1.0 * efPane.getPrefWidth() * 0.25);
                    parallelT1.play();

                    changeWidth = true;

                    parallelT1.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            searchBtn.setDisable(false);
                            TranslateBtn.setDisable(false);
                            GameBtn.setDisable(false);
                        }
                    });
                }
            }
        });

        TranslateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (isInSearchFunction) {
                    isInSearchFunction = false;
                    showView("translate-view.fxml");
                }
                if (changeWidth) {
                    isInSearchFunction = false;
                    AddBtn.setDisable(true);
                    searchBtn.setDisable(true);
                    GameBtn.setDisable(true);

                    scaleTrans.setToX(1);
                    tranTrans.setByX(1.0 * efPane.getPrefWidth() * 0.25);

                    parallelT1.play();
                    changeWidth = false;

                    parallelT1.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            AddBtn.setDisable(false);
                            searchBtn.setDisable(false);
                            GameBtn.setDisable(false);
                            setAllNodevisible(mainPain, true);
                        }
                    });
                    showView("translate-view.fxml");
                    setAllNodevisible(mainPain, false);
                }
            }
        });

        showView("Search-view.fxml");

        GameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        QuitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
    }
}