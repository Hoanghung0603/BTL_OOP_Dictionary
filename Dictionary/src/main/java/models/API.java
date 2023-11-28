package models;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Scanner;

public class API {

    public static void main(String[] args) throws IOException {
    System.out.println(translate("hello" + '\n' + "hi", "vi", "en"));
    generateTextToSpeech("hello", "en");
    }
    
    public static String translate(String text, String target, String source) throws IOException {

        String urlStr = "https://script.google.com/macros/s/AKfycbzeEquVJh3w7YYFhULGN7oyKgRWFFh7I1CSxX8m52HeZlX7EigOXVE1KB3K2O30PliE9Q/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + target +
                "&source=" + source;

        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static String generateTextToSpeech(String text, String language) {
        try {
            String apiKey = "7ffb5027b1274e489ea8878d40494f7c";
            String apiUrl = "https://api.voicerss.org/";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            setConnectionProperties(connection);
            String data = "key=" + apiKey + "&src=" + text  ;
            if(Objects.equals(language, "Vietnamese"))  data = data + "&hl=vi-VN";
            sendDataToApi(connection, data);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                Path outputPath = Paths.get("src\\main\\resources\\data\\output.mp3");
                Files.copy(is, outputPath, StandardCopyOption.REPLACE_EXISTING);
                is.close();
                connection.disconnect();
                return outputPath.toAbsolutePath().toString();
            } else {
                System.out.println("API request failed with HTTP code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void setConnectionProperties(HttpURLConnection connection) {
        try {
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendDataToApi(HttpURLConnection connection, String data) {
        try {
            OutputStream os = connection.getOutputStream();
            os.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


