package frontend;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.*;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import service.API;
import service.SpeechAPI;
import service.T2SThread;

import java.util.ArrayList;


import java.net.URL;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    ObservableList<String> suggList = FXCollections.observableArrayList();
    ObservableList<String> recentSearch = FXCollections.observableArrayList();
    LinkedHashSet<String> favWords = new LinkedHashSet<>();

    @FXML
    AnchorPane searchPane;
    @FXML
    Label alert, wordTarget;  //wordtarget la label hien tu tieng anh dang search
    @FXML
    Button saveBtn, soundBtn, deleteBtn, showFavorWords;
    @FXML
    ListView<String> suggResults;
    @FXML
    TextArea defTextArea;
    @FXML
    TextField inputWord;
    @FXML
    ImageView yellowStar;

    String sourceWord = "";   //từ đang cần tra

    @FXML
    private void suggInputWord() {
        suggList.clear();
        sourceWord = sourceWord.toLowerCase();
        System.out.println(sourceWord);
        ArrayList<Word> list = new ArrayList<>();
        list = DictionaryCommandline.dictionarySearcher(sourceWord);
        for(Word x : list) {
            suggList.add(x.getWordTarget());
        }
        if (suggList.isEmpty()) {
            suggList.add("");
            alert.setVisible(true);
            FadeTransition fadeAlert = new FadeTransition(Duration.seconds(2.5), alert);
            fadeAlert.setFromValue(1.0);
            fadeAlert.setToValue(0.0);
            fadeAlert.play();
        }
        suggResults.setItems(suggList);
    }

    //khi click vao mot tu trong suggResults
    @FXML
    private void handleMouseClickSuggWord(MouseEvent event) {
        sourceWord = suggResults.getSelectionModel().getSelectedItem().trim();
        if(Dictionary.favoriteWord.contains(sourceWord))  {
            yellowStar.setVisible(true);
            System.out.println("true");
        }
        else yellowStar.setVisible(false);
        if (!wordTarget.equals("") && !wordTarget.equals("Definition")) {
            soundBtn.setDisable(false);
            saveBtn.setDisable(false);
        }
//
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
        if(suggList.getFirst().equals("")) suggResults.getSelectionModel().selectFirst();

        defTextArea.setVisible(true);
    }

    @FXML //DONE
    private void clickSoundBtn() throws Exception {
        if(!API.isInternetAvailable()) {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            Voice voice = VoiceManager.getInstance().getVoice("kevin16");
            if (voice != null) {
                voice.allocate();
                voice.speak(sourceWord);
            } else throw new IllegalStateException("Cannot find voice: kevin16");
        }
        else {
            T2SThread t2sThread = new T2SThread();
            t2sThread.getSpeechFromTextThread(sourceWord, "en");
        }
    }

    @FXML
    private void handleClickSaveBtn() {
        //thêm từ vừa tra vào danh sách từ đã lưu
        if(!Dictionary.favoriteWord.contains(sourceWord)) {
            Dictionary.favoriteWord.add(sourceWord);
            yellowStar.setVisible((true));
        }
        else {
            //Neu da co thi xoa
            Dictionary.favoriteWord.remove(sourceWord);
            yellowStar.setVisible((false));
        }

    }

    @FXML
    private void handleClickShowFavorWords() {
        yellowStar.setVisible(false);
        suggList.setAll(Dictionary.favoriteWord.reversed());
        suggResults.setItems(suggList);
        wordTarget.setText("Definition");
        saveBtn.setDisable(true);
        soundBtn.setDisable(true);
        defTextArea.setText("");
        inputWord.setText("");
        deleteBtn.setVisible(false);
    }

    //them
    @FXML
    private void handleClickDeleteBtn() {
        yellowStar.setVisible(false);
        wordTarget.setText("Definition");
        sourceWord = "";
        soundBtn.setDisable(true);
        saveBtn.setDisable(true);
        defTextArea.setText("");
        inputWord.setText("");
        suggList.clear();
        suggList.add("");
        suggResults.setItems(recentSearch);
        deleteBtn.setVisible(false);

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
      //  dictionaryManagement.insertFromFile();
        FadeTransition fadeTrans = new FadeTransition(Duration.seconds(1.0), searchPane);
        fadeTrans.setFromValue(0);
        fadeTrans.setToValue(1);
        fadeTrans.play();
        alert.setVisible(false);
        suggList.add("");
        recentSearch.setAll(Dictionary.recentWord);
        suggResults.setItems(recentSearch);

        inputWord.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                saveBtn.setDisable(true);
                soundBtn.setDisable(true);
                defTextArea.setText("");
                wordTarget.setText("Definition");
                sourceWord = inputWord.getText().trim();
                deleteBtn.setVisible(true);
                if (!sourceWord.equals("")) {
                    suggInputWord();
                } else {
                    suggList.clear();
                    suggList.add("");
                    suggResults.setItems(recentSearch);
                    if (sourceWord.equals("")) deleteBtn.setVisible(false);
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
