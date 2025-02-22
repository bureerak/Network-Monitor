package main.NetworkTools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        } catch (IOException ignored) { return null; }
    }

    /**
     * Get an IP Address interface MAC address.
     * @return MAC Address as string.
     */
    public static String getMACAddress(String ip) {
        try {
            if (InetAddress.getByName(ip).equals(InetAddress.getLocalHost())) {
                return getMACAddress();
            } else {
                return getARPMAC(ip);
            }
        } catch (IOException ignored) { }
        return null;
    }

    private static String getARPMAC(String ip){
        String systemInput = "";
        String result = null;
        try {
            Runtime.getRuntime().exec("arp -a");
            Scanner s = new Scanner(Runtime.getRuntime().exec("arp -a " + ip).getInputStream()).useDelimiter("\\A");
            systemInput = s.next();
            Pattern macPattern = Pattern.compile("\\s{0,}([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})");
            Matcher macMatcher = macPattern.matcher(systemInput);
            if (macMatcher.find()) {
                result = macMatcher.group().replaceAll("\\s", "").replaceAll("-", ":");
            } else {
                return null;
            }
        } catch (IOException ignored) { }
        return result;
    }
}
