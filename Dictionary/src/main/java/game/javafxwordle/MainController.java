package game.javafxwordle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class MainController implements Initializable {
    public static final ArrayList<String> winningWords = new ArrayList<>();
    public static final ArrayList<String> dictionaryWords = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeWordLists();
        this.createUI();
        this.getRandomWord();
//        try {
//            this.helpIcon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/help.png"))));
//            //this.restartIcon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Utils/images/icons8-restart-40.png"))));
//        } catch (NullPointerException e) {
//            // Xử lý ngoại lệ, ví dụ: hiển thị thông báo lỗi hoặc thực hiện hành động phù hợp.
//            e.printStackTrace();
//        }

        this.gridRequestFocus();
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

    public void gridRequestFocus() {
        gridPane.requestFocus();
    }

    @FXML
    protected void onKeyPressed(KeyEvent keyEvent) {
        mainHelper.onKeyPressed(gridPane, keyboardRow1, keyboardRow2, keyboardRow3, keyEvent);
    }

    public void getRandomWord() {
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

    public void initializeWordLists() {
        InputStream winning_words = getClass().getResourceAsStream("/winning-words.txt");
        InputStream dictionary = getClass().getResourceAsStream("/dictionary.txt");
        if(winning_words == null) System.out.println("CHWA LOAD FILE");
        if(dictionary == null) System.out.println("CUNG CHUA LOAD DIC");
        if (winning_words != null && dictionary != null) {
            Stream<String> winning_words_lines = new BufferedReader(new InputStreamReader(winning_words)).lines();
            winning_words_lines.forEach(winningWords::add);
            Stream<String> dictionary_lines = new BufferedReader(new InputStreamReader(dictionary)).lines();
            dictionary_lines.forEach(dictionaryWords::add);
        } else {
            System.exit(0);
        }
    }
}
