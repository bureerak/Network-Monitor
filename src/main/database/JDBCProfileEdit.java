package main.database;

import main.ui.ProfileEditorView;

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
     * @param e array to store result form query
     * @return void
     */
    public static ArrayList<String> refresh(ArrayList<String> e) {
        String sql = "SELECT profile_name, used FROM profile";
        try ( Connection conn = DBCP.getConnection();
             Statement stm = conn.createStatement() ) {
            ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString("profile_name");
                boolean used = res.getBoolean("used");
                name = used ? name + " Active" : name + " Idle";
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
             PreparedStatement preState = conn.prepareStatement(sql)) {
            preState.setString(1,profile);
            preState.executeUpdate();
        } catch (SQLException s) {
            System.out.println("Delete Failed");
            s.printStackTrace();
        }
    }

    /**
     *
     * @param name the profile to be select
     * @return boolean if true = can select this profile, false = can't select this profile
     */
    public static boolean selectProfile(String name) {
        String sql = "SELECT used,profile_id FROM profile WHERE profile_name = ?";
        String sqlSetStatus = "UPDATE profile SET used = 1 WHERE profile_name = ?";
        String sqlUnselect = "UPDATE profile SET used = 0 WHERE profile_name = ?";
        try (Connection conn = DBCP.getConnection();
             PreparedStatement preState = conn.prepareStatement(sql);
             PreparedStatement setStatus = conn.prepareStatement(sqlSetStatus);
             PreparedStatement statement = conn.prepareStatement(sqlUnselect)) {
            if (ProfileEditorView.getNowSelect() != null){
                statement.setString(1,ProfileEditorView.getNowSelect());
                statement.executeUpdate();
                ProfileEditorView.setProfile_id(0);
                System.out.println(ProfileEditorView.getNowSelect() + " Unselected");
            }
            preState.setString(1,name);
            ResultSet rs = preState.executeQuery();
            rs.next();
            boolean status = rs.getBoolean("used");
            int profile_id = rs.getInt("profile_id");
            if (!status) {
                ProfileEditorView.setProfile_id(profile_id);
                System.out.println("Current profile id: " + ProfileEditorView.getProfile_id());
                setStatus.setString(1,name);
                setStatus.executeUpdate();
                return true;
            }
            rs.close();
        } catch (SQLException s) {
            System.out.println("Select Error");
            s.printStackTrace();
        }
        return false;
    }

    /**
     * Unselected current profile
     */
    public static void unselectProfile() {
        if (ProfileEditorView.getNowSelect() != null) {
            String sqlUnselect = "UPDATE profile SET used = 0 WHERE profile_name = ?";
            try (Connection conn = DBCP.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sqlUnselect)) {
                statement.setString(1,ProfileEditorView.getNowSelect());
                statement.executeUpdate();
                System.out.println("Clear selected profile Success.");
            } catch (SQLException s) {
                System.out.println("Internal Error.");
                s.printStackTrace();
            }
        }
    }
}
