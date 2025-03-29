package main.NetworkTools;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import main.exception.InvalidIPException;

public class DeviceScanner {
    private static final int IP_SCAN_TIMEOUT = 350;
    private static final int IP_SCAN_THREADS = 35;

    public void scan(String ip, DeviceDisplay display) {
        String startIP = "";
        String endIP = "";
        if (ip.indexOf("-") > 0) {
            startIP = ip.substring(0, ip.indexOf("-"));
            endIP = ip.substring(ip.indexOf("-") + 1);
        } else {
            startIP = ip;
            endIP = ip;
        }

        if (IPValidator.isValidIPv4(startIP) && IPValidator.isValidIPv4(endIP)) {
            doScan(IPCalculator.toBinary(startIP), IPCalculator.toBinary(endIP), display);
        } else {
            throw new InvalidIPException();
        }
    }

    private void doScan(String[] startIP, String[] endIP, DeviceDisplay display) {
        String currentIP = IPCalculator.toDecimal(startIP);
        int ipRange = IPCalculator.calcUsableIP(startIP, endIP);
        final ExecutorService scanExecutor = Executors.newFixedThreadPool(IP_SCAN_THREADS);
        final List<Future<String[]>> scanList = new ArrayList<>();
        for (int i = 0; i < ipRange; i++) {
            scanList.add(isReachable(scanExecutor, currentIP));
            currentIP = DeviceScanner.nextIP(currentIP);
        }
        scanExecutor.shutdown();

        for (final Future<String[]> f : scanList) {
            try {
                if (Integer.parseInt(f.get()[1]) >= 0) {
                    display.addDevice(f.get()[0], f.get()[2], f.get()[3]);
                }
            } catch (Exception ignored) { }
        }
    }

    private Future<String[]> isReachable(final ExecutorService es, final String ip) {
        return es.submit(new Callable<String[]>() {
            @Override public String[] call() {
                String[] results = new String[4];
                results[0] = ip;
                results[1] = String.valueOf(Ping.runOnce(ip, IP_SCAN_TIMEOUT));
                results[2] = "";
                results[3] = "";
                if (Integer.parseInt(results[1]) >= 0) {
                    results[2] = InternalNetwork.getMACAddress(ip);
                    try {
                        results[3] += InetAddress.getByName(ip).getHostName();
                    } catch (IOException ignored) { }
                }
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
}
