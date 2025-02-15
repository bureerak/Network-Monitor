package main.NetworkTools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class InternalNetwork {
    /**
     * Get a using network interface IP.
     * @return IP Address as string.
     */
    public static String getIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get a using network interface hostname.
     * @return Hostname as string.
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get a using network interface MAC address.
     * @return MAC Address as string.
     */
    public static String getMACAddress() {
        try {
            NetworkInterface localInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            byte[] byteMac = localInterface.getHardwareAddress();

            StringBuilder macAddress = new StringBuilder();
            for (byte b : byteMac) {
                macAddress.append(String.format("%02X:", b));
            }
            if (! macAddress.isEmpty()) {
                macAddress.setLength(macAddress.length() - 1);
            }

            return macAddress.toString();
        } catch (UnknownHostException | SocketException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
