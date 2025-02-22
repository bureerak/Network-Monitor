package main.NetworkScanner;

public class test implements StatusDisplay {
    @Override
    public void setInterval(int interval) {
        System.out.println("Current interval: " + interval + " minutes.");
    }

    @Override
    public void setScanning(boolean isScanning) {
        System.out.println("Current Scanning: " + isScanning);
    }

    public test() {
        ScannerRunner sr = new ScannerRunner("192.168.3.1-192.168.3.254",this);
    }

    public static void main(String[] args) {
        test test = new test();
    }
}
