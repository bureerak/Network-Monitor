package main.NetworkScanner;

import java.util.Timer;
import java.util.TimerTask;

public class ScannerRunner {
    private static final String COMMON_PORTS = "7, 19, 20-25, 42-1000, 1025-1985, 2000-2100, 2222, 2302, 2483-2484, 2745, 2967, 3050-3785, 4333-4899, 5000-5999, 6000, 6001, 6112, 6129, 6257, 6346-6347, 6379, 6500, 6566, 6588, 6665-6669, 6679,6697, 6699, 6881-6999, 7000-7001, 7199, 7648-7649, 8000, 8001, 8080-8081, 8086-8087, 8118, 8200, 8222, 8500, 8767, 8866, 9042, 9100-9103, 9119, 9800, 9898, 9999-10001, 10113-10116, 10161-10162, 11371, 12345, 13720-13721, 14567, 15118, 19226, 19638, 20000, 24800, 25565, 25575, 25999, 27015, 27017, 27374, 28960, 31337, 33434";
    private final Timer timer;
    public ScannerRunner(String ip, StatusDisplay display) {
        this(ip, 5, display);
    }

    public ScannerRunner(String ip, int scanInterval, StatusDisplay display) {
        this(ip, COMMON_PORTS, scanInterval, display);
    }

    public ScannerRunner(String ip, String port, int scanInterval, StatusDisplay display) {
        if (port.equals(COMMON_PORTS)) {
            for (int i = 0; i < 50; i++) {
                int randomPort = (int)(Math.random() * 642) + 33435 + (i * 642);
                port += ", " + randomPort;
            }
        }

        display.setInterval(scanInterval);
        timer = new Timer();
        String finalPort = port;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setScanning(true, display);
                ScannerProcess scan = new ScannerProcess(ip, finalPort);
                scan.start();
                while (scan.isAlive() || ScannerProcess.getScanningPort() > 0) { }
                setScanning(false, display);
            }
        }, 0, scanInterval * 60000);
    }

    public void stopRunner() {
        if (timer != null) {
            timer.cancel();
            System.out.println("* Runner stopped.");
        }
    }

    public void setScanning(boolean scanning, StatusDisplay display) {
        display.setScanning(scanning);
    }
}
