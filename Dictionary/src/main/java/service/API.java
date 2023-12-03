package service;

import okhttp3.OkHttpClient;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Scanner;

public class API {

    public static boolean isInternetAvailable() {
        try {
            URL url = new URL("https://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (Exception e) {
           return false;
        }
    }
    protected static String subscriptionKey;
    protected static String serviceRegion;

}


