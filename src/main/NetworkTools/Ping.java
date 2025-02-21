package main.NetworkTools;

import java.net.InetAddress;
import java.io.IOException;

public class Ping {
    private static final int PING_TIMEOUT = 5000;

    /**
     * Ping an address once with default timeout.
     * @return Ping time in milliseconds as int,
     * if timeout occurred, return -1,
     * if address is not reachable, return -2.
     */
    public static int runOnce(String ipAddress){
        return runOnce(ipAddress, PING_TIMEOUT);
    }

    /**
     * Ping an address once with custom timeout in milliseconds.
     * @return Ping time in milliseconds as int,
     * if timeout occurred, return -1,
     * if address is not reachable, return -2.
     */
    public static int runOnce(String ipAddress, int timeOut) {
        long timeUsed = System.currentTimeMillis();
        boolean canPing = false;
        try {
            canPing = InetAddress.getByName(ipAddress).isReachable(timeOut);
            timeUsed = System.currentTimeMillis() - timeUsed;
            if (! canPing) {
                timeUsed = -1;
            }
        } catch (IOException e) {
            timeUsed = -2;
        }
        return (int) timeUsed;
    }

    /*public static void main(String[] args) {
        String host = "192.168.3.249";
        for (int i = 1; i <= 4; i++) {
            System.out.print("Round " + i + " | ");
            int result = runOnce(host);
            if (result == -1) {
                System.out.println("Timeout");
            } else if (result == -2) {
                System.out.println("Host error");
            } else {
                System.out.printf(host + ", " + result + " ms.");
            }
        }
    }*/
}