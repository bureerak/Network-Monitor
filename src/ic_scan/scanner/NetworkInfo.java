package ic_scan.scanner;

import java.net.*;
import java.util.Enumeration;

public class NetworkInfo {
    public void getInfo(){
        try {
            // ดึงข้อมูลของเครื่องตัวเอง
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("Hostname: " + localHost.getHostName());
            System.out.println("Local IP Address: " + localHost.getHostAddress());

            // วนลูปดึงข้อมูลของ Network Interface ทั้งหมด
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                // ข้ามอินเทอร์เฟซที่ปิดการใช้งานหรือเป็น Loopback
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                System.out.println("\nInterface Name: " + networkInterface.getName());

                // ดึง MAC Address
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    StringBuilder macAddress = new StringBuilder();
                    for (byte b : mac) {
                        macAddress.append(String.format("%02X:", b));
                    }
                    if (macAddress.length() > 0) {
                        macAddress.setLength(macAddress.length() - 1); // ลบ ":" สุดท้ายออก
                    }
                    System.out.println("MAC Address: " + macAddress);
                }

                // ดึง IP Address ที่เกี่ยวข้องกับอินเทอร์เฟซนี้
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    System.out.println("IP Address: " + inetAddress.getHostAddress());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
