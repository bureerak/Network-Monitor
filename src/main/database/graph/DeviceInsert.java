package main.database.graph;

import main.database.DBCP;
import main.database.UserPreference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DeviceInsert extends InsertModel{
    @Override
    public void insertData(ArrayList<Object> data) {
        LocalDateTime now = getIntervalDT();
        String sql = "INSERT INTO device_info ( profile_id , mac , datetime ) VALUE ( ?,?,? )";
        try (Connection con = DBCP.getConnection(); PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, UserPreference.getProfileID());
            statement.setString(2,(String) data.get(0));
            statement.setTimestamp(3, Timestamp.valueOf(now));
            statement.executeUpdate();
        } catch (SQLException s) {
            System.out.println("Insert Failed (Device)");
            s.printStackTrace();
        }
    }

    public void insertData(String mac) {
        ArrayList<Object> array = new ArrayList<>();
        array.add(mac);
        insertData(array);
    }
}
