package models;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.LinkedHashSet;

public class Dictionary {
    public static TreeSet<Word> listWord = new TreeSet<Word>();
    public static LinkedList<String> favoriteWord = new LinkedList<String>();
    public static TreeSet<Word> listAdd = new TreeSet<>();
    public static LinkedHashSet<String> recentWord = new LinkedHashSet<>();

    //public static LinkedList<String> removedWord = new LinkedList<String>();
    //public static LinkedList<Word> editedWord = new LinkedList<Word>();
}