package main.NetworkScanner;

import main.database.UserPreference;

public class InitializeScan implements StatusDisplay {
    private ScannerRunner sr;
    @Override
    public void setInterval(int interval) {
        System.out.println("Current Interval: " + interval + " minutes.");
    }

    @Override
    public void setScanning(boolean isScanning) {
        System.out.println("Current Scanning: " + isScanning);
    }

    private void start() {
        if(sr == null) {
            sr = new ScannerRunner("192.168.1.1-192.168.1.254", UserPreference.getInterval(), this);
        }
    }

    public void stopScan() {
        if (sr != null) {
            sr.stopRunner();
            sr = null;
        }
    }

    public void notifyStatus(int status){
        if (status == UserPreference.SAVING) {
            start();
        } else if ( status == UserPreference.IDLE ) {
            stopScan();
        }
    }
}
