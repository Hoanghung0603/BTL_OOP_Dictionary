package frontend;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.DictionaryManagement;
import models.Word;

import java.net.URL;
import java.util.ResourceBundle;

public class AddWsuggController implements Initializable, Listener {
    @FXML
    AnchorPane addWsuggPane;
    @FXML
    TextArea defTextArea;

    @Override
    public void onNewWordChange() {
        if(!ShareInfoAddWord.getNewWord().equals("")) {
            //rewrite this
            //shareInfoaddword.getnewWord
            //Nghĩa là chỗ này sẽ là chỗ của cái từ mới?
            // Tuwf m
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Word word = DictionaryManagement.dictionaryLookup(ShareInfoAddWord.getNewWord());
            String lookup = word.toString2();
            defTextArea.setText(lookup);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition tranTransition = new TranslateTransition(Duration.seconds(0.75), addWsuggPane);
        tranTransition.setByX(26);
        tranTransition.play();
        ShareInfoAddWord.setListener(this);

    }
}
