package main.database;

import io.github.cdimascio.dotenv.Dotenv;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;

public class JDBCLogin {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String URL = dotenv.get("DB_URL");
    private static final String DB_user = dotenv.get("DB_USER");
    private static final String DB_pass = dotenv.get("DB_PASS");

    public static boolean loginJDBC(String username, char[] password){
        String sql = "SELECT password FROM users_account WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(URL,DB_user,DB_pass)) {
            System.out.println("Connection Established...");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            ResultSet res = statement.executeQuery();
            if (  res.next() && BCrypt.checkpw( String.valueOf(password), res.getString("password")) ) {
                System.out.print(username);
                System.out.println(" logged in");
                res.close(); statement.close();
                return true;
            } else {
                System.out.println("login failed");
            }
            res.close(); statement.close();
        } catch ( Exception e ) {
            System.out.println("Internal Error");
        } finally {
            Arrays.fill(password,'\0');
        }
        return false;
    }
}
