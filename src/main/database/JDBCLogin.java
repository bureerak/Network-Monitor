package main.database;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;

public class JDBCLogin {

    /**
     *
     * @param username by input
     * @param password by input
     * @return void
     */
    public static boolean loginJDBC(String username, char[] password){
        String sql = "SELECT password FROM users_account WHERE username = ?";
        try (Connection connection = DBCP.getConnection()) {
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
