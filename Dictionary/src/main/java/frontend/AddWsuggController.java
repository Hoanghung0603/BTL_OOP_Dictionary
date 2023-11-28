package frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import models.DictionaryManagement;
import models.Word;

import java.net.URL;
import java.util.ResourceBundle;

public class AddWsuggController implements Initializable, Listener {
    @FXML
    AnchorPane addWsuggPane;
    @FXML
    Button editBtn, confEditBtn, resetBtn;
    @FXML
    TextArea defTextArea;

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
            //nếu newWord không có trong từ điển
            defTextArea.setText("Từ này chưa có trong từ điển. Nếu bạn muốn thêm vào từ điển, click Edit, sau đó nhập nghĩa của từ mới, cuối cùng click confirm");
            editBtn.setVisible(true);
        }
        //thêm điều kiện nếu newword rỗng
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ShareInfoAddWord.setListener(this);

        editBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resetBtn.setVisible(true);
                confEditBtn.setVisible(true);
                defTextArea.setEditable(true);
                prevDef = defTextArea.getText();
            }
        });

        confEditBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resetBtn.setVisible(false);
                defTextArea.setEditable(false);
                confEditBtn.setVisible(false);
                //nếu từ có trong từ điển -> sửa nghĩa
                //nếu không -> thêm vào từ điển
                //lấy định nghĩa vừa sửa:     defTextArea.getText()
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
