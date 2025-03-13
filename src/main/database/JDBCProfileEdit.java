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
        String sql = "SELECT profile_id FROM profile WHERE profile_name = ?";
        String sqlSetStatus = "UPDATE profile SET used = 1 WHERE profile_name = ?";
        try (Connection conn = DBCP.getConnection();
             PreparedStatement preState = conn.prepareStatement(sql);
             PreparedStatement setStatus = conn.prepareStatement(sqlSetStatus)) {
            if ( !UserPreference.getProfile().equals("None") ) { unselectProfile(); }
            preState.setString(1,name);
            ResultSet rs = preState.executeQuery();
            rs.next();
            int profile_id = rs.getInt("profile_id");
            if (isUsable(name)) {
                UserPreference.setProfileID(profile_id);
                System.out.print("* Current profile id: " + UserPreference.getProfileID() +" | ");
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
        if ( !UserPreference.getProfile().equals("None") ) {
            String sqlUnselect = "UPDATE profile SET used = 0 WHERE profile_name = ?";
            try (Connection conn = DBCP.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sqlUnselect)) {
                statement.setString(1,UserPreference.getProfile());
                statement.executeUpdate();
                UserPreference.setStatus(UserPreference.IDLE);
                System.out.println("* Clear selected profile Success.");
            } catch (SQLException s) {
                System.out.println("Internal Error.");
                s.printStackTrace();
            }
        }
    }

    /**
     *
     * @param name
     * @return true if profile is not occupied else false
     */
    public static boolean isUsable(String name) {
        String sql = "SELECT used FROM profile WHERE profile_name = ?";
        try ( Connection conn = DBCP.getConnection();
              PreparedStatement statement = conn.prepareStatement(sql);) {
            statement.setString(1,name);
            ResultSet rs = statement.executeQuery();
            rs.next();
            boolean used_state = rs.getBoolean("used");
            return  !used_state;
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return false;
    }
}
