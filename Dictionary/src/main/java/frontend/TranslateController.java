package frontend;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import service.APITranslate;

import service.SpeechAPI;
import service.T2SThread;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TranslateController implements Initializable {
    @FXML
    TextArea toBeTranslatedText, translateText;
    @FXML
    Button change, translateBtn;
    @FXML
    Button soundTarget, soundSource, deleteText, copyTextBtn;
    @FXML
    Label labelTextIn, labelTranslate;
    @FXML
    AnchorPane translatePane;
    @FXML
    private void handleMouseClickSoundSource() throws Exception {
        // văn bản vào:    inputString
        T2SThread t2sThread = new T2SThread();
        t2sThread.getSpeechFromTextThread(inputString, in);
        // phát âm thanh từ nhập vào
        System.out.println("Phát âm thanh source");
    }

    @FXML
    private void handleMouseClickCopyBtn() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(translateText.getText());
        clipboard.setContent(content);
    }

    @FXML
    private void handleMouseClickDelTextBtn() {
        toBeTranslatedText.setText("");
        translateText.setText("");
    }

    @FXML
    private void handleMouseClickSoundTarget() throws Exception {
        // phát âm thanh văn bản đã dịch
        // translateString
        T2SThread t2sThread = new T2SThread();
        t2sThread.getSpeechFromTextThread(translateString, out);
        System.out.println("phát âm thanh target");
    }

    String inputString, translateString;
    boolean vietToEng = true;

    String out = "en";
    String in = "vi";

    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeTrans = new FadeTransition(Duration.seconds(1.0), translatePane);
        fadeTrans.setFromValue(0);
        fadeTrans.setToValue(1);
        fadeTrans.play();
        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //...
                vietToEng = !vietToEng;
                if(vietToEng) {
                    labelTextIn.setText("Việt");
                    labelTranslate.setText("Anh");
                    out = "en";
                    in = "vi";
                }

                else {
                    labelTextIn.setText("Anh");
                    labelTranslate.setText("Việt");
                    out = "vi";
                    in = "en";
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
                APITranslate apiTranslate = new APITranslate();
                try {
                    translateString = apiTranslate.translate(inputString, in, out);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(translateString);
                translateText.setText(translateString);
            }
        });
    }
    public static void main (String[] args) throws IOException {
        APITranslate apiTranslate = new APITranslate();
        System.out.println(APITranslate.translate("xin chào" + '\n' + "hài", "vi", "en"));
    }
}
