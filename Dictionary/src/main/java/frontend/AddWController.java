package frontend;


import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.Dictionary;
import models.DictionaryManagement;
import models.Word;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddWController implements Initializable {


    @FXML
    AnchorPane addWpane;


    @FXML
    TextArea inputDefText;
    @FXML
    TextField inputText;
    @FXML
    Button add;

    String newWord, newExplain;
    boolean isInDictionary = false;
    @FXML
    private void handleMouseClickAdd() throws IOException {
        DictionaryManagement.addWord(newWord, newExplain);
        DictionaryManagement.dictionaryExportToFile();
        inputText.setText("");
        inputDefText.setText("");
        add.setVisible(false);
        inputDefText.setEditable(false);
        ShareInfoAddWord.setNewWord("");
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.0), addWpane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        inputText.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                newWord = inputText.getText().trim();
                if(!newWord.equals("")) {
                    add.setVisible(true);
                    inputDefText.setEditable(true);

                    // nếu newword trong từ điển thì isInDictionary = true;
                    isInDictionary = DictionaryManagement.TFlookup(newWord);

                    if(isInDictionary) {
                        ShareInfoAddWord.setNewWord(newWord);
                    }
                }
                else{
                    add.setVisible(false);
                    inputDefText.setEditable(false);
                }
            }
        });

        inputDefText.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                newExplain = inputDefText.getText();
            }
        });

    }
}
