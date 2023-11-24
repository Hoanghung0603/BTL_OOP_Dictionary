package frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import models.API;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TranslateController implements Initializable {
    @FXML
    TextArea toBeTranslatedText, translateText;
    @FXML
    Button change, translateBtn;
    @FXML
    Label labelTextIn, labelTranslate;

    String inputString, translateString;
    boolean vietToEng = true;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //...
                vietToEng = !vietToEng;
                labelTextIn.setText(vietToEng ? "Việt" : "Anh");
                labelTranslate.setText(vietToEng ? "Anh" : "Việt");

            }
        });
        toBeTranslatedText.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                inputString = toBeTranslatedText.getText();
                if (inputString.isEmpty()) translateBtn.setDisable(true);
                else translateBtn.setDisable(false);
            }
        });

        translateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    if(vietToEng) {
                        try {
                            translateString = API.VtranslatetoE(inputString);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        try {
                            translateString = API.EtranslatetoV(inputString);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(translateString);
                    translateText.setText(translateString);
            }
        });
    }
}
