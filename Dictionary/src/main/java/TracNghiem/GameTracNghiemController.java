package TracNghiem;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.application.Platform;


import java.io.InputStream;
import java.util.*;
import java.net.URL;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class GameTracNghiemController implements Initializable {

    @FXML
    private Button doneButton = new Button();
    private int point = 0;

    @FXML
    private Label question = new Label();
    @FXML
    private Button answerA = new Button(), answerB = new Button(), answerC = new Button(), answerD = new Button();
    @FXML
    private Button retryButton = new Button(), menuButton = new Button();

    private List<String> questionArray, multipleChoiceArray, chosenChoiceArray, answerArray;

    private int[] counter = new int[10];

    protected IntegerProperty seconds = new SimpleIntegerProperty();

    @FXML
    protected Timeline countdown;

    @FXML
    protected Label timeLeft = new Label();

    private boolean isRunning;


    protected final int timeForAGame = 75;

    @FXML
    private Button button1, button2, button3, button4, button5;
    @FXML
    private Button button6, button7, button8, button9, button10;

    private boolean[] listOfResult = new boolean[10];

    /**
     * Đồng hồ đến ngược thời gian làm bài
     * @param initialSeconds
     */
    public void startCountDownClock(int initialSeconds) {
        seconds.set(initialSeconds);

        countdown = new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
            seconds.set(seconds.get() - 1);
            timeLeft.setText("Time left: " + seconds.get() + " seconds");
            if (seconds.get() <= 0) {
                countdown.stop();
                isRunning = false;
                setMark();
                Alert result = alertInfo("Kết quả", "Số điểm là: " + point);
                Platform.runLater(() -> {
                    result.showAndWait();
                });
            }

        }));
        countdown.setCycleCount(initialSeconds);
        countdown.play();
    }

    /**
     * đưa 50 câu hỏi vào
     */
    public void loadQuestion() {
        questionArray = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("CauHoiTracNghiem.txt");
        //File readQuestionFromFile = new File(ClassLoader.getSystemResourceAsStream("CauHoiTracNghiem.txt"));
        Scanner sc = new Scanner(inputStream);
        while (sc.hasNextLine()) {
            questionArray.add(sc.nextLine());
        }
    }

    /**
     * Đọc 200 câu trả lời vào
     */
    public void loadMultipleChoice() {
        multipleChoiceArray = new ArrayList<>();
        chosenChoiceArray = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            chosenChoiceArray.add("no answer");
        }
        multipleChoiceArray = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("LuaChonTracNghiem.txt");
        // File readChoiceFromFile = new File(multipleChoicePathFile);
        Scanner sc = new Scanner(inputStream);
        while (sc.hasNextLine()) {
            multipleChoiceArray.add(sc.nextLine());
        }
    }

    /**
     * đọc 50 đáp án vào
     */
    public void loadAnswer() {
        answerArray = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("DapAnTracNghiem.txt");
        Scanner sc = new Scanner(inputStream);
        while (sc.hasNextLine()) {
            answerArray.add(sc.nextLine());
        }
    }

    /**
     * Chọn ra 10 câu ngẫu nhiên để đưa vào bài.
     */
    public void loadCounter() {
        counter = new int[10];
        listOfResult = new boolean[10];
        Arrays.fill(listOfResult, false);
        Collections.fill(chosenChoiceArray, "no answer");
        HashSet<Integer> usedNumber = new HashSet<>();
        Random generateNumber = new Random();
        for (int i = 0; i < 10; i++) {
            counter[i] = generateNumber.nextInt(0, 59);
            while (usedNumber.contains(counter[i])) {
                counter[i] = generateNumber.nextInt(0, 59);
            }
            usedNumber.add(counter[i]);
        }

    }

    public void lockAnswer() {
        answerA.setDisable(true);
        answerB.setDisable(true);
        answerC.setDisable(true);
        answerD.setDisable(true);
    }

    public void openAnswer() {
        answerA.setDisable(false);
        answerB.setDisable(false);
        answerC.setDisable(false);
        answerD.setDisable(false);
    }

    public void clearSelection() {
        answerA.getStyleClass().clear();
        answerA.getStyleClass().add("choice-box");


        answerB.getStyleClass().clear();
        answerB.getStyleClass().add("choice-box");

        answerC.getStyleClass().clear();
        answerC.getStyleClass().add("choice-box");

        answerD.getStyleClass().clear();
        answerD.getStyleClass().add("choice-box");
    }


    public void setQuestion(int i, Button needToSet) {
        openAnswer();
        needToSet.setOnAction(event -> {
            clearSelection();
            if (!isRunning) {
                showRightAndWrong(i);
                return;
            }
            question.getStyleClass().add("question-text");
            question.setText(questionArray.get(counter[i]));
            answerA.setText(multipleChoiceArray.get(4 * counter[i]));
            if (chosenChoiceArray.get(i).equals(answerA.getText())) {
                answerA.getStyleClass().remove("choice-box");
                answerA.getStyleClass().add("choice-box-selected");
            }
            answerA.setOnAction(event1 -> {
                if (chosenChoiceArray.get(i).equals(answerA.getText())) {
                    needToSet.getStyleClass().remove("choose");
                    needToSet.getStyleClass().add("nav-button");
                    answerA.getStyleClass().remove("choice-box-selected");
                    answerA.getStyleClass().add("choice-box");
                    chosenChoiceArray.set(i, "no answer");
                    return;
                }
                clearSelection();
                answerA.getStyleClass().add("choice-box-selected");
                needToSet.getStyleClass().add("choose");
                chosenChoiceArray.set(i, answerA.getText());
            });

            answerB.setText(multipleChoiceArray.get(4 * counter[i] + 1));
            if (chosenChoiceArray.get(i).equals(answerB.getText())) {
                answerB.getStyleClass().remove("choice-box");
                answerB.getStyleClass().add("choice-box-selected");
            }
            answerB.setOnAction(event1 -> {
                if (chosenChoiceArray.get(i).equals(answerB.getText())) {
                    needToSet.getStyleClass().remove("choose");
                    needToSet.getStyleClass().add("nav-button");
                    answerB.getStyleClass().remove("choice-box-selected");
                    answerB.getStyleClass().add("choice-box");
                    chosenChoiceArray.set(i, "no answer");
                    return;
                }
                clearSelection();
                needToSet.getStyleClass().add("choose");
                answerB.getStyleClass().add("choice-box-selected");
                chosenChoiceArray.set(i, answerB.getText());
            });

            answerC.setText(multipleChoiceArray.get(4 * counter[i] + 2));
            if (chosenChoiceArray.get(i).equals(answerC.getText())) {
                answerC.getStyleClass().remove("choice-box");
                answerC.getStyleClass().add("choice-box-selected");
            }
            answerC.setOnAction(event1 -> {
                if (chosenChoiceArray.get(i).equals(answerC.getText())) {
                    needToSet.getStyleClass().remove("choose");
                    needToSet.getStyleClass().add("nav-button");
                    answerC.getStyleClass().remove("choice-box-selected");
                    answerC.getStyleClass().add("choice-box");
                    chosenChoiceArray.set(i, "no answer");
                    return;
                }
                clearSelection();
                needToSet.getStyleClass().add("choose");
                answerC.getStyleClass().add("choice-box-selected");
                chosenChoiceArray.set(i, answerC.getText());
            });

            answerD.setText(multipleChoiceArray.get(4 * counter[i] + 3));
            if (chosenChoiceArray.get(i).equals(answerD.getText())) {
                answerD.getStyleClass().remove("choice-box");
                answerD.getStyleClass().add("choice-box-selected");
            }
            answerD.setOnAction(event1 -> {
                if (chosenChoiceArray.get(i).equals(answerD.getText())) {
                    needToSet.getStyleClass().remove("choose");
                    needToSet.getStyleClass().add("nav-button");
                    answerD.getStyleClass().add("choice-box");
                    answerD.getStyleClass().remove("choice-box-selected");
                    chosenChoiceArray.set(i, "no answer");
                    return;
                }
                clearSelection();
                needToSet.getStyleClass().add("choose");
                answerD.getStyleClass().add("choice-box-selected");
                chosenChoiceArray.set(i, answerD.getText());
            });
        });
    }

    public void setCertainMark(Button needToSet, int i) {
        if (needToSet.getStyleClass().contains("choose") || needToSet.getStyleClass().contains("nav.button")) {
            needToSet.getStyleClass().clear();
        }
        if (chosenChoiceArray.get(i).equals(answerArray.get(counter[i]))) {
            point++;
            needToSet.getStyleClass().add("right");
            listOfResult[i] = true;
        } else {
            needToSet.getStyleClass().add("wrong");
            listOfResult[i] = false;
        }
    }

    public void showRightAndWrong(int i) {
        question.setText(questionArray.get(counter[i]));
        answerA.setText(multipleChoiceArray.get(4 * counter[i]));
        answerB.setText(multipleChoiceArray.get(4 * counter[i] + 1));
        answerC.setText(multipleChoiceArray.get(4 * counter[i] + 2));
        answerD.setText(multipleChoiceArray.get(4 * counter[i] + 3));
        if (answerArray.get(counter[i]).equals(answerA.getText())) {
            answerA.getStyleClass().add("choice-box-right");
        }
        if (chosenChoiceArray.get(i).equals(answerA.getText()) && !listOfResult[i]) {
            answerA.getStyleClass().add("choice-box-wrong");
        }

        if (answerArray.get(counter[i]).equals(answerB.getText())) {
            answerB.getStyleClass().add("choice-box-right");
        }
        if (chosenChoiceArray.get(i).equals(answerB.getText()) && !listOfResult[i]) {
            answerB.getStyleClass().add("choice-box-wrong");
        }

        if (answerArray.get(counter[i]).equals(answerC.getText())) {
            answerC.getStyleClass().add("choice-box-right");
        }
        if (chosenChoiceArray.get(i).equals(answerC.getText()) && !listOfResult[i]) {
            answerC.getStyleClass().add("choice-box-wrong");
        }

        if (answerArray.get(counter[i]).equals(answerD.getText())) {
            answerD.getStyleClass().add("choice-box-right");
        }
        if (chosenChoiceArray.get(i).equals(answerD.getText()) && !listOfResult[i]) {
            answerD.getStyleClass().add("choice-box-wrong");
        }
    }

    /*

    /**
     * tính điểm
     */
    public void setMark() {
        setCertainMark(button1, 0);
        setCertainMark(button2, 1);
        setCertainMark(button3, 2);
        setCertainMark(button4, 3);
        setCertainMark(button5, 4);
        setCertainMark(button6, 5);
        setCertainMark(button7, 6);
        setCertainMark(button8, 7);
        setCertainMark(button9, 8);
        setCertainMark(button10, 9);
    }

    /**
     * thông báo Alert.
     * @param title
     * @param content
     */
    public static void showAlertInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        alert.hide();
    }

    public static Alert alertInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert;
    }

    public void startQuiz() {
        loadQuestion();
        loadMultipleChoice();
        loadAnswer();
        loadCounter();
        setQuestion(0, button1);
        setQuestion(1, button2);
        setQuestion(2, button3);
        setQuestion(3, button4);
        setQuestion(4, button5);
        setQuestion(5, button6);
        setQuestion(6, button7);
        setQuestion(7, button8);
        setQuestion(8, button9);
        setQuestion(9, button10);
    }

    public void resetQuiz() {
        point = 0;
        for (int i = 0; i < 10; i++) {
            chosenChoiceArray.set(i, "no answer");
        }
        button1.getStyleClass().clear();
        button2.getStyleClass().clear();
        button3.getStyleClass().clear();
        button4.getStyleClass().clear();
        button5.getStyleClass().clear();
        button6.getStyleClass().clear();
        button7.getStyleClass().clear();
        button8.getStyleClass().clear();
        button9.getStyleClass().clear();
        button10.getStyleClass().clear();

        button1.getStyleClass().add("nav-button");
        button2.getStyleClass().add("nav-button");
        button3.getStyleClass().add("nav-button");
        button4.getStyleClass().add("nav-button");
        button5.getStyleClass().add("nav-button");
        button6.getStyleClass().add("nav-button");
        button7.getStyleClass().add("nav-button");
        button8.getStyleClass().add("nav-button");
        button9.getStyleClass().add("nav-button");
        button10.getStyleClass().add("nav-button");


        answerA.getStyleClass().clear();
        answerB.getStyleClass().clear();
        answerC.getStyleClass().clear();
        answerD.getStyleClass().clear();

        answerA.getStyleClass().add("choice-box");
        answerB.getStyleClass().add("choice-box");
        answerC.getStyleClass().add("choice-box");
        answerD.getStyleClass().add("choice-box");
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadQuestion();
        loadMultipleChoice();
        loadAnswer();
        loadCounter();
        lockAnswer();
        setQuestion(0, button1);
        setQuestion(1, button2);
        setQuestion(2, button3);
        setQuestion(3, button4);
        setQuestion(4, button5);
        setQuestion(5, button6);
        setQuestion(6, button7);
        setQuestion(7, button8);
        setQuestion(8, button9);
        setQuestion(9, button10);
        isRunning = true;
        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isRunning = false;
                setMark();
                showAlertInfo("Kết quả", "Point: " + point);
                countdown.stop();
            }
        });
        retryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetQuiz();
                startQuiz();
                countdown.stop();
                setUpOpening();
                startCountDownClock(timeForAGame);
                isRunning = true;
            }
        });
        startCountDownClock(timeForAGame);
    }

    public void setUpOpening() {
        timeLeft.setText("Time left : 75 seconds");
        question.setText("Click on any question to start");
        answerA.setText("A");
        answerB.setText("B");
        answerC.setText("C");
        answerD.setText("D");
    }

}
