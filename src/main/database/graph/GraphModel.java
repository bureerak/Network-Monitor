package main.database.graph;


import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class GraphModel {
    private static LocalDateTime now;
    public void setIntervalDT() {
        now = LocalDateTime.now();
    }
    public LocalDateTime getIntervalDT(){
        return now;
    }
    public abstract void insertData(ArrayList<Object> data);
}
