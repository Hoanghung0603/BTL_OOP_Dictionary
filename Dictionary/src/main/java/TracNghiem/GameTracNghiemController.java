package TracNghiem;


import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
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


import java.io.InputStream;
import java.util.*;
import java.net.URL;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class GameTracNghiemController implements Initializable {
    private final String questionFilePath = "Dictionary/src/main/resources/CauHoiTracNghiem.txt";

    private final String multipleChoicePathFile = "/resources/LuaChonTracNghiem.txt";

    private final String answerFilePath = "DapAnTracNghiem.txt";

    @FXML
    private Button doneButton = new Button();

    private int point = 0;

    private String choseButton= "-fx-background-color: yellow";

    private String rightAnswer = "-fx-background-color: green";

    private String wrongAnswer = "-fx-background-color: red";

    private String notChoseButton = "-fx-background-color: white";

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

    private int currQuestion = 1;


    protected final int timeForAGame = 90;

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
                setMark();
                countdown.stop();
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
            counter[i] = generateNumber.nextInt(0, 49);
            while (usedNumber.contains(counter[i])) {
                counter[i] = generateNumber.nextInt(0, 49);
            }
            usedNumber.add(counter[i]);
        }

    }

    public void setQuestion(int i, Button needToSet) {
        needToSet.setOnAction(event -> {
            question.setText(questionArray.get(counter[i]));
            answerA.setText(multipleChoiceArray.get(4 * counter[i]));
            if (chosenChoiceArray.get(i).equals(answerA.getText())) {
                answerA.setStyle(choseButton);
            } else {
                answerA.setStyle(notChoseButton);
            }
            answerA.setOnAction(event1 -> {
                if (chosenChoiceArray.get(i).equals(answerA.getText())) {
                    button1.setStyle(notChoseButton);
                    answerA.setStyle(notChoseButton);
                    chosenChoiceArray.set(i, "no answer");
                    return;
                }
                answerA.setStyle(choseButton);
                needToSet.setStyle(choseButton);
                answerB.setStyle(notChoseButton);
                answerC.setStyle(notChoseButton);
                answerD.setStyle(notChoseButton);
                chosenChoiceArray.set(i, answerA.getText());
            });

            answerB.setText(multipleChoiceArray.get(4 * counter[i] + 1));
            if (chosenChoiceArray.get(i).equals(answerB.getText())) {
                answerB.setStyle(choseButton);
            } else {
                answerB.setStyle(notChoseButton);
            }
            answerB.setOnAction(event1 -> {
                if (chosenChoiceArray.get(i).equals(answerB.getText())) {
                    needToSet.setStyle(notChoseButton);
                    answerB.setStyle(notChoseButton);
                    chosenChoiceArray.set(i, "no answer");
                    return;
                }
                needToSet.setStyle(choseButton);
                answerB.setStyle(choseButton);
                answerA.setStyle(notChoseButton);
                answerC.setStyle(notChoseButton);
                answerD.setStyle(notChoseButton);
                chosenChoiceArray.set(i, answerB.getText());
            });

            answerC.setText(multipleChoiceArray.get(4 * counter[i] + 2));
            if (chosenChoiceArray.get(i).equals(answerC.getText())) {
                answerC.setStyle(choseButton);
            } else {
                answerC.setStyle(notChoseButton);
            }
            answerC.setOnAction(event1 -> {
                if (!chosenChoiceArray.get(i).equals("no answer")) {
                    needToSet.setStyle(notChoseButton);
                    answerC.setStyle(notChoseButton);
                    chosenChoiceArray.set(i, "no answer");
                    return;
                }
                needToSet.setStyle(choseButton);
                answerC.setStyle(choseButton);
                answerB.setStyle(notChoseButton);
                answerD.setStyle(notChoseButton);
                answerA.setStyle(notChoseButton);
                chosenChoiceArray.set(i, answerC.getText());
            });

            answerD.setText(multipleChoiceArray.get(4 * counter[i] + 3));
            if (chosenChoiceArray.get(i).equals(answerD.getText())) {
                answerD.setStyle(choseButton);
            } else {
                answerD.setStyle(notChoseButton);
            }
            answerD.setOnAction(event1 -> {
                if (chosenChoiceArray.get(i).equals(answerD.getText())) {
                    needToSet.setStyle(notChoseButton);
                    answerD.setStyle(notChoseButton);
                    chosenChoiceArray.set(i, "no answer");
                    return;
                }

                needToSet.setStyle(choseButton);
                answerD.setStyle(choseButton);
                answerB.setStyle(notChoseButton);
                answerC.setStyle(notChoseButton);
                answerA.setStyle(notChoseButton);
                chosenChoiceArray.set(i, answerD.getText());
            });
        });
    }

    /*

    /**
     * tính điểm
     */
    public void setMark() {
        if (chosenChoiceArray.get(0).equals(answerArray.get(counter[0]))) {
            point++;
            button1.setStyle(rightAnswer);
        } else {
            button1.setStyle(wrongAnswer);
        }
        if (chosenChoiceArray.get(1).equals(answerArray.get(counter[1]))) {
            point++;
            button2.setStyle(rightAnswer);
        } else {
            button2.setStyle(wrongAnswer);
        }
        if (chosenChoiceArray.get(2).equals(answerArray.get(counter[2]))) {
            point++;
            button3.setStyle(rightAnswer);
        } else {
            button3.setStyle(wrongAnswer);
        }
        if (chosenChoiceArray.get(3).equals(answerArray.get(counter[3]))) {
            point++;
            button4.setStyle(rightAnswer);
        } else {
            button4.setStyle(wrongAnswer);
        }
        if (chosenChoiceArray.get(4).equals(answerArray.get(counter[4]))) {
            point++;
            button5.setStyle(rightAnswer);
        } else {
            button5.setStyle(wrongAnswer);
        }
        if (chosenChoiceArray.get(5).equals(answerArray.get(counter[5]))) {
            point++;
            button6.setStyle(rightAnswer);
        } else {
            button6.setStyle(wrongAnswer);
        }
        if (chosenChoiceArray.get(6).equals(answerArray.get(counter[6]))) {
            point++;
            button7.setStyle(rightAnswer);
        } else {
            button7.setStyle(wrongAnswer);
        }
        if (chosenChoiceArray.get(7).equals(answerArray.get(counter[7]))) {
            point++;
            button8.setStyle(rightAnswer);
        } else {
            button8.setStyle(wrongAnswer);
        }
        if (chosenChoiceArray.get(8).equals(answerArray.get(counter[8]))) {
            point++;
            button9.setStyle(rightAnswer);
        } else {
            button9.setStyle(wrongAnswer);
        }
        if (chosenChoiceArray.get(9).equals(answerArray.get(counter[9]))) {
            point++;
            button10.setStyle(rightAnswer);
        } else {
            button10.setStyle(wrongAnswer);
        }
        showAlertInfo("Kết quả", "Số điểm là: " + point);
    }

    /**
     * thông báo Alert.
     * @param title
     * @param content
     */
    public static void showAlertInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        alert.hide();
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
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setMark();
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
            }
        });
        startCountDownClock(timeForAGame);
    }

    public void setUpOpening() {
        question.setText(questionArray.get(counter[0]));
        answerA.setText(multipleChoiceArray.get(4 * counter[0]));
        answerB.setText(multipleChoiceArray.get(4 * counter[0] + 1));
        answerC.setText(multipleChoiceArray.get(4 * counter[0] + 2));
        answerD.setText(multipleChoiceArray.get(4 * counter[0] + 3));
        setQuestion(0, button1);
    }

}
