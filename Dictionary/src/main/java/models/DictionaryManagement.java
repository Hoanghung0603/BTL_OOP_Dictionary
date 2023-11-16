package models;

import java.util.Scanner;
import java.io.*;
import java.nio.*;
import java.util.*;

public class DictionaryManagement extends Dictionary {

    public DictionaryManagement() {}

    public void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        for(int i = 0; i < number; i++) {
        String engword = scanner.nextLine().toLowerCase();
        String explain = scanner.nextLine().toLowerCase();
        Word word = new Word(engword, explain);
        listWord.add(word);
        }
    }
    public void insertFavouriteWordFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        for(int i = 0; i < number; i++) {
            String engword = scanner.nextLine().toLowerCase();
            favoriteWord.add(engword);
        }
    }

    public static void deleteWordFromCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the word to delete: ");
        String wordTarget = scanner.nextLine();

        boolean deleted = DictionaryCommandline.dictionaryDelete(wordTarget);
        if (deleted) {
            System.out.println("Word deleted successfully.");
        } else {
            System.out.println("Word not found in the dictionary.");
        }
    }

    public static void dictionaryExportToFile() throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("data.txt"));
            for (Word word : listWord) {
                writer.write(word.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error exporting dictionary to file: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error closing file: " + e.getMessage());
                }
            }
        }
    }
    public static void printDictionarySearcher() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the word to search: ");
        String wordTarget = scanner.nextLine();

        ArrayList<Word> words = DictionaryCommandline.dictionarySearcher(wordTarget);
        if (words.size() > 0) {
            System.out.println("Found " + words.size() + " word(s):");
            for (Word word : words) {
                System.out.println(word);
            }
        } else {
            System.out.println("No word found.");
        }
    }
    public static void addWordFromCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the word: ");
        String wordTarget = scanner.nextLine();


        System.out.print("Enter the word's definition: ");
        String wordExplain = scanner.nextLine();

        Word newWord = new Word(wordTarget, wordExplain);
        listWord.add(newWord);

        System.out.println("Word added successfully.");
    }
    public static void lookupWordFromCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the word to lookup: ");
        String wordTarget = scanner.nextLine();

        Word word = DictionaryCommandline.dictionaryLookup(wordTarget);
        if (word != null) {
            System.out.println(word);
        } else {
            System.out.println("Word not found in the dictionary.");
        }
    }
    public static void editWordFromCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the word to edit: ");
        String wordTarget = scanner.nextLine();

        Word word = DictionaryCommandline.dictionaryLookup(wordTarget);
        if (word != null) {

            System.out.print("Enter the new word's definition (leave blank to keep the old one): ");
            String newWordExplain = scanner.nextLine();

            if (!newWordExplain.isEmpty()) {
                word.setWordExplain(newWordExplain);
            }

            System.out.println("Word edited successfully.");
        } else {
            System.out.println("Word not found in the dictionary.");
        }
    }

    public static void intro() {
        String x = "Welcome to My Application!\n[0] Exit \n [1] Add \n [2] Remove \n [3] Update \n [4] Display \n [5] Lookup " +
                "\n [6] Search \n [7] Game \n [8] Import from file \n [9] Export to file \n Your action: ";
        System.out.println(x);
    }
    public static void main (String[] args) throws IOException {
        printDictionarySearcher();
    }



}
