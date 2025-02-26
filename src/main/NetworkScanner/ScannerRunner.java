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
                setScanning(true, display);
                ScannerProcess scan = new ScannerProcess(ip, port);
                scan.start();
                while (scan.isAlive() || ScannerProcess.getScanningPort() > 0) { }
                setScanning(false, display);
            }
        }, 0, scanInterval * 60000);
    }

    public void setScanning(boolean scanning, StatusDisplay display) {
        isScanning = scanning;
        display.setScanning(scanning);
    }
}
