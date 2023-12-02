package models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static models.Dictionary.recentWord;


public class RecentWord implements File {
    @Override
    public void insertFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/data/recentword.txt"))) {
            String line;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\t");
                if(i <= 10) {
                    i++;
                    recentWord.add(parts[0]);
                }
                else System.out.println("ignoring line: " + line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exportToFile() {
        try {
        String content2 ="";
        for (String word : recentWord) {
            content2 += word + "\n";
        }
            Files.write(Paths.get("src\\main\\resources\\data\\recentword.txt"), content2.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

