package main.database;

import main.database.graph.DeviceFetch;
import main.database.graph.LatencyFetch;
import main.database.graph.PortFetch;

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

        PortFetch pf = PortFetch.getInstance();
        pf.fetch(3);

        System.out.println(pf.getCount());
        System.out.println(pf.getDateTime());
    }
}
