package main.database;

import main.database.graph.LatencyFetch;

public class OhmLook {
    /**
     * How to use create by getInstance
     * Avg [1,2,3]
     * DateTime [1,2,3]
     * arrays are correspond each other.
     */
    public static void main(String[] args) {
        LatencyFetch lf = LatencyFetch.getInstance();
        lf.fetch();
        System.out.println(lf.getAvg());
        System.out.println(lf.getDateTimes());
    }
}
