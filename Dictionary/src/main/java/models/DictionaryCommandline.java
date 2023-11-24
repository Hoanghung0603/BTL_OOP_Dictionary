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
                    insertFromFileAdded();
                    break;
                case 9 :
                    dictionaryExportToFile();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + command);
            }

        }
    }




}

