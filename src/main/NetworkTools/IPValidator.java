package main.NetworkTools;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPValidator {
    public static boolean isValidIPv4(String ipAddress) {
        String ipV4Pattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ipAddress.matches(ipV4Pattern);
    }

    public static boolean isValidHost(String host) {
        try {
            return isValidIPv4(InetAddress.getByName(host).getHostAddress());
        } catch (UnknownHostException e) {
            return false;
        }
    }

    public static boolean isValidSubnetMask(String subnetMask) {
        if (isValidIPv4(subnetMask)) {
            String[] tempSubnet = IPCalculator.toBinary(subnetMask);
            boolean foundZero = false;
            for (String i : tempSubnet) {
                for (char j : i.toCharArray()) {
                    if (j == '1' && foundZero) {
                        return false;
                    }

                    if (j == '0') {
                        foundZero = true;
                    }
                }
            }
            return true;
        } else {
            try {
                int testCIDR = Integer.parseInt(subnetMask);
                return testCIDR >= 0 && testCIDR <= 32;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
    }

    public static boolean isValidPort(int port) {
        return port >= 1 && port <= 65535;
    }

    public static boolean isValidPort(int port1, int port2) {
        return port1 <= port2 && isValidPort(port1) && isValidPort(port2);
    }
}
