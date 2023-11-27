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
        return wordTarget + "\n" + wordSpelling + "\n" + wordExplain;
    }

    public String toString2() {
        return wordSpelling + "\n" + wordExplain;
    }

    public String toString3() {
        return wordTarget + "\t" + wordExplain + "\n";
    }



    @Override
    public int compareTo(Word other) {
        return this.wordTarget.compareToIgnoreCase(other.wordTarget);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Word)) {
            return false;
        }
        Word word = (Word) o;
        return Objects.equals(wordTarget, word.wordTarget) && Objects.equals(wordExplain, word.wordExplain);
    }

}