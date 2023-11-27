package frontend;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    Button editBtn, confEditBtn;
    @FXML
    TextArea defTextArea;
    @Override
    public void onNewWordChange() {
        if(!ShareInfoAddWord.getNewWord().equals("")) {
            //Nghĩa là chỗ này sẽ là chỗ của cái từ mới?
            Word word = DictionaryManagement.dictionaryLookup(ShareInfoAddWord.getNewWord());
            String lookup = word.toString2();
            defTextArea.setText(lookup);
            editBtn.setVisible(true);
        }
        else {
            defTextArea.setText("");
            editBtn.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        TranslateTransition tranTransition = new TranslateTransition(Duration.seconds(0.1), addWsuggPane);
//        tranTransition.setByX(-55);
//        tranTransition.play();
        ShareInfoAddWord.setListener(this);
        defTextArea.setEditable(false);
        confEditBtn.setVisible(false);
        editBtn.setVisible(false);

        editBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                confEditBtn.setVisible(true);
                defTextArea.setEditable(true);
            }
        });

        confEditBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                defTextArea.setEditable(false);
                confEditBtn.setVisible(false);
                //sửa lại nghĩa của từ
            }
        });
    }
}
