package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLConnects{
    private static final String HOST_NAME = "localhost";

    private static final String DB_NAME = "dictionary";

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "Hackerman_123";

    private static final String PORT = "3307";

    private static final String MYSQL_URL = String.format("jdbc:mysql://%s:%s/%s", HOST_NAME, PORT, DB_NAME);

    private static Connection connection = null;

    public ArrayList<String> word = new ArrayList<>();

    public ArrayList<String> definition = new ArrayList<>();

    /**
     * Đóng kết nối của database
     */
    private void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * đóng ResultSet rs.
     */
    private static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * đóng PreparedStatement ps.
     */
    private static void close(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Kết nối tới Database
     *
     * <p>Reference: https://stackoverflow.com/questions/2839321/connect-java-to-a-mysql-database
     */
    public void connectToDatabase() throws SQLException {
        System.out.println("Connect to database.");
        connection = DriverManager.getConnection(MYSQL_URL, USER_NAME, PASSWORD);
        System.out.println("Connected");
    }

    /**
     *  Close the Database connection.
     */
    public void close() {
        close(connection);
        System.out.println("Database disconnected!");
    }

    /**
     *
     */
    public void getAllWord() {
        final String SQL_QUERY = "SELECT * FROM dictionary";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        word.add(rs.getString(2));
                        definition.add(rs.getString(3));
                    }
                } finally {
                    rs.close();
                }
            } finally {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
