package models;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.*;
import java.nio.*;
import java.util.*;



public class DictionaryManagement extends Dictionary {

    public DictionaryManagement() {
    }

    public FavouriteWord fav = new FavouriteWord();
    public RecentWord rc = new RecentWord();

    public void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        for (int i = 0; i < number; i++) {
            String engword = scanner.nextLine().toLowerCase();
            String explain = scanner.nextLine().toLowerCase();
            Word word = new Word(engword, explain);
            listWord.add(word);
        }
    }

    public void insertFavouriteWordFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        for (int i = 0; i < number; i++) {
            String engword = scanner.nextLine().toLowerCase();
            favoriteWord.add(engword);
        }
    }

    public static void insertFromFile() {
        try {
            String content = readFile("Dictionary/src/main/resources/data/dictionary.txt", Charset.defaultCharset());
            String[] words = content.split("@");
            for (String word : words) {
                String[] result = word.split("\r?\n", 2);
                if (result.length > 1) {
                    String wordExplain = result[1];
                    String wordTarget = "";
                    String wordSpelling = "";
                    if (result[0].contains("/")) {
                        wordTarget = result[0].substring(0, result[0].indexOf("/"));
                        //Cắt từ đầu đến kí tự /
                        wordSpelling = result[0].substring(result[0].indexOf("/"));
                        //Từ kí tự / đến cuối
                    } else {
                        wordTarget = result[0];
                        wordSpelling = "";
                    }
                    listWord.add(new Word(wordTarget.trim(), wordSpelling.trim(), wordExplain.trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Word formatStringtoWord(String content) {
        String[] result = content.split("\r?\n", 2);
        System.out.println(result[0]);
        String wordExplain;
        String wordTarget = "";
        String wordSpelling = "";
        if (result.length > 1) {
            wordExplain = result[1];
            System.out.println(wordExplain);

            if (result[0].contains("/")) {
                wordTarget = result[0].substring(0, result[0].indexOf("/"));
                //Cắt từ đầu đến kí tự /
                wordSpelling = result[0].substring(result[0].indexOf("/"));
                //Từ kí tự / đến cuối
            } else {
                wordTarget = result[0];
                wordSpelling = "";
            }
        }  else {
            String[] split = result[0].split("\t", 2);
            wordTarget = split[0];
            wordExplain = split[1];
        }
        return new Word(wordTarget.trim(), wordSpelling.trim(), wordExplain.trim());
    }

    public static void exportToFile() {
        try {
            String content = "";
            for (Word word : listWord) {
                content += formatWordinDic(word);
            }
            Files.write(Paths.get("src\\main\\resources\\data\\dictionary.txt"), content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void addWord(String wordTarget, String wordExplain) {
        Word newWord = new Word(wordTarget, wordExplain);
        listWord.add(newWord);
    }

    public static Word dictionaryLookup(String s) {
        Word w1 = new Word();
        w1.setWordTarget(s);
        Word w2 = new Word();
        w2.setWordTarget(s + "a");
        //tao day con tu s den s+"a" bang subset
        TreeSet<Word> chat = (TreeSet<Word>) listWord.subSet(w1, w2);
        //Tìm kiếm bằng cách chặt các dãy con
        Iterator<Word> i = chat.iterator();
        if (i.hasNext()) {
            Word findout = i.next();
            if (findout.getWordTarget().equals(s)) return findout;
        }
        Word notExist = new Word(s, "", "");
        return notExist;
    }

    public static boolean isInDictionary(String s) {
        Word notExist = new Word(s, "", "");
        if (dictionaryLookup(s).equals(notExist)) return false;
        return true;
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

    public static String readFile(String path, Charset encoding) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path, encoding))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }


    public static String formatWordinDic(Word word) {
        String wordEntry = "@" + word.getWordTarget() + "\t" + word.getWordSpelling() + "\n" + word.getWordExplain() + "\n";
        return wordEntry;
    }

    public static void returnToDefault() {
        String sourceFilePath = "src\\main\\resources\\data\\dictionaryDefault.txt";
        String destinationFilePath = "src\\main\\resources\\data\\dictionary.txt";

        try {
            // Đọc nội dung từ file nguồn
            FileInputStream fis = new FileInputStream(sourceFilePath);
            byte[] sourceData = new byte[fis.available()];
            fis.read(sourceData);
            fis.close();

            // Xóa nội dung trong file đích
            FileOutputStream fos = new FileOutputStream(destinationFilePath, false);
            fos.write(new byte[0]);
            fos.close();

            // Ghi nội dung đã đọc từ file nguồn vào file đích
            fos = new FileOutputStream(destinationFilePath, true);
            fos.write(sourceData);
            fos.close();

            System.out.println("Đã copy thành công và xóa hết nội dung file đích.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int editDistance(String a, String b)
    {
        int n = a.length();
        int m = b.length();
        int[][] f = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) f[i][0] = i;
        for (int i = 0; i <= m; i++) f[0][i] = i;
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= m; j++) {
                f[i][j] = Math.min(f[i - 1][j], f[i][j - 1]) + 1;
                if (a.charAt(i - 1) == b.charAt(j - 1)) f[i][j] = Math.min(f[i][j], f[i - 1][j - 1]);
                else f[i][j] = Math.min(f[i][j], f[i - 1][j - 1] + 1);
            }
        return f[n][m];
    }
    public static String formatWord(String s) {
        s = s.toLowerCase();
        s = s.trim();
        s = s.replaceAll("\\s+", " ");
        return s;
    }
    public static String autoCorrect(String word)
    {
        word = formatWord(word);
        if (!DictionaryCommandline.dictionarySearcher(word).isEmpty()) {
            return DictionaryCommandline.dictionarySearcher(word).get(0).getWordTarget();

        }
        for (Word w : listWord)
        {
            if( w.getWordTarget().length() - word.length() >= 1 && w.getWordTarget().length() - word.length() <= -1)
                continue;
            int d = editDistance(word, w.getWordTarget());
            if ( d == 1 )
                return w.getWordTarget();
        }
        return "Not found";
    }

    /* public static void clear(String file) {
         String filePath = "src/main/resources/data/" + file + ".txt";
         try {
             FileWriter writer = new FileWriter(filePath);
             writer.write(""); // Ghi một chuỗi rỗng để xóa dữ liệu
             writer.close();
             System.out.println("Dữ liệu đã được xóa khỏi tệp tin.");
         } catch (IOException e) {
             System.out.println("Đã xảy ra lỗi khi xóa dữ liệu: " + e.getMessage());
         }
     }
     public static void reset() throws IOException {
         clear("data");
         clear("recentword");
     }
     public static void exportToFile() throws IOException {
         clear("data");
         clear("recentword");
         dictionaryExportToFileRecentWord();
         dictionaryExportToFile();
     }*/
    public static void insertData() {
        insertFromFile();
        RecentWord recent = new RecentWord();
        recent.insertFromFile();
        FavouriteWord fav = new FavouriteWord();
        fav.insertFromFile();
    }

    public static void exportDataToFile() {
        exportToFile();
        RecentWord recent = new RecentWord();
        recent.exportToFile();
        FavouriteWord fav = new FavouriteWord();
        fav.exportToFile();
    }

    public static void main(String[] args) throws IOException {
        insertData();
        System.out.println(autoCorrect("honeye"));
    }

}
