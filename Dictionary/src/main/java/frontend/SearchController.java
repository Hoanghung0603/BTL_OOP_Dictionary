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
    //public Dictionary dictionary = new Dictionary();
   // public DictionaryManagement dictionaryManagement = new DictionaryManagement();
    ObservableList<String> suggList = FXCollections.observableArrayList();

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

        if (suggList.isEmpty()) {
            FadeTransition fadeAlert = new FadeTransition(Duration.seconds(2.5), alert);
            fadeAlert.setFromValue(1.0);
            fadeAlert.setToValue(0.0);
            fadeAlert.play();
        } else {
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

        defTextArea.setVisible(true);
        defTextArea.setEditable(false);
        saveBtn.setVisible(false);
    }

    @FXML
    private void clickEditBtn() {
        defTextArea.setEditable(true);
        saveBtn.setVisible(true);
        //alert?
    }

    @FXML
    private void clickSaveBtn() {

    }

    @FXML
    private void clickDeleteBtn() {

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

      //  dictionaryManagement.insertFromFile();

        FadeTransition fadeTrans = new FadeTransition(Duration.seconds(1.0), searchPane);
        fadeTrans.setFromValue(0);
        fadeTrans.setToValue(1);
        fadeTrans.play();
        alert.setVisible(false);

        inputWord.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!inputWord.getText().isEmpty()) {
                    suggInputWord();
                } else {

                }
            }
        });


    }
}
