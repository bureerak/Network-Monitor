package main.database.graph;

import main.database.DBCP;
import main.database.UserPreference;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class LatencyFetch implements DataFetcher {
    private ArrayList<Double> avg;
    private ArrayList<LocalTime> dateTimes;
    private static LatencyFetch instance;

    private LatencyFetch(){
        avg = new ArrayList<>();
        dateTimes = new ArrayList<>();
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
    public ArrayList<LocalTime> getDateTimes() {
        return dateTimes;
    }

    @Override
    public void fetch() {
        String sql = "SELECT AVG(latency) as Latency,datetime FROM `latency_info` WHERE profile_id = ? GROUP BY datetime ORDER BY datetime ASC;";
        try (Connection conn = DBCP.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql);) {
            stm.setInt(1, UserPreference.getProfileID());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                avg.add(rs.getDouble("Latency"));
                dateTimes.add(rs.getTimestamp("datetime").toLocalDateTime().toLocalTime());
            }
            rs.close();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    @Override
    public void fetchRange(LocalDateTime start, LocalDateTime stop) {

    }

}

