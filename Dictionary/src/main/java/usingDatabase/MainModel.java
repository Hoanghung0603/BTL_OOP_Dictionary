package usingDatabase;

import models.Word;

import java.io.UnsupportedEncodingException;
import java.sql.*;

public class MainModel {
            static String jdbcUrl = "jdbc:sqlite:src\\main\\java\\usingDatabase\\database.db";
            static Connection connection;
            static Statement statement;

            static {
                try {
                    connection = DriverManager.getConnection(jdbcUrl);
                    statement = connection.createStatement();
                } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static String getWordleWord() throws SQLException {
        String sql = "SELECT * FROM wordle_wordList ORDER BY RANDOM() LIMIT 1;";
        return statement.executeQuery(sql).getString("Word");
    }

    public static void closeConnection() throws SQLException {
        statement.close();
        connection.close();
    }

    public static boolean checkWordleWordExists(String word) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM wordle_wordList WHERE Word LIKE '" + word + "'");
        int count = resultSet.getInt("count");
        return count > 0;
    }

    public static void main(String[] args) throws SQLException, UnsupportedEncodingException {
     System.out.println(checkWordleWordExists("table"));
    }
}