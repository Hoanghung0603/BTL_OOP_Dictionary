package Controller;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    AnchorPane searchPane;
    @FXML
    Label alert;
    @FXML
    Button saveBtn, changeBtn, deleteBtn;

    @FXML
    ListView<String> suggestList;

    @FXML
    TextField sTextF;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeTrans = new FadeTransition(Duration.seconds(1.0), searchPane);
        fadeTrans.setFromValue(0);
        fadeTrans.setToValue(1);
        fadeTrans.play();
        alert.setVisible(false);

        ObservableList<String> items = FXCollections.observableArrayList("text1", "text2", "text3", "text4", "text5", "text6",
                "text7", "text 8", "text9" , "text10", "text11", "text12", "text13", "text14", "text15", "text16", "text17");
        suggestList.setItems(items);

    }
}
