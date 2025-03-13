package main.database.graph;

import main.database.DBCP;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class LatencyFetch implements DataFetcher {
    private ArrayList<Double> avg;
    private ArrayList<LocalDateTime> dateTimes;
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
    public ArrayList<LocalDateTime> getDateTimes() {
        return dateTimes;
    }

    @Override
    public void fetch() {
        String sql = "SELECT AVG(latency) as Latency,datetime FROM `latency_info` GROUP BY datetime ORDER BY datetime ASC;";
        try (Connection conn = DBCP.getConnection();
             Statement stm = conn.createStatement();) {
            ResultSet rs = stm.executeQuery(sql);
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

