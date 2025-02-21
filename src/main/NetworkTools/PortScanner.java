package main.NetworkTools;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class PortScanner {
    private static final int PORT_SCAN_TIMEOUT = 150;
    private static final int PORT_SCAN_THREADS = 20;

    public static ArrayList<int[]> scan(String ip, String port) {
        try {
            int startPort = Integer.parseInt(port.substring(0, port.indexOf('-')));
            int endPort = Integer.parseInt(port.substring(port.indexOf('-') + 1));
            if (IPValidator.isValidIPv4(ip) && IPValidator.isValidPort(startPort, endPort)) {
                return doScan(ip, startPort, endPort);
            } else {
                System.out.println("Invalid IP");
            }
        } catch (Exception ignored) {
            System.out.println("Invalid port number");
        }
        return null;
    }

    private static ArrayList<int[]> doScan(String ip, int startPort, int endPort) {
        ArrayList<int[]> result = new ArrayList<>();
        final ExecutorService es = Executors.newFixedThreadPool(PORT_SCAN_THREADS);
        final List<Future<int[]>> futures = new ArrayList<>();
        for (int i = startPort; i <= endPort; i++) {
            futures.add(isPortOpen(es, ip, i));
        }
        es.shutdown();
        for (final Future<int[]> f : futures) {
            try {
                result.add(f.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private static Future<int[]> isPortOpen(final ExecutorService es, final String ip, final int port) {
        return es.submit(new Callable<int[]>() {
            @Override public int[] call() {
                int[] result = {port, 0};
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), PORT_SCAN_TIMEOUT);
                    socket.close();
                    result[1] = 1;
                } catch (Exception ignored) {
                } finally {
                    return result;
                }
            }
        });
    }

    /*public static void main(String[] args) {
        long timeUsed =  System.currentTimeMillis();
        int openCount = 0;
        ArrayList<int[]> results = scan("192.168.3.249", "2280-2289");
        if (results != null) {
            Iterator scanResults = results.iterator();
            while (scanResults.hasNext()) {
                int[] temp = (int[]) scanResults.next();
                if (temp[1] == 1) {
                    openCount++;
                }
                System.out.println("Port " + temp[0] + ": " + (temp[1] == 1 ? "Open" : "Close"));
            }
            timeUsed = System.currentTimeMillis() - timeUsed;
            System.out.println();
            System.out.println("Total open ports: " + openCount);
            System.out.println("Time Used: " + timeUsed + " ms.");
        }
    }*/
}
