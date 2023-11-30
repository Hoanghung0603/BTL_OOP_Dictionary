package GameApplication;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;


import java.util.*;
import java.net.URL;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class HelloController extends GameTracNghiemController {
    private char firstLetter;

    private boolean isRunningGame = true;

    private final Integer easyTime = 15, normalTime = 10, hardTime = 5;

    private int scorePLayer1 = 0, scorePlayer2 = 0;

    @FXML
    private Label timeLeft, scorePoints, playerTurn, wordBefore;

    @FXML
    private Button deleteButton, enterButton;

    @FXML
    private TextField input;

    @FXML
    private VBox player1Pane, player2Pane;

    @FXML
    private AnimationTimer gameLoop;

    @FXML
    private Pane mainPane;

    private static final String player1 = "It's player 1's turn";

    private static final String player2 = "It's player 2's turn";

    private int turn = 0;

    private URL url;
    private ResourceBundle resourceBundle;

    @FXML
    Timeline countdown;

    private IntegerProperty seconds = new SimpleIntegerProperty();

    Label rightWord = new Label("Từ này có trong từ điển");

    Label wrongWord = new Label("Từ này không có trong từ điển");

    Label khongPhuHopVoiDeBai = new Label("Từ này không bắt đầu bằng chữ cái cuối từ trước");

    public void rightWord() {
        rightWord.setVisible(false);
        mainPane.getChildren().add(rightWord);

    }

    /*
    public void HideLabelExample() {

        myLabel.setVisible(false);
        getChildren().add(myLabel);

        PauseTransition visible = new PauseTransition(Duration.seconds(2));
        visible.setOnFinished(e -> myLabel.setVisible(true));

        PauseTransition hidden = new PauseTransition(Duration.seconds(3));
        hidden.setOnFinished(e -> myLabel.setVisible(false));

        visible.play();
        hidden.playAfter(visible);

    }
     */

    public static void showAlertInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        alert.hide();
    }

    private static boolean playAgain() {
        Alert gameOver = new Alert(Alert.AlertType.CONFIRMATION);
        Button playAgain = new Button("Có");
        gameOver.setTitle("Game Over");
        gameOver.setHeaderText(null);
        gameOver.setContentText("Bạn có muốn chơi lại không");
        gameOver.show();

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        gameOver.getButtonTypes().setAll(okButton);

        Optional<ButtonType> result = gameOver.showAndWait();
        return result.get() == okButton;
    }

    private boolean gameOver(int timesForAGame) {
        return (timesForAGame == 0 ? true : false);
    }

    public void startGame() {
        playerTurn = new Label(player1);
        deleteButton.setText("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(input.getText().length() > 0) {
                    // Delete last char
                    String newText = input.getText().substring(0, input.getText().length()-1);
                    input.setText(newText);
                }
            }
        });
        enterButton.setText("Enter");
        enterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    File read = new File("/Users/nguyentiendat/Desktop/FrontEnds-master/src/main/resources/GameApplication/word.txt");
                    Scanner sc = new Scanner(read);
                    boolean hasWord = false;
                        while (sc.hasNextLine()) {
                        String data = sc.nextLine();
                        if (data.compareTo(input.getText().toLowerCase()) == 0) {
                            Label newWord = new Label();
                            Font nice = new Font(18.0);
                            newWord.setFont(nice);
                            newWord.setText(input.getText());
                            if (turn % 2 == 0) {
                                player1Pane.getChildren().add(newWord);
                                scorePLayer1++;
                                playerTurn.setText(player2);
                                scorePoints.setText("Current score: " + scorePlayer2);
                            } else {
                                player2Pane.getChildren().add(newWord);
                                scorePlayer2++;
                                playerTurn.setText(player1);
                                scorePoints.setText("Current score: " + scorePLayer1);
                            }
                            turn++;
                            input.clear();
                            hasWord = true;
                            HelloController.showAlertInfo("Kết quả","Bạn đã đoán đúng");
                            if (scorePLayer1 > 5 || scorePlayer2 > 5) {
                                startCountDownClock(normalTime);
                            }
                            else if (scorePLayer1 > 10 || scorePlayer2 > 10) {
                                startCountDownClock(hardTime);
                            }
                            else {
                                startCountDownClock(easyTime);
                            }
                            break;
                        }
                    }
                    if (!hasWord) {
                        HelloController.showAlertInfo("Kết quả","Không có từ trong từ ");
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        });
        startCountDownClock(easyTime);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startGame();
    }
}
