package main.database.graph;

import main.database.DBCP;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DeviceFetch implements DataFetcher{
    private ArrayList<Integer> devices  = new ArrayList<>() ;
    private ArrayList<LocalDateTime> time  = new ArrayList<>() ;
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
    public ArrayList<LocalDateTime> getDateTime(){
        return time;
    }

    @Override
    public void fetch(int profile_id) {
        devices = new ArrayList<>();
        time = new ArrayList<>();
        String sql = "SELECT COUNT(mac) AS Device , datetime FROM device_info WHERE profile_id = ? GROUP BY datetime ORDER BY datetime ASC";
        try (Connection con = DBCP.getConnection(); PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, profile_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                devices.add(rs.getInt("Device"));
                time.add(rs.getTimestamp("datetime").toLocalDateTime());
            }
            rs.close();
        } catch (SQLException s) {
            System.out.println("Fetch Error");
            s.printStackTrace();
        }

    }

    @Override
    public void fetchRange(int profile_id ,LocalDateTime start, LocalDateTime stop) {
        devices = new ArrayList<>();
        time = new ArrayList<>();
        String sql = "SELECT COUNT(mac) AS Device , datetime FROM device_info WHERE datetime BETWEEN ? AND ? AND profile_id = ? GROUP BY datetime ORDER BY datetime ASC";
        try (Connection con = DBCP.getConnection(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setTimestamp(1, Timestamp.valueOf(start));
            stm.setTimestamp(2,Timestamp.valueOf(stop));
            stm.setInt(3,profile_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                devices.add(rs.getInt("Device"));
                time.add(rs.getTimestamp("datetime").toLocalDateTime());
            }
            rs.close();
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }
}
