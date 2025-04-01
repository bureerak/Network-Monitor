package main.database.graph;

import main.database.DBCP;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class LatencyFetch implements DataFetcher {
    private ArrayList<Double> avg = new ArrayList<>();
    private ArrayList<LocalDateTime> dateTimes = new ArrayList<>();
    private static LatencyFetch instance;

    private LatencyFetch(){
    }
    public static LatencyFetch getInstance() {
        if (instance == null) {
            instance = new LatencyFetch();
        }
        return instance;
    }

    public ArrayList<Double> getAvg(){
        return avg;
    }
    public ArrayList<LocalDateTime> getDateTimes() {
        return dateTimes;
    }

    @Override
    public void fetch(int profile_id) {
        avg = new ArrayList<>();
        dateTimes = new ArrayList<>();
        String sql = "SELECT ROUND(AVG(latency), 2) as Latency,datetime FROM `latency_info` WHERE profile_id = ? GROUP BY datetime ORDER BY datetime ASC;";
        try (Connection conn = DBCP.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, profile_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                avg.add(rs.getDouble("Latency"));
                dateTimes.add(rs.getTimestamp("datetime").toLocalDateTime());
            }
            rs.close();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    @Override
    public void fetchRange(int profile_id, LocalDateTime start, LocalDateTime stop) {
        avg = new ArrayList<>();
        dateTimes = new ArrayList<>();
        String sql = "SELECT ROUND(AVG(latency), 2) as Latency,datetime FROM `latency_info` WHERE datetime BETWEEN ? AND ? AND profile_id = ? GROUP BY datetime ORDER BY datetime ASC";
        try (Connection conn = DBCP.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setTimestamp(1,Timestamp.valueOf(start));
            stm.setTimestamp(2,Timestamp.valueOf(stop));
            stm.setInt(3, profile_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                avg.add(rs.getDouble("Latency"));
                dateTimes.add(rs.getTimestamp("datetime").toLocalDateTime());
            }
            rs.close();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

}

