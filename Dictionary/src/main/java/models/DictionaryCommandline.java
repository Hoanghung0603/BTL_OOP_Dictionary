package models;

import java.io.*;
import java.nio.*;
import java.util.*;
import java.nio.charset.Charset;
public class DictionaryCommandline extends DictionaryManagement {

    public DictionaryCommandline() {}

    public void dictionaryBasic()
    {
        System.out.println("Nhap vao so tu va danh sach tu dien:");
        insertFromCommandline();
        showAllWords();
    }

    public static void insertFromFile1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/data/data.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 2) {
                    Word tmp = new Word(parts[0], parts[1].trim());
                    listWord.add(tmp);
                } else {
                    System.out.println("ignoring line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showAllWords() {
        String[] header = {"No", "English", "Vietnamese"};
        System.out.println(String.format("%-5s| %-15s| %s", header[0], header[1], header[2]));
        int stt = 0;
        for (Word w : listWord)
        {
            stt++;
            // %-10d: Số nguyên định dạng 10, - căn trái.
            // %-15s: String định dạng 15, - căn trái.
            //%s String
            System.out.printf("%-5d| %-15s| %s\n", stt, w.getWordTarget(), w.getWordExplain());
        }
    }

    public static Word dictionaryLookup(String s) {
        Word w1 = new Word();
        w1.setWordTarget(s);
        Word w2 = new Word();
        w2.setWordTarget(s+"a");
        //tao day con tu s den s+"a" bang subset
        TreeSet<Word> chat = (TreeSet<Word>) listWord.subSet(w1,w2);
        //Tìm kiếm bằng cách chặt các dãy con
        Iterator<Word> i = chat.iterator();
        if (i.hasNext())
        {
            Word findout = i.next();
            if(findout.getWordTarget().equals(s)) return findout;
        }
        Word notExist = new Word(s, "","This word is not already existed");
        return notExist;
    }
    public static boolean dictionaryDelete(String s) {
        Word w1 = new Word();
        w1.setWordTarget(s);
        Word w2 = new Word();
        w2.setWordTarget(s + "a");
        TreeSet<Word> chat = (TreeSet<Word>) listWord.subSet(w1,w2);
        Iterator<Word> i = chat.iterator();
        if (i.hasNext())
        {
            Word findout = i.next();
            if(findout.getWordTarget().equals(s)) {
                listWord.remove(w1);
                return true;
            }
        }
        return false;
    }


    public static ArrayList<Word> dictionarySearcher(String prefix) {
        Word w1 = new Word();
        w1.setWordTarget(prefix);
        Word w2 = new Word();
        w2.setWordTarget(prefix + "zzzzzzzzzz");
        TreeSet<Word> chat = (TreeSet<Word>) listWord.subSet(w1,w2);
        Iterator<Word> i = chat.iterator();
        ArrayList<Word> list = new ArrayList<Word>();
        while (i.hasNext())
        {
            Word findout = i.next();
            if(findout.getWordTarget().startsWith(prefix)) list.add(findout);
        }
        return list;
    }
    public static void printList(ArrayList<Word> list)
    {
        for (Word w : list)
        {
            System.out.println(w.getWordTarget());
        }
    }
    public static void insertFromFile() {
        try {
            String content = readFile("src\\main\\resources\\data\\datadic.txt", Charset.defaultCharset());
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


    public static void dictionaryAdvanced( ) throws IOException
    {
        intro();
        DictionaryCommandline Ob = new DictionaryCommandline();
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while (!exit)
        {
            int command = sc.nextInt();
            sc.nextLine();
            switch (command)
            {
                case 0 :
                    //dictionaryExportToFile();
                    exit = true;
                    break;
                case 1 :
                    addWordFromCommand();
                    break;
                case 2 :
                    deleteWordFromCommand();
                    break;
                case 3 :
                    editWordFromCommand();
                    break;
                case 4 :
                    showAllWords();
                    break;
                case 5 :
                    lookupWordFromCommand();
                    break;
                case 6 :
                    printDictionarySearcher();
                    break;
                case 7 :
                    exit = true;
                    break;
                case 8 :
                    //insertFromFile();
                    insertFromFile1();
                    break;
                case 9 :
                    dictionaryExportToFile();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + command);
            }

        }
    }


    public static void main (String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);
    String s = scanner.nextLine();
    System.out.println(s);
    insertFromFile();
    insertFromFile1();
    printList(dictionarySearcher(s));


}

}

