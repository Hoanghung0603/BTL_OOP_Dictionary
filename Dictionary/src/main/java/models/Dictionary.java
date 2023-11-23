package models;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

public class Dictionary {
    public static TreeSet<Word> listWord = new TreeSet<Word>();
    public static LinkedList<String> favoriteWord = new LinkedList<String>();
    public static TreeSet<Word> listAdd = new TreeSet<>();

    public static HashSet<String> recentWord = new HashSet<>();

    //public static LinkedList<String> removedWord = new LinkedList<String>();
    //public static LinkedList<Word> editedWord = new LinkedList<Word>();
}