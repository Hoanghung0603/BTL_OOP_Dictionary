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
    TextField inputText;
    @FXML
    Button deleteBtn;

    String newWord, newExplain;
    boolean isInDictionary = false;


    @FXML
    private void handleMouseClickDelete() {
        deleteBtn.setVisible(false);
        inputText.setText("");
        ShareInfoAddWord.setNewWord("");
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.0), addWpane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        deleteBtn.setVisible(false);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Xử lý sự kiện sau khi đã chờ đợi 1 giây
                        newWord = inputText.getText().trim();
                        if (!newWord.equals("")) {
                            deleteBtn.setVisible(true);
                            isInDictionary = (boolean) DictionaryManagement.isInDictionary(newWord);
                            //xem xet bỏ điều kiện if else này, thay bằng shareInfoaddword.setnewword(newword);
                            ShareInfoAddWord.setNewWord(newWord);
                        } else {
                            ShareInfoAddWord.setNewWord("");
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

    }
}