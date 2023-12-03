package TracNghiem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import models.DictionaryManagement;
import models.Word;

import java.net.URL;
import java.util.*;

public class WordGameController extends GameTracNghiemController {
    @FXML
    private TextField input;

    @FXML
    private Button enter, delete, shuffle;

    private int point;

    @FXML
    private Button firstChar, secondChar, thirdChar, fourthChar, fifthChar, sixChar, compulsoryChar;

    @FXML
    private Pane wordAdded;

    private final char[] vowels = {'A', 'E', 'I', 'O', 'U'};

    @FXML
    private Label Point, howManyWordAdded;

    @FXML
    private Label right = new Label("Good job");

    @FXML
    private Label noWord = new Label("No word found");

    @FXML
    private Label noCompulsoryChar = new Label("No compulsory word");

    private ArrayList<String> listWordAdded;

    char c;

    private int numOfWord = 0;

    public boolean alreadyAdded(String ans) {
        for (String fromList : listWordAdded) {
            if (fromList.equals(listWordAdded)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCompulsoryChar(String ans) {
        for (int i = 0; i < ans.length(); i++) {
            if (ans.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }



    public void initialize(URL url, ResourceBundle resourceBundle) {
        Random generates = new Random();

        HashSet<Character> usedChar = new HashSet<>();

        // Set nút 1
        c = (char)(generates.nextInt(26) + 'A');
        while (usedChar.contains(c)) {
            c = (char)(generates.nextInt(26) + 'A');
        }
        usedChar.add(c);
        firstChar.setText(String.valueOf(c));
        firstChar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                input.appendText(firstChar.getText());
            }
        });

        //Set nút 2
        c = (char)(generates.nextInt(26) + 'A');
        while (usedChar.contains(c)) {
            c = (char)(generates.nextInt(26) + 'A');
        }
        usedChar.add(c);
        secondChar.setText(String.valueOf(c));
        secondChar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                input.appendText(secondChar.getText());
            }
        });

        //Set nút 3
        int num = generates.nextInt(4);
        while (usedChar.contains(vowels[num])) {
            num = generates.nextInt(4);
        }
        thirdChar.setText(String.valueOf(vowels[num]));
        usedChar.add(vowels[num]);
        thirdChar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                input.appendText(thirdChar.getText());
            }
        });

        //Set nút 4
        num = generates.nextInt(4);
        while (usedChar.contains(vowels[num])) {
            num = generates.nextInt(4);
        }
        fourthChar.setText(String.valueOf(vowels[num]));
        usedChar.add(vowels[num]);
        fourthChar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                input.appendText(fourthChar.getText());
            }
        });

        //Set nút 5
        c = (char)('A' + generates.nextInt(26));
        while (usedChar.contains(c)) {
            c = (char)('A' + generates.nextInt(26));
        }
        usedChar.add(c);
        fifthChar.setText(String.valueOf(c));
        fifthChar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                input.appendText(fifthChar.getText());
            }
        });

        //Set nút 6
        c = (char)('A' + generates.nextInt(26));
        while (usedChar.contains(c)) {
            c = (char)('A' + generates.nextInt(26));
        }
        usedChar.add(c);
        sixChar.setText(String.valueOf(c));
        sixChar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                input.appendText(sixChar.getText());
            }
        });


        //Set nút bắt buộc
        c = (char)('A' + generates.nextInt(26));
        while (usedChar.contains(c)) {
            c = (char)('A' + generates.nextInt(26));
        }
        usedChar.add(c);
        compulsoryChar.setText(String.valueOf(c));
        compulsoryChar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                input.appendText(compulsoryChar.getText());
            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String getText = input.getText();
                if (getText.length() > 0) {
                    input.setText(getText.substring(0, getText.length() - 1));
                }
            }
        });

        listWordAdded = new ArrayList<>();
        enter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String answer = input.getText();
                DictionaryManagement check = new DictionaryManagement();
                if (!hasCompulsoryChar(answer)) {
                    showAlertInfo("Inappropriate word", "No compulsory character found");
                    input.clear();
                    return;
                }

                if(alreadyAdded(answer)) {
                    showAlertInfo("Inappropriate word", "Already added");
                    input.clear();
                    return;
                }

                if (!check.isInDictionary(answer)) {
                    showAlertInfo("No word found", "This word is not already existed");
                    input.clear();
                    return;
                }

                point += answer.length();
                Label word = new Label(answer);
                wordAdded.getChildren().add(word);
                showAlertInfo("Appropriate word", "Good job");
                Point.setText("Point = " + point);
                listWordAdded.add(answer);
                if (numOfWord == 0) {
                    howManyWordAdded.setText("You have found " + ++numOfWord + " word");
                } else {
                    howManyWordAdded.setText("You have found " + ++numOfWord + " words");
                }
                input.clear();
            }
        });
    }
}
