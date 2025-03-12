package main.NetworkScanner;

import main.NetworkTools.*;
import main.database.GraphModel;
import main.database.Latency;

import java.util.ArrayList;

public class ScannerProcess extends Thread implements DeviceDisplay, PortDisplay {
    private static int portScanning = 0;
    String ip;
    String port;

    private final GraphModel latency;

    public ScannerProcess(String ip, String port) {
        this.ip = ip;
        this.port = port;

        this.latency = new Latency();
        latency.setIntervalDT();
    }

    @Override
    public void run() {
        DeviceScanner scanner = new DeviceScanner();
        scanner.scan(ip, this);
    }

    private int getLatency(String ip) {
        int latency = 0;
        for (int i = 0; i <= 3; i++) {
            int responseTime = Ping.runOnce(ip);
            if (i != 0) {
                if (responseTime >= 0)
                    latency += responseTime;
                else
                    return -1;
            }
        }

        return latency / 3;
    }

    private void scanPort(String ip) {
        new Thread(() -> {
            addScanningPort();
            PortScanner portScanner = new PortScanner();
            portScanner.scan(ip, port, this);
            removeScanningPort();
        }).start();
    }

    public static void addScanningPort() {
        portScanning++;
    }

    public static void removeScanningPort() {
        portScanning--;
    }

    public static int getScanningPort() {
        return portScanning;
    }

    @Override
    public void addDevice(String ip, String mac, String hostname) {
        //INSERT TO DATABASE
        int latency = getLatency(ip);
        scanPort(ip);
        ( (Latency) this.latency).insertData( mac,latency );
        System.out.println(hostname +" | "+ mac + " | " + ip + " | " + latency + " ms");

    }

    @Override
    public void setPort(String ip, ArrayList<Integer> port) {
       //INSERT TO DATABASE WITH IP IS A KEY

    }

    @Override
    public void updateProgress(int scanned) {

    }

}
