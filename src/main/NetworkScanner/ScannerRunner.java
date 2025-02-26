package main.NetworkScanner;

import java.util.Timer;
import java.util.TimerTask;

public class ScannerRunner {
    private static final String COMMON_PORTS = "7, 19, 20-25, 42-1000, 1025-1985, 2000-2100, 2222, 2302, 2483-2484, 2745, 2967, 3050-3785, 4333-4899, 5000-5999, 6000";

    public ScannerRunner(String ip, StatusDisplay display) {
        this(ip, 5, display);
    }

    public ScannerRunner(String ip, int scanInterval, StatusDisplay display) {
        this(ip, COMMON_PORTS, scanInterval, display);
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
        display.setScanning(scanning);
    }
}
