package main.database;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class JDBCProfileEdit {

    /**
     *
     * @param profileName insert name to sql profile table
     */
    public static void addProfile(String profileName) {
        String sql = "INSERT INTO profile (profile_name, time_stamp) VALUE (?, ?)";
        try (Connection conn = DBCP.getConnection();
             PreparedStatement preState = conn.prepareStatement(sql)) {
            preState.setString(1,profileName);
            preState.setTimestamp(2,Timestamp.valueOf(LocalDateTime.now()));
            preState.executeUpdate();
        } catch (SQLException se) {
            System.out.println("Internal Error");
            se.printStackTrace();
        }
    }

    /**
     * refresh combo box
     * @param e
     * @return void
     */
    public static ArrayList<String> refresh(ArrayList<String> e) {
        String sql = "SELECT profile_name FROM profile WHERE used = 0";
        try ( Connection conn = DBCP.getConnection();
             Statement stm = conn.createStatement(); ) {
            ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString("profile_name");
                e.add(name);
            }
            res.close();
            return e;
        } catch (SQLException s) {
            System.out.println("Internal Error");
            s.printStackTrace();
        }
        return e;
    }

    /**
     * Delete profile by name
     * @param profile name to delete.
     */
    public static void deleteProfile(String profile) {
        String sql = "DELETE FROM profile WHERE profile_name = ?";
        try (Connection conn = DBCP.getConnection();
             PreparedStatement preState = conn.prepareStatement(sql);) {
            preState.setString(1,profile);
            preState.executeUpdate();
        } catch (SQLException s) {
            System.out.println("Delete Failed");
            s.printStackTrace();
        }
    }
}
