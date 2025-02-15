package main.NetworkTools;

public class IPCalculator {
    public static void calculate(String ipAddress, String subnetMask) {
        if (IPValidator.isValidIP(ipAddress) && IPValidator.isValidSubnetMask(subnetMask)) {
            String[] binaryIP = toBinary(ipAddress);
            String[] binarySubnet = toBinary(subnetMask);
            String[] binaryNetwork = {"", "", "", ""};
            String[] binaryBroadcast = {"", "", "", ""};
            String[] binaryWildcard = {"", "", "", ""};
            String[] firstIP = {"", "", "", ""};
            String[] lastIP = {"", "", "", ""};

            for (int i = 0; i < binaryIP.length; i++) {
                for (int j = 0; j < 8; j++) {
                    //NETWORK ID
                    if (binarySubnet[i].charAt(j) == '1') {
                        binaryNetwork[i] += binaryIP[i].charAt(j);
                    } else {
                        binaryNetwork[i] += "0";
                    }

                    //BROADCAST ID
                    if (binarySubnet[i].charAt(j) == '1') {
                        binaryBroadcast[i] += binaryIP[i].charAt(j);
                    } else {
                        binaryBroadcast[i] += "1";
                    }

                    //WILDCARD ID
                    if (binarySubnet[i].charAt(j) == '1') {
                        binaryWildcard[i] += "0";
                    } else {
                        binaryWildcard[i] += "1";
                    }
                }
            }

            firstIP[0] = binaryNetwork[0];
            firstIP[1] = binaryNetwork[1];
            firstIP[2] = binaryNetwork[2];
            firstIP[3] = Integer.toBinaryString(Integer.parseInt(binaryNetwork[3], 2) + 1);

            lastIP[0] = binaryBroadcast[0];
            lastIP[1] = binaryBroadcast[1];
            lastIP[2] = binaryBroadcast[2];
            lastIP[3] = Integer.toBinaryString(Integer.parseInt(binaryBroadcast[3], 2) - 1);

            System.out.println("Binary IP: " + binaryIP[0] + " " + binaryIP[1] + " " + binaryIP[2] + " " + binaryIP[3]);
            System.out.println("Binary Subnet: " + binarySubnet[0] + " " + binarySubnet[1] + " " + binarySubnet[2] + " " + binarySubnet[3]);
            System.out.println("Binary Network: " + binaryNetwork[0] + " " + binaryNetwork[1] + " " + binaryNetwork[2] + " " + binaryNetwork[3]);
            System.out.println("Binary Broadcast: " + binaryBroadcast[0] + " " + binaryBroadcast[1] + " " + binaryBroadcast[2] + " " + binaryBroadcast[3]);
            System.out.println("Binary Wildcard: " + binaryWildcard[0] + " " + binaryWildcard[1] + " " + binaryWildcard[2] + " " + binaryWildcard[3]);
            System.out.println();
            System.out.println("IP Address: " + toDecimal(binaryIP));
            System.out.println("Subnet Mask: " + toDecimal(binarySubnet));
            System.out.println("Network: " + toDecimal(binaryNetwork));
            System.out.println("Broadcast: " + toDecimal(binaryBroadcast));
            System.out.println("Wildcard: " + toDecimal(binaryWildcard));
            System.out.println();
            System.out.println("First Available IP: " + toDecimal(firstIP));
            System.out.println("Last Available IP: " + toDecimal(lastIP));
            System.out.println("Usable IP: " + calcUsableIP(firstIP, lastIP));

        } else {
            System.out.println("Invalid IP Address and/or Subnet Mask");
        }
    }

    public static String[] toBinary(String ipAddress) {
        if (IPValidator.isValidIP(ipAddress)) {
            String[] octet = ipAddress.split("[.]");
            for (int i = 0; i < octet.length; i++) {
                octet[i] = Integer.toBinaryString(Integer.parseInt(octet[i]));
                octet[i] = "0".repeat(8 - octet[i].length()) + octet[i];
            }
            return octet;
        }
        return null;
    }

    private static String toDecimal(String[] binaryIP) {
        String ipAddress = "";
        for (String s : binaryIP) {
            ipAddress += Integer.parseInt(s, 2) + ".";
        }
        return ipAddress.substring(0, ipAddress.length() - 1);
    }

    private static int calcUsableIP(String[] firstIP, String[] lastIP) {
        int firstOctet = (Integer.parseInt(lastIP[0], 2) - Integer.parseInt(firstIP[0], 2)) * 256 * 256 * 256;
        int secondOctet = (Integer.parseInt(lastIP[1], 2) - Integer.parseInt(firstIP[1], 2)) * 256 * 256;
        int thirdOctet = (Integer.parseInt(lastIP[2], 2) - Integer.parseInt(firstIP[2], 2)) * 256;
        int forthOctet = (Integer.parseInt(lastIP[3], 2) - Integer.parseInt(firstIP[3], 2));
        return firstOctet + secondOctet + thirdOctet + forthOctet + 1;
    }

    public static void main(String[] args) {
        calculate("192.168.11.32", "255.192.128.0");
    }
}
