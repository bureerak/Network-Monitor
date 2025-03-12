package main.database;


import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class GraphModel {
    private LocalDateTime now;
    public void setIntervalDT() {
        now = LocalDateTime.now();
    }
    public LocalDateTime getIntervalDT(){
        return now;
    }
    public abstract void fetchData();
    public abstract void insertData(ArrayList data);
}
