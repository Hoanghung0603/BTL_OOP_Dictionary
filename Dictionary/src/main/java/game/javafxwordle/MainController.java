package game.javafxwordle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class MainController implements Initializable {
    @FXML
    VBox guideVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        guideVbox.setPadding(new Insets(20, 20, 20, 20));
        Label helpParagraph = new Label("Guess the WORDLE in six tries. \n Each guess must be a valid five-letter word.\n Hit the enter button to submit.\n\n After each guess, the color of the tiles will change to \n show how close your guess was to the word.");
        helpParagraph.setTextAlignment(TextAlignment.CENTER);
        helpParagraph.getStyleClass().setAll("lead");

        Label labelExample = new Label("Examples");
        labelExample.getStyleClass().setAll("h3");
        labelExample.setTextAlignment(TextAlignment.LEFT);

        /* FIRST WORD */

        ArrayList<Label> firstWord = new ArrayList<>();
        Label label1 = new Label("W");
        label1.getStyleClass().setAll("correct-letter-example");
        firstWord.add(label1);
        for (String letter : new String[]{"E", "A", "R", "Y"}) {
            Label label = new Label(letter);
            label.getStyleClass().setAll("default-letter-example");
            firstWord.add(label);
        }
        HBox firstWordVBox = new HBox(3);
        for (Label label : firstWord)
            firstWordVBox.getChildren().add(label);
        Label firstWordLabel = new Label("The letter W is in the word and in the correct spot.");
        firstWordLabel.getStyleClass().setAll("lead");

        /* SECOND WORD */

        ArrayList<Label> secondWord = new ArrayList<>();
        Label labelP = new Label("P");
        labelP.getStyleClass().setAll("default-letter-example");
        Label labelI = new Label("I");
        labelI.getStyleClass().setAll("present-letter-example");
        secondWord.add(labelP);
        secondWord.add(labelI);
        for (String letter : new String[]{"L", "L", "S"}) {
            Label label = new Label(letter);
            label.getStyleClass().setAll("default-letter-example");
            secondWord.add(label);
        }
        HBox secondWordVBox = new HBox(3);
        for (Label label : secondWord)
            secondWordVBox.getChildren().add(label);
        Label secondWordLabel = new Label("The letter I is in the word but in the wrong spot.");
        secondWordLabel.getStyleClass().setAll("lead");

        /* THIRD WORD */

        ArrayList<Label> thirdWord = new ArrayList<>();
        for (String letter : new String[]{"V", "A", "G"}) {
            Label label = new Label(letter);
            label.getStyleClass().setAll("default-letter-example");
            thirdWord.add(label);
        }
        Label labelU = new Label("U");
        labelU.getStyleClass().setAll("wrong-letter-example");
        Label labelE = new Label("E");
        labelE.getStyleClass().setAll("default-letter-example");
        thirdWord.add(labelU);
        thirdWord.add(labelE);
        HBox thirdWordVBox = new HBox(3);
        for (Label label : thirdWord)
            thirdWordVBox.getChildren().add(label);
        Label thirdWordLabel = new Label("The letter U is not in the word in any spot.");
        thirdWordLabel.getStyleClass().setAll("lead");

        //guideVbox.setAlignment(Pos.TOP_CENTER);
        guideVbox.getChildren().addAll(helpParagraph, labelExample, firstWordVBox,
                firstWordLabel, secondWordVBox, secondWordLabel, thirdWordVBox, thirdWordLabel);



        this.createUI();
        try {
            this.getRandomWord();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Platform.runLater(() -> {
            gridPane.requestFocus();
            if (gridPane.isFocused()) {
                System.out.println("focusing grid");
            } else {
                System.out.println("focus fail");
            }
        });
    }

    private final MainHelper mainHelper = MainHelper.getInstance();

    @FXML
    public GridPane gridPane;
    @FXML
    public GridPane keyboardRow1;
    @FXML
    public GridPane keyboardRow2;
    @FXML
    public GridPane keyboardRow3;
    @FXML
    public ImageView helpIcon;
    @FXML
    public ImageView githubIcon;
    @FXML
    public HBox titleHBox;
    @FXML
    public ImageView restartIcon;

    public void createUI() {
        createGrid();
        createKeyboard();
        createTitleHBox();
    }

    public void createGrid() {
        mainHelper.createGrid(gridPane);
    }

    public void createKeyboard() {
        mainHelper.createKeyboard(keyboardRow1, keyboardRow2, keyboardRow3);
    }

    @FXML
    protected void onKeyPressed(KeyEvent keyEvent) throws SQLException {
        mainHelper.onKeyPressed(gridPane, keyboardRow1, keyboardRow2, keyboardRow3, keyEvent);
    }

    public void getRandomWord() throws SQLException {
        mainHelper.getRandomWord();
    }

    public void showHelp() {
        HelpWindow.display();
    }

    public void createTitleHBox() {
        mainHelper.createTitleHBox(titleHBox);
    }

    public void restart() {
        mainHelper.restart(restartIcon, gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
    }
    public static void showToast() {
        Toast.makeText();
    }

        public static void quit() {
        System.exit(0);
    }


}

