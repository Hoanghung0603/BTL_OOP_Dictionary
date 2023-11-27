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
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


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
    Button saveBtn, soundBtn, deleteBtn, showFavorWords, deleteFavWord;
    @FXML
    ButtonBar buttonBar;
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
        word = word.toLowerCase();
        System.out.println(word);
        ArrayList<Word> list = new ArrayList<>();
        list = DictionaryCommandline.dictionarySearcher(word);
        for(Word x : list) {
            suggList.add(x.getWordTarget());
        }
        if (!suggList.isEmpty()) {
            suggResults.setItems(suggList);
        }
//        else {
//            FadeTransition fadeAlert = new FadeTransition(Duration.seconds(2.5), alert);
//            fadeAlert.setFromValue(1.0);
//            fadeAlert.setToValue(0.0);
//            fadeAlert.play();
//        }
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
        if(Dictionary.recentWord.size() == 10) Dictionary.recentWord.remove(0);
        if(Dictionary.recentWord.contains(word)) Dictionary.recentWord.remove(word);
        Dictionary.recentWord.add(word);
        recentSearch.setAll(Dictionary.recentWord.reversed());
        suggResults.getSelectionModel().selectFirst();

        defTextArea.setVisible(true);
    }

    @FXML
    private void clickSoundBtn() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
            voice.speak("Hello world, i can speak english");
        } else throw new IllegalStateException("Cannot find voice: kevin16");
    }

    @FXML
    private void handleClickSaveBtn() {
        //thêm từ vừa tra vào danh sách từ đã lưu
    }

    @FXML
    private void handleClickShowFavorWords() {
        // set suggList thành danh sách các từ đã lưu
//        suggList.set();
//        suggResults.setItems(suggList);
        deleteBtn.setVisible(false);
    }

    @FXML
    private void handleClickDeleteBtn() {
        defTextArea.setText("");
        inputWord.setText("");
        suggResults.setItems(recentSearch);
        deleteBtn.setVisible(false);
        buttonBar.setVisible(false);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
      //  dictionaryManagement.insertFromFile();
        FadeTransition fadeTrans = new FadeTransition(Duration.seconds(1.0), searchPane);
        fadeTrans.setFromValue(0);
        fadeTrans.setToValue(1);
        fadeTrans.play();
        alert.setVisible(false);
        recentSearch.setAll(Dictionary.recentWord);
        suggResults.setItems(recentSearch);

        inputWord.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                defTextArea.setText("");
                wordTarget.setText("Definition");
                String word = inputWord.getText().trim();
                deleteBtn.setVisible(true);
                if (!inputWord.getText().isEmpty() && DictionaryManagement.TFlookup(word)) {
                    suggInputWord();
                    buttonBar.setVisible(true);
                } else {
                    buttonBar.setVisible(false);
                    if (inputWord.getText().isEmpty()) deleteBtn.setVisible(false);
                    else {
                        suggResults.setItems(recentSearch);
                        alert.setVisible(true);
                        FadeTransition fadeAlert = new FadeTransition(Duration.seconds(2.5), alert);
                        fadeAlert.setFromValue(1.0);
                        fadeAlert.setToValue(0.0);
                        fadeAlert.play();
                    }
                }
            }
        });
        inputWord.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(inputWord.getText().trim().equals("Type your word")) {
                    inputWord.setText("");
                }
            }
        });
    }
}
