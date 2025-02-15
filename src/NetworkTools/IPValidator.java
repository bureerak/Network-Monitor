package NetworkTools;

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
            String[] tempMask = subnetMask.split("\\.");
            for (String mask : tempMask) {
                if (Integer.parseInt(mask) != 128 && Integer.parseInt(mask) != 192 && Integer.parseInt(mask) != 224 &&
                Integer.parseInt(mask) != 240 && Integer.parseInt(mask) != 248 && Integer.parseInt(mask) != 252 &&
                Integer.parseInt(mask) != 254 && Integer.parseInt(mask) != 255 && Integer.parseInt(mask) != 0) {
                    return false;
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
