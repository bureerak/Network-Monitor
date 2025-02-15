package NetworkTools;

import java.io.InputStream;
import java.net.InetAddress;
import java.io.IOException;
import java.util.Scanner;

public class Traceroute {
    private static final String os = System.getProperty("os.name").toLowerCase();

    public static void trace(String address){
        String route = "";
        try {
            Process traceRt;
            if(os.contains("win")) traceRt = Runtime.getRuntime().exec("tracert " + address);
            else traceRt = Runtime.getRuntime().exec("traceroute " + address);
            Scanner s = new Scanner(traceRt.getInputStream()).useDelimiter("\\A");
            route = s.hasNext() ? s.next() : "";
        }
        catch (IOException e) {

        }
        System.out.println(route);
    }

    public static void main(String[] args) {
        trace("www.google.com");
    }
}
