package models;

import java.util.Objects;

public class Word implements Comparable<Word> {
    private String wordTarget;

    private String wordSpelling;
    private String wordExplain;



    /**
     * hamn khoi tao
     */
    public Word() {
        this.wordExplain = "";
        this.wordTarget = "";
    }


    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    public Word(String target,String spelling, String explain)
    {
        setWordExplain(explain);
        setWordTarget(target);
        setWordSpelling(spelling);
    }

    public String getWordSpelling() {
        return wordSpelling;
    }
    public void setWordSpelling(String wordSpelling) {
        this.wordSpelling = wordSpelling;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public String toString() {
        return wordTarget + "\t" + wordSpelling + "\n" + wordExplain;
    }

    @Override
    public int compareTo(Word other) {
        return this.wordTarget.compareToIgnoreCase(other.wordTarget);
    }


}