package main.NetworkTools;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PortScanner {
    private static final int PORT_SCAN_TIMEOUT = 100;
    private static final int PORT_SCAN_THREADS = 250;

    public void scan(String ip, String port, PortDisplay display) {
        int startPort = 0;
        int endPort = 0;
        try {
            if (port.indexOf("-") > 0) {
                startPort = Integer.parseInt(port.substring(0, port.indexOf('-')));
                endPort = Integer.parseInt(port.substring(port.indexOf('-') + 1));
            } else {
                startPort = Integer.parseInt(port);
                endPort = Integer.parseInt(port);
            }

            if (IPValidator.isValidIPv4(ip) && IPValidator.isValidPort(startPort, endPort)) {
                doScan(ip, startPort, endPort, display);
            } else {
                System.out.println("Invalid IP/Port");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            System.out.println("Invalid port number");
        }
    }

    private void doScan(String ip, int startPort, int endPort, PortDisplay display) {
        ArrayList<Integer> results = new ArrayList<>();
        final ExecutorService scanExecutor = Executors.newFixedThreadPool(PORT_SCAN_THREADS);
        final List<Future<int[]>> scanList = new ArrayList<>();
        for (int i = startPort; i <= endPort; i++) {
            scanList.add(isPortOpen(scanExecutor, ip, i));
        }
        scanExecutor.shutdown();
        for (final Future<int[]> f : scanList) {
            try {
                if (f.get()[1] == 1) {
                    results.add(f.get()[0]);
                }
            } catch (InterruptedException | ExecutionException ignored) { }
        }
        display.setPort(ip, results);
    }

    private Future<int[]> isPortOpen(final ExecutorService es, final String ip, final int port) {
        return es.submit(new Callable<int[]>() {
            @Override public int[] call() {
                int[] result = {port, 0};
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), PortScanner.PORT_SCAN_TIMEOUT);
                    socket.close();
                    result[1] = 1;
                } catch (Exception ignored) {
                } finally {
                    return result;
                }
            }
        });
    }
}
