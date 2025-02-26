package main.NetworkTools;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

public class PortScanner {
    private static final int PORT_SCAN_TIMEOUT = 100;
    private static final int PORT_SCAN_THREADS = 250;

    public void scan(String ip, String port, PortDisplay display) {
        Set<Integer> portList;
        portList = getPorts(port);
        try {
            if (IPValidator.isValidIPv4(ip) && !portList.isEmpty()) {
                doScan(ip, portList, display);
            } else {
                System.out.println("Invalid IP/Port or Port is empty.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            System.out.println("Invalid port number");
        }
    }

    private void doScan(String ip, Set<Integer> portList, PortDisplay display) {
        ArrayList<Integer> results = new ArrayList<>();
        final ExecutorService scanExecutor = Executors.newFixedThreadPool(PORT_SCAN_THREADS);
        final List<Future<int[]>> scanList = new ArrayList<>();
        Iterator<Integer> it = portList.iterator();
        while (it.hasNext()) {
            scanList.add(isPortOpen(scanExecutor, ip, it.next()));
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

    private Set<Integer> getPorts(String port) {
        Set<Integer> portList = new HashSet<>();
        if (port.contains(",")) {
            String[] ports = port.split(",");
            for (String p : ports) {
                checkPort(p, portList);
            }
        } else {
            checkPort(port, portList);
        }

        return portList;
    }

    private void checkPort(String port, Set<Integer> portList) {
        port = port.strip();
        if (port.indexOf("-") > 0) {
            String[] split = port.split("-");
            int start = Integer.parseInt(split[0]);
            int end = Integer.parseInt(split[1]);
            if (IPValidator.isValidPort(start, end)) {
                for (int i = start; i <= end; i++) {
                    portList.add(i);
                }
            }
        } else if (! port.isBlank()) {
            try {
                int temp = Integer.parseInt(port);
                if (IPValidator.isValidPort(temp)) {
                    portList.add(temp);
                }
            } catch (Exception ignored) { }
        }
    }
}
