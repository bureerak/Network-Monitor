package main.database.graph;

import main.database.DBCP;
import main.database.UserPreference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PortInsert extends DeviceInsert{
    @Override
    public void insertData(ArrayList<Object> data) {
        LocalDateTime now = getIntervalDT();
        String sql = "INSERT INTO port_info ( profile_id , ip , count , datetime ) VALUE ( ?,?,?,? )";
        try (Connection con = DBCP.getConnection(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1,UserPreference.getProfileID());
            stm.setString(2,(String) data.get(0));
            stm.setInt(3,(Integer) data.get(1));
            stm.setTimestamp(4, Timestamp.valueOf(now));
            stm.executeUpdate();
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }

    }

    public void insertData(String ip,ArrayList<Integer> port) {
        ArrayList<Object> array = new ArrayList<>();
        array.add(ip);
        array.add(port.size());
        insertData(array);
    }
}
