package main.database;

import main.database.graph.DeviceFetch;
import main.database.graph.LatencyFetch;

public class SampleUse {
    /**
     * How to use create by getInstance
     * Avg [1,2,3]
     * DateTime [1,2,3]
     * arrays are correspond each other.
     */
    public static void main(String[] args) {
        LatencyFetch lf = LatencyFetch.getInstance();
        lf.fetch(1);

        DeviceFetch df = DeviceFetch.getInstance();
        df.fetch(1);

        System.out.println(df.getDevices());
        System.out.println(df.getTime());
    }
}
