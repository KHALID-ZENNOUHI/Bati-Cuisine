package BatiCuisine.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    public static DataBaseConnection instance;
    private final Connection connection;
    private static final String user = "BatiCuisine";
    private static final String url = "jdbc:postgresql://localhost:5432/BatiCuisine";
    private static final String password = "";

    private DataBaseConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.connection = conn;
    }

    public static DataBaseConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) instance = new DataBaseConnection();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        instance.getConnection().close();
    }


}
