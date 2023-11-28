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
    Button soundTarget, soundSource, deleteText;
    @FXML
    Label labelTextIn, labelTranslate;

    @FXML
    private void handleMouseClickSoundSource() {
        // văn bản vào:    inputString
        // phát âm thanh từ nhập vào
        System.out.println("Phát âm thanh source");
    }

    @FXML
    private void handleMouseClickDelTextBtn() {
        toBeTranslatedText.setText("");
        translateText.setText("");
    }

    @FXML
    private void handleMouseClickSoundTarget() {
        // phát âm thanh văn bản đã dịch
        // translateString

        System.out.println("phát âm thanh target");
    }

    String inputString, translateString;
    boolean vietToEng = true;
    String target;
    String source;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //...
                vietToEng = !vietToEng;
                if(vietToEng) {
                    labelTextIn.setText("Việt");
                    labelTranslate.setText("Anh");
                    target = "en";
                    source = "vi";
                }

                else {
                    labelTextIn.setText("Anh");
                    labelTranslate.setText("Việt");
                    target = "vi";
                    source = "en";
                }
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
                try {
                    translateString = API.translate(inputString, target, source);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(translateString);
                    translateText.setText(translateString);
            }
        });
    }
}
