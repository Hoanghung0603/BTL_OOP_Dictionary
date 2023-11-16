package frontend;


import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AddWController implements Initializable {
    @FXML
    AnchorPane addWpane, addWsugg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.0), addWpane);
        TranslateTransition tranTransition = new TranslateTransition(Duration.seconds(0.75), addWsugg);


        tranTransition.setByX(26);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.play();
        tranTransition.play();
    }
}
