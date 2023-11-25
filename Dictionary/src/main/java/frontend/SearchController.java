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

import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


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
    Button saveBtn, soundBtn, deleteBtn;

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
        Word tmp2 = DictionaryManagement.addLookup(word);
        String text = tmp.getWordSpelling() + "\n" + tmp.getWordExplain() + tmp2.getWordExplain();
        defTextArea.setText(text);

        if(Dictionary.recentWord.size() == 10) Dictionary.recentWord.remove(0);
        if(Dictionary.recentWord.contains(word)) Dictionary.recentWord.remove(word);
        Dictionary.recentWord.add(word);
        recentSearch.setAll(Dictionary.recentWord.reversed());

        defTextArea.setVisible(true);
        saveBtn.setVisible(false);
    }

    @FXML
    private void clickSoundBtn() {
        URI uri = Paths.get("src/main/resources/data/output.mp3").toUri();

        // Tạo một đối tượng Media từ tệp âm thanh
        Media media = new Media(uri.toString());

        // Tạo một đối tượng MediaPlayer từ đối tượng Media
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Bắt đầu phát âm thanh
        mediaPlayer.play();

        // Đợi cho đến khi phát xong
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.stop();
        });
        System.out.println("Phát âm thanh");


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
        recentSearch.setAll(Dictionary.recentWord);
        suggResults.setItems(recentSearch);
        defTextArea.setWrapText(true);
        inputWord.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                defTextArea.setText("");
                wordTarget.setText("Definition");
                String word = inputWord.getText().trim();
                if (!inputWord.getText().isEmpty()) {
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


    }
}
