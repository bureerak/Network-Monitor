package main.database.graph;

import main.database.DBCP;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class DeviceFetch implements DataFetcher{
    private ArrayList<Integer> devices  = new ArrayList<>() ;
    private ArrayList<LocalTime> time  = new ArrayList<>() ;
    private static DeviceFetch deviceFetch;

    private DeviceFetch(){
    }
    public static DeviceFetch getInstance() {
        if (deviceFetch == null) {
            deviceFetch = new DeviceFetch();
        }
        return deviceFetch;
    }

    public ArrayList<Integer> getDevices(){
        return devices;
    }
    public ArrayList<LocalTime> getTime(){
        return time;
    }

    @Override
    public void fetch(int profile_id) {
        devices = new ArrayList<>();
        time = new ArrayList<>();
        String sql = "SELECT COUNT(mac) AS Device , datetime FROM device_info WHERE profile_id = ? GROUP BY datetime";
        try (Connection con = DBCP.getConnection(); PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, profile_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                devices.add(rs.getInt("Device"));
                time.add(rs.getTimestamp("datetime").toLocalDateTime().toLocalTime());
            }
            rs.close();
        } catch (SQLException s) {
            System.out.println("Fetch Error");
            s.printStackTrace();
        }

    }

    @Override
    public void fetchRange(LocalDateTime start, LocalDateTime stop) {

    }
}
