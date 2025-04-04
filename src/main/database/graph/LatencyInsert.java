package main.database.graph;

import main.database.DBCP;
import main.database.UserPreference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class LatencyInsert extends InsertModel {

    @Override
    public void insertData(ArrayList<Object> data) {
        LocalDateTime now = getIntervalDT();
        String sql = "INSERT INTO latency_info ( profile_id , mac , latency , datetime ) VALUE ( ?,?,?,? )";
        try (Connection conn = DBCP.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, UserPreference.getProfileID());
            stm.setString(2, (String) data.get(0));
            stm.setFloat(3, (Float) data.get(1));
            stm.setTimestamp(4, Timestamp.valueOf(now));
            stm.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public void insertData(String mac,float latency) {
        ArrayList<Object> array = new ArrayList<>();
        array.add(mac); array.add(latency);
        insertData(array);
    }
}
