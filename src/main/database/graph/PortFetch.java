package main.database.graph;

import main.database.DBCP;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PortFetch implements DataFetcher{
    private ArrayList<Integer> count = new ArrayList<>();
    private ArrayList<LocalDateTime> time = new ArrayList<>();
    private static PortFetch portFetch;
    private PortFetch(){}
    public static PortFetch getInstance() {
        if (portFetch == null) {
            portFetch = new PortFetch();
        }
        return portFetch;
    }

    public ArrayList<Integer> getCount(){
        return count;
    }
    public ArrayList<LocalDateTime> getDateTime(){
        return time;
    }

    @Override
    public void fetch(int profile_id) {
        count = new ArrayList<>();
        time = new ArrayList<>();
        String sql = "SELECT SUM(count) as count , datetime FROM port_info WHERE profile_id = ? GROUP BY datetime ORDER BY datetime ASC;";
        try (Connection con = DBCP.getConnection(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1,profile_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                count.add(rs.getInt("count"));
                time.add(rs.getTimestamp("datetime").toLocalDateTime());
            }
            rs.close();
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    @Override
    public void fetchRange(int profile_id, LocalDateTime start, LocalDateTime stop) {
        count = new ArrayList<>();
        time = new ArrayList<>();
        String sql = "SELECT SUM(count) as count , datetime FROM port_info WHERE datetime BETWEEN ? AND ? AND profile_id = ? GROUP BY datetime ORDER BY datetime ASC;";
        try (Connection con = DBCP.getConnection(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setTimestamp(1, Timestamp.valueOf(start));
            stm.setTimestamp(2,Timestamp.valueOf(stop));
            stm.setInt(3,profile_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                count.add(rs.getInt("count"));
                time.add(rs.getTimestamp("datetime").toLocalDateTime());
            }
            rs.close();
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }
}
