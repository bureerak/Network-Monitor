package main.NetworkTools;

public interface DeviceDisplay {
    public abstract void addDevice(String ip, String mac, String hostname);
    public void updateProgress(int scanned);
}
