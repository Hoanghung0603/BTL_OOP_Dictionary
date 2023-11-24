package frontend;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.Dictionary;
import models.DictionaryCommandline;
import models.DictionaryManagement;
import models.Word;
import java.util.ArrayList;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    ObservableList<String> suggList = FXCollections.observableArrayList();
    ObservableList<String> recentSearch = FXCollections.observableArrayList();

    @FXML
    AnchorPane searchPane;
    @FXML
    Label alert, wordTarget;  //wordtarget la label hien tu tieng anh dang search
    @FXML
    Button saveBtn, editBtn, deleteBtn;

    @FXML
    ListView<String> suggResults;
    @FXML
    TextArea defTextArea;

    @FXML
    TextField inputWord;

    @FXML
    private void suggInputWord() {
        suggList.clear();
        String word = inputWord.getText().trim();
        ArrayList<Word> list = new ArrayList<>();
        list = DictionaryCommandline.dictionarySearcher(word);
        for(Word x : list) {
            suggList.add(x.getWordTarget());
        }
        if (!suggList.isEmpty()) {
            suggResults.setItems(suggList);
        }
    }

    //khi click vao mot tu trong suggResults
    @FXML
    private void handleMouseClickSuggWord(MouseEvent event) {
        String word = suggResults.getSelectionModel().getSelectedItem();
        if (word == null) return;
        wordTarget.setText(word);
        //defTextArea.setText();  // settext dinh nghia cua tu can tra
        Word tmp = DictionaryCommandline.dictionaryLookup(word);
        String text = tmp.getWordSpelling() + "\n" + tmp.getWordExplain();
        defTextArea.setText(text);
        recentSearch.add(word);

        defTextArea.setVisible(true);
        saveBtn.setVisible(false);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

      //  dictionaryManagement.insertFromFile();
        FadeTransition fadeTrans = new FadeTransition(Duration.seconds(1.0), searchPane);
        fadeTrans.setFromValue(0);
        fadeTrans.setToValue(1);
        fadeTrans.play();
        alert.setVisible(false);

        recentSearch.setAll("Text1", "Text2", "Text3", "Text4", "Text5", "Text6", "Text7");
        suggResults.setItems(recentSearch);

        inputWord.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                defTextArea.setText("");
                wordTarget.setText("Definition");
                String word = inputWord.getText().trim();
                if (!inputWord.getText().isEmpty() && DictionaryManagement.TFlookup(word)) {
                    suggInputWord();
                } else {
                    suggResults.setItems(recentSearch);
                    FadeTransition fadeAlert = new FadeTransition(Duration.seconds(2.5), alert);
                    fadeAlert.setFromValue(1.0);
                    fadeAlert.setToValue(0.0);
                    fadeAlert.play();
                }
            }
        });

        inputWord.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(inputWord.getText().trim().equals("Type your word")) inputWord.setText("");
            }
        });
    }
}
