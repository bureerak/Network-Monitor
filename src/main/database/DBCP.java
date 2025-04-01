package main.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCP {
    private static final HikariDataSource dataSource;
    private static final Dotenv dotenv;
    static {
        HikariConfig config = new HikariConfig();
        dotenv = Dotenv.configure()
                .directory("./")  // หรือระบุ path ที่ชัดเจน
                .filename(".env") // ชื่อไฟล์
                .load();
        config.setJdbcUrl(dotenv.get("DB_URL"));
        config.setUsername(dotenv.get("DB_USER"));
        config.setPassword(dotenv.get("DB_PASS"));
        config.setMaximumPoolSize(6);
        config.setMinimumIdle(2);
        config.setIdleTimeout(300000);
        config.setMaxLifetime(600000);
        dataSource = new HikariDataSource(config);
    }

    /**
     *
     * @return Connection like DriverManger
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closePool() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

}
