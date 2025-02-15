package main.NetworkTools;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPValidator {
    public static boolean isValidIP(String ipAddress) {
        try {
            InetAddress.getByName(ipAddress);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    public static boolean isValidSubnetMask(String subnetMask) {
        try {
            InetAddress.getByName(subnetMask);

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
        } catch (UnknownHostException e) {
            try {
                int testCIDR = Integer.parseInt(subnetMask);
                return testCIDR >= 0 && testCIDR <= 32;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
    }
}
