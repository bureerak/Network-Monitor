package main.NetworkTools;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class NetworkScanner {
    private static final int IP_SCAN_TIMEOUT = 350;
    private static final int IP_SCAN_THREADS = 35;

    public static ArrayList<String[]> scan(String ip) {
        String startIP = ip.substring(0, ip.indexOf("-"));
        String endIP = ip.substring(ip.indexOf("-") + 1);
        if (IPValidator.isValidIPv4(startIP) && IPValidator.isValidIPv4(endIP)) {
            return doScan(IPCalculator.toBinary(startIP), IPCalculator.toBinary(endIP));
        } else {
            System.out.println("Invalid Input IP");
        }
        return null;
    }

    private static ArrayList<String[]> doScan(String[] startIP, String[] endIP) {
        ArrayList<String[]> results = new ArrayList<>();
        String currentIP = IPCalculator.toDecimal(startIP);
        int ipRange = IPCalculator.calcUsableIP(startIP, endIP);

        final ExecutorService scanExecutor = Executors.newFixedThreadPool(IP_SCAN_THREADS);
        final List<Future<String[]>> scanList = new ArrayList<>();
        for (int i = 0; i < ipRange; i++) {
            scanList.add(isReachable(scanExecutor, currentIP));
            currentIP = nextIP(currentIP);
        }
        scanExecutor.shutdown();

        for (final Future<String[]> f : scanList) {
            try {
                if (Integer.parseInt(f.get()[1]) >= 0) {
                    results.add(new String[]{f.get()[0], f.get()[2]});
                }
            } catch (Exception ignored) { }
        }

        return results;
    }

    private static Future<String[]> isReachable(final ExecutorService es, final String ip) {
        return es.submit(new Callable<String[]>() {
            @Override public String[] call() {
                String[] results = new String[3];
                results[0] = ip;
                results[1] = String.valueOf(Ping.runOnce(ip, IP_SCAN_TIMEOUT));
                results[2] = "";
                try {
                    results[2] = InetAddress.getByName(ip).getHostName();
                } catch (Exception ignored) { }
                return results;
            }
        });
    }

    private static String nextIP(String ip) {
        return nextIP(ip.split("[.]"));
    }

    private static String nextIP(String[] ip) {
        int[] ipInt = {Integer.parseInt(ip[0]), Integer.parseInt(ip[1]), Integer.parseInt(ip[2]), Integer.parseInt(ip[3])};
        if (ipInt[3] + 1 > 255) {
            if (ipInt[2] + 1 > 255) {
                if (ipInt[1] + 1 > 255) {
                    if (ipInt[0] + 1 > 255) {
                        ipInt[0] = 255; ipInt[1] = 255; ipInt[2] = 255; ipInt[3] = 255;
                    } else {
                        ipInt[0]++; ipInt[1] = 0; ipInt[2] = 0; ipInt[3] = 1;
                    }
                } else {
                    ipInt[1]++; ipInt[2] = 0; ipInt[3] = 1;
                }
            } else {
                ipInt[2]++; ipInt[3] = 1;
            }
        } else {
            ipInt[3]++;
        }

        return ipInt[0] + "." + ipInt[1] + "." + ipInt[2] + "." + ipInt[3];
    }

    public static void main(String[] args) {
        long timeUsed =  System.currentTimeMillis();
        int reachableCount = 0;
        ArrayList<String[]> results = scan("192.168.3.1-192.168.3.150");
        if (results != null) {
            Iterator scanResults = results.iterator();
            while (scanResults.hasNext()) {
                String[] temp = (String[]) scanResults.next();
                System.out.println(temp[0] + " [" + temp[1] + "] is reachable");
                reachableCount++;
            }
            timeUsed = System.currentTimeMillis() - timeUsed;
            System.out.println();
            System.out.println("Reachable count: " + reachableCount);
            System.out.println("Time Used: " + timeUsed + " ms.");
        }
    }
}
