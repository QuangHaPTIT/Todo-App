package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
	private static final String DB_DRIVER = ConfigLoader.getProperty("db.driver");
    private static final String DB_URL = ConfigLoader.getProperty("db.url");
    private static final String DB_USERNAME = ConfigLoader.getProperty("db.username");
    private static final String DB_PASSWORD = ConfigLoader.getProperty("db.password");

    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Lỗi tải driver database", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

 
    public static int getMaxTodos() {
        return ConfigLoader.getIntProperty("app.max_todos");
    }

    public static boolean isDebugMode() {
        return ConfigLoader.getBooleanProperty("app.debug_mode");
    }
}
