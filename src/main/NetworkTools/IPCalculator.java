package main.NetworkTools;

public class IPCalculator {
    private boolean isInputValid =  false;
    private String binaryIP;
    private String binarySubnet;
    private String binaryNetwork;
    private String binaryBroadcast;
    private String binaryWildcard;
    private String stringIP;
    private String stringSubnet;
    private String stringNetwork;
    private String stringBroadcast;
    private String stringWildcard;
    private String firstIP;
    private String lastIP;
    private int usableIP;

    public IPCalculator(String ipAddress, String subnetMask) {
        if (IPValidator.isValidIPv4(ipAddress) && IPValidator.isValidSubnetMask(subnetMask)) {
            isInputValid = true;
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

            this.binaryIP = binaryIP[0] + " " + binaryIP[1] + " " + binaryIP[2] + " " + binaryIP[3];
            this.binarySubnet = binarySubnet[0] + " " + binarySubnet[1] + " " + binarySubnet[2] + " " + binarySubnet[3];
            this.binaryNetwork =  binaryNetwork[0] + " " + binaryNetwork[1] + " " + binaryNetwork[2] + " " + binaryNetwork[3];
            this.binaryBroadcast = binaryBroadcast[0] + " " + binaryBroadcast[1] + " " + binaryBroadcast[2] + " " + binaryBroadcast[3];
            this.binaryWildcard = binaryWildcard[0] + " " + binaryWildcard[1] + " " + binaryWildcard[2] + " " + binaryWildcard[3];
            this.stringIP = toDecimal(binaryIP);
            this.stringSubnet = toDecimal(binarySubnet);
            this.stringNetwork = toDecimal(binaryNetwork);
            this.stringBroadcast = toDecimal(binaryBroadcast);
            this.stringWildcard = toDecimal(binaryWildcard);
            this.firstIP = toDecimal(firstIP);
            this.lastIP = toDecimal(lastIP);
            this.usableIP = calcUsableIP(firstIP, lastIP);
        } else {
            System.out.println("Invalid IP Address and/or Subnet Mask");
        }
    }

    public boolean isInputValid() {
        return isInputValid;
    }

    public String getBinaryIP() {
        return binaryIP;
    }

    public String getBinarySubnet() {
        return binarySubnet;
    }

    public String getBinaryNetwork() {
        return binaryNetwork;
    }

    public String getBinaryBroadcast() {
        return binaryBroadcast;
    }

    public String getBinaryWildcard() {
        return binaryWildcard;
    }

    public String getIP() {
        return stringIP;
    }

    public String getSubnetMask() {
        return stringSubnet;
    }

    public String getNetworkID() {
        return stringNetwork;
    }

    public String getBroadcastIP() {
        return stringBroadcast;
    }

    public String getWildcardIP() {
        return stringWildcard;
    }

    public String getFirstIP() {
        return firstIP;
    }

    public String getLastIP() {
        return lastIP;
    }

    public int getUsableIP() {
        return usableIP;
    }

    public static String[] toBinary(String ipAddress) {
        if (IPValidator.isValidIPv4(ipAddress)) {
            String[] octet = ipAddress.split("[.]");
            for (int i = 0; i < octet.length; i++) {
                octet[i] = Integer.toBinaryString(Integer.parseInt(octet[i]));
                octet[i] = "0".repeat(8 - octet[i].length()) + octet[i];
            }
            return octet;
        }
        return null;
    }

    public static String toDecimal(String[] binaryIP) {
        String ipAddress = "";
        for (String s : binaryIP) {
            ipAddress += Integer.parseInt(s, 2) + ".";
        }
        return ipAddress.substring(0, ipAddress.length() - 1);
    }

    public static int calcUsableIP(String[] firstIP, String[] lastIP) {
        int firstOctet = (Integer.parseInt(lastIP[0], 2) - Integer.parseInt(firstIP[0], 2)) * 256 * 256 * 256;
        int secondOctet = (Integer.parseInt(lastIP[1], 2) - Integer.parseInt(firstIP[1], 2)) * 256 * 256;
        int thirdOctet = (Integer.parseInt(lastIP[2], 2) - Integer.parseInt(firstIP[2], 2)) * 256;
        int forthOctet = (Integer.parseInt(lastIP[3], 2) - Integer.parseInt(firstIP[3], 2));
        return firstOctet + secondOctet + thirdOctet + forthOctet + 1;
    }

    /*public static void main(String[] args) {
        IPCalculator calc = new IPCalculator("10.20.3.5", "255.255.255.0");
        if (calc.isInputValid()) {
            System.out.println("Binary IP: " + calc.getBinaryIP());
            System.out.println("Binary Subnet: " + calc.getBinarySubnet());
            System.out.println("Binary Network: " + calc.getBinaryNetwork());
            System.out.println("Binary Broadcast: " + calc.getBinaryBroadcast());
            System.out.println("Binary Wildcard: " + calc.getBinaryWildcard());
            System.out.println("IP Address: " + calc.getIP());
            System.out.println("Subnet Mask: " + calc.getSubnetMask());
            System.out.println("Network ID: " + calc.getNetworkID());
            System.out.println("Broadcast IP: " + calc.getBroadcastIP());
            System.out.println("Wildcard IP: " + calc.getWildcardIP());
            System.out.println("First IP: " + calc.getFirstIP());
            System.out.println("Last IP: " + calc.getLastIP());
            System.out.println("Usable IP: " + calc.getUsableIP());
        } else {

        }
    }*/
}
