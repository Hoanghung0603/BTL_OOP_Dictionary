package frontend;


import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.Dictionary;
import models.DictionaryManagement;
import models.Word;

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
    Button addConfirmBtn;

    String newWord, newExplain;
    boolean isInDictionary = false;
    @FXML
    private void handleMouseClickAdd() {
        Word word = DictionaryManagement.dictionaryLookup(newWord);
        Word word2 = DictionaryManagement.addLookup(newWord);
        if(DictionaryManagement.TFlookup(newWord) == true) {
            String explain = word2.getWordExplain() + '\n' + "- " + newExplain;
            Word newWord2 = new Word(newWord, explain);
            Dictionary.listAdd.remove(word2);
            Dictionary.listAdd.add(newWord2);
        }
        else {
            Word newaddWord = new Word(newWord, "- " + newExplain);
            Dictionary.listAdd.add(newaddWord);
        }
        // DictionaryManagement.dictionaryExportToFile();
        inputText.setText("");
        inputDefText.setText("");
        addConfirmBtn.setVisible(false);
        inputDefText.setEditable(false);
        ShareInfoAddWord.setNewWord("");
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.0), addWpane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Xử lý sự kiện sau khi đã chờ đợi 1 giây
                        newWord = inputText.getText().trim();
                        if (!newWord.equals("")) {
                            System.out.println("New Word: " + newWord);
                            inputDefText.setEditable(true);
                            addConfirmBtn.setVisible(true);
                            isInDictionary = (boolean) DictionaryManagement.TFlookup(newWord);

                            System.out.println(newWord);
                            System.out.println(isInDictionary + " is");

                            if (isInDictionary) {
                                ShareInfoAddWord.setNewWord(newWord);
                            }
                            else {
                                ShareInfoAddWord.setNewWord("");
                            }
                        } else {
                            ShareInfoAddWord.setNewWord("");
                            addConfirmBtn.setVisible(false);
                            inputDefText.setEditable(false);
                        }
                    }
                }));

        inputText.setOnKeyTyped(event -> {
            timeline.stop();
            timeline.playFromStart();
        });

        inputText.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(inputText.getText().equals("Type your word")) {
                    inputText.setText("");
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