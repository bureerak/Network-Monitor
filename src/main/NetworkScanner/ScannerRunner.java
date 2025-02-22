package main.NetworkScanner;

import java.util.Timer;
import java.util.TimerTask;

public class ScannerRunner {
    private boolean isScanning = false;

    public ScannerRunner(String ip, StatusDisplay display) {
        this(ip, "1-65535", 5, display);
    }

    public ScannerRunner(String ip, String port, int scanInterval, StatusDisplay display) {
        display.setInterval(scanInterval);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isScanning = true;
                display.setScanning(true);
                ScannerProcess scan = new ScannerProcess(ip, port);
                scan.start();
                while (scan.isAlive()) { }
                isScanning = false;
                display.setScanning(false);
            }
        }, 0, scanInterval * 60000);
    }
}
