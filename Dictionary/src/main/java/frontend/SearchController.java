package frontend;

import Alert.AlertManager;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
import service.API;
import service.T2SThread;
import java.util.ArrayList;


import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    ObservableList<String> suggList = FXCollections.observableArrayList();
    ObservableList<String> recentSearch = FXCollections.observableArrayList();

    @FXML
    AnchorPane searchPane;
    @FXML
    Label invalidWordAlert, showFavWordAlert, wordTarget, likeWordAlert;  //wordtarget la label hien tu tieng anh dang search
    @FXML
    Label sugLabel, correctWord;
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

    @FXML
    Timeline timeline;

    String sourceWord = "";   //từ đang cần tra
    boolean isShowingFavWords = false;

    // hàm này để dựa vào word, setText của correct word thành từ auto correct
    private void setCorrectWord(String word) {
        sugLabel.setVisible(true);
        String word_da_sua = DictionaryManagement.autoCorrect(sourceWord);
        // đang mặc định từ auto correct là hello
        if(!word_da_sua.equals("Not found")) {
            correctWord.setText(word_da_sua);
        }
        else if(word_da_sua.equals("Not found") || DictionaryManagement.isInDictionary(word_da_sua))
            sugLabel.setVisible(false);

    }
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

                sugLabel.setVisible(false);
                correctWord.setText("");

                AlertManager.showAlert(invalidWordAlert);
            }
            suggResults.setItems(suggList);

            //setCorrectWord(sourceWord);


            if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING) {
                timeline.stop(); // Dừng Timeline nếu đang chạy
            }

            timeline = new Timeline(new KeyFrame(
                    Duration.seconds(1),
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            setCorrectWord(sourceWord);
                        }
                    })
            );
            timeline.play();
        }

    //khi click vao mot tu trong suggResults
    @FXML
    private void handleMouseClickSuggWord(MouseEvent event) {
        sourceWord = suggResults.getSelectionModel().getSelectedItem();
        if (sourceWord != null) sourceWord.trim();
        else return;
        if (Dictionary.favoriteWord.contains(sourceWord))  {
            yellowStar.setVisible(true);
            System.out.println("true");
        }
        else yellowStar.setVisible(false);
        if (!wordTarget.equals("") && !wordTarget.equals("Definition")) {
            soundBtn.setDisable(false);
            saveBtn.setDisable(false);
        }

        String word = suggResults.getSelectionModel().getSelectedItem();
        if (word == null) return;

        sugLabel.setVisible(false);
        correctWord.setText("");

        wordTarget.setText(word);
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
            likeWordAlert.setText("Add to favorite words list");
            AlertManager.showAlert(likeWordAlert);
            Dictionary.favoriteWord.add(sourceWord);
            yellowStar.setVisible((true));
        }
        else {
            likeWordAlert.setText("Remove from favorite words list");
            AlertManager.showAlert(likeWordAlert);
            //Neu da co thi xoa
            Dictionary.favoriteWord.remove(sourceWord);
            yellowStar.setVisible((false));
        }
    }

    @FXML
    private void handleClickShowFavorWords() {
        if(!isShowingFavWords) {
            showFavWordAlert.setText("Showing favorite words list!");
            AlertManager.showAlert(showFavWordAlert);
            suggList.setAll(Dictionary.favoriteWord.reversed());
            suggResults.setItems(suggList);
            inputWord.setText("");
            deleteBtn.setVisible(false);
            isShowingFavWords = true;
        }
        else {
            showFavWordAlert.setText("Close favorite words list!");
            AlertManager.showAlert(showFavWordAlert);
            isShowingFavWords = false;
            if(suggList.isEmpty()) suggList.add("");
            suggResults.setItems(recentSearch);
        }
        sugLabel.setVisible(false);
        correctWord.setText("");
        setDefaultSearchGUI();
    }

    //them
    @FXML
    private void handleClickDeleteBtn() {
        sourceWord = "";
        setDefaultSearchGUI();

        inputWord.setText("");

        suggList.clear();
        suggList.add("");
        suggResults.setItems(recentSearch);

        deleteBtn.setVisible(false);
        sugLabel.setVisible(false);
        correctWord.setText("");
    }

    private void setDefaultSearchGUI() {
        yellowStar.setVisible(false);
        wordTarget.setText("Definition");
        soundBtn.setDisable(true);
        saveBtn.setDisable(true);
        defTextArea.setText("");
    }

    @FXML
    private void handleMouseClickCorrectWordLabel() {
        sourceWord = correctWord.getText();
        inputWord.setText(sourceWord);
        suggInputWord();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        //  dictionaryManagement.insertFromFile();
        FadeTransition fadeTrans = new FadeTransition(Duration.seconds(1.0), searchPane);
        fadeTrans.setFromValue(0);
        fadeTrans.setToValue(1);
        fadeTrans.play();
        suggList.add("");
        recentSearch.setAll(Dictionary.recentWord);
        suggResults.setItems(recentSearch);

        inputWord.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                setDefaultSearchGUI();
                isShowingFavWords = false;
                sourceWord = inputWord.getText().trim();
                deleteBtn.setVisible(true);
                System.out.println(DictionaryManagement.autoCorrect(sourceWord));
                if (!sourceWord.equals("")) {
                    suggInputWord();
                } else {
                    suggList.clear();
                    suggList.add("");
                    suggResults.setItems(recentSearch);

                    if (sourceWord.equals("")) {
                        deleteBtn.setVisible(false);

                        sugLabel.setVisible(false);
                        correctWord.setText("");
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
