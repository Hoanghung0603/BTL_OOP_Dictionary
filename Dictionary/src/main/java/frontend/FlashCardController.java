package GameApplication;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class FlashCardController extends GameTracNghiemController{
    private List<String> englishWords = new ArrayList<>();

    private List<String> vietnameseWords = new ArrayList<>();

    private List<String> englishPronunciation = new ArrayList<>();

    Button nextWord, previousWord;

    Label currentWord;

    private final String englishWordFileLocation = "home";

    private final String vietnameseMeaningFileLocation = "home";

    private final String englishPronunciationFileLocation = "home";



}
