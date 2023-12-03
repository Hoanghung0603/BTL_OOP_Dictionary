package models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static models.Dictionary.favoriteWord;
import static models.Dictionary.recentWord;

public class FavouriteWord implements File {
    @Override
    public void insertFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/data/favourite.txt"))) {
            String line;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\t");
                if(i <= 10) {
                    i++;
                    favoriteWord.add(parts[0]);
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
            String content ="";
            for (String word : favoriteWord) {
                content += word + "\n";
            }
            Files.write(Paths.get("src\\main\\resources\\data\\favourite.txt"), content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
