package frontend;

import Alert.AlertManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import models.Dictionary;
import models.DictionaryCommandline;
import models.DictionaryManagement;
import models.Word;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddWsuggController implements Initializable, Listener {
    @FXML
    AnchorPane addWsuggPane;
    @FXML
    Button editBtn, confEditBtn, resetBtn;
    @FXML
    TextArea defTextArea;
    @FXML
    Label stateUpdateAlert;

    String prevDef = "";
    @Override
    public void onNewWordChange() {
        if(!ShareInfoAddWord.getNewWord().equals("")) {   //thay điều kiện if này bằng nếu newword có trong từ điển
            //Nghĩa là chỗ này sẽ là chỗ của cái từ mới?
            Word word = DictionaryManagement.dictionaryLookup(ShareInfoAddWord.getNewWord());
            String lookup = word.toString2();
            defTextArea.setText(lookup);
            editBtn.setVisible(true);
        }
        else {
            defTextArea.setEditable(false);
            defTextArea.setText("");
            confEditBtn.setVisible(false);
            resetBtn.setVisible(false);
            editBtn.setVisible(true);
        }
    }

    private void setDefaultGUI(Boolean bool) {
        resetBtn.setVisible(bool);
        confEditBtn.setVisible(bool);
        defTextArea.setEditable(bool);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ShareInfoAddWord.setListener(this);

        editBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(confEditBtn.isVisible()) return;
                setDefaultGUI(true);
                prevDef = defTextArea.getText();
            }
        });

        confEditBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setDefaultGUI(false);
                //nếu từ có trong từ điển -> sửa nghĩa
                Alert confirmation = new AlertManager().alertConfirmation("Update", "Nghĩa của từ " + ShareInfoAddWord.getNewWord() + " sẽ được thay đổi");
                Optional<ButtonType> option = confirmation.showAndWait();
                if (option.get() == ButtonType.OK) {
                    //thay đổi nghĩa của từ
                    stateUpdateAlert.setText("Cập nhật thành công!");
                    AlertManager.showAlert(stateUpdateAlert);
                } else {
                    stateUpdateAlert.setText("Cập nhật không thành công!");
                    AlertManager.showAlert(stateUpdateAlert);
                }
                //nếu không -> thêm vào từ điển FORMAT LẠI CÁI NÀY -> WORD VÀ ADD VÀO LISTWORD
                //lấy định nghĩa vừa sửa:     defTextArea.getText() FORMAT LẠI CÁI NÀY -> WORD VÀ REPLACE VÀO LISTWORD

                Word word = DictionaryManagement.formatStringtoWord(ShareInfoAddWord.getNewWord() + "\t" + defTextArea.getText());
                //System.out.println(ShareInfoAddWord.getNewWord() + "\t" + defTextArea.getText());
                //System.out.print(DictionaryManagement.formatStringtoWord(ShareInfoAddWord.getNewWord() + "\t" + defTextArea.getText()).toString());
                if(DictionaryManagement.isInDictionary(ShareInfoAddWord.getNewWord())) {
                    Word oldWord = DictionaryManagement.dictionaryLookup(ShareInfoAddWord.getNewWord());
                    Dictionary.listWord.remove(oldWord);
                }
                System.out.print("NGU NGỐC" + " " + word.toString());
                Dictionary.listWord.add(word);

                //sửa lại nghĩa của từ
            }
        });

        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                defTextArea.setText(prevDef);
            }
        });
    }
}
