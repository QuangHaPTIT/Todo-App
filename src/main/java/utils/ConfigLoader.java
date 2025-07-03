package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
	private static final Properties prop = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("Không tìm thấy file application.properties");
            }
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi đọc file cấu hình", e);
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(prop.getProperty(key));
    }

    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(prop.getProperty(key));
    }
}
