package frontend;

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
import models.APITranslate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.controlsfx.dialog.CommandLinksDialog;

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
    private void handleMouseClickSoundSource() {
        // văn bản vào:    inputString
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
    private void handleMouseClickSoundTarget() {
        // phát âm thanh văn bản đã dịch
        // translateString

        System.out.println("phát âm thanh target");
    }

    String inputString, translateString;
    boolean vietToEng = true;

    String out = "en";
    String in = "vi";

    public void initialize(URL url, ResourceBundle resourceBundle) {
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
