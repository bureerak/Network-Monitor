package main.NetworkScanner;

import main.NetworkTools.*;

import java.util.ArrayList;
import java.util.Iterator;

public class ScannerProcess extends Thread implements DeviceDisplay, PortDisplay {
    String ip;
    String port;
    ArrayList devices = new ArrayList();
    ArrayList ports = new ArrayList();
    ArrayList dataDev;
    ArrayList dataPort;

    public ScannerProcess(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public void run() {
        DeviceScanner scanner = new DeviceScanner();
        scanner.scan(ip, this);

        Iterator it = devices.iterator();
        while (it.hasNext()) {
            ArrayList temp = (ArrayList) it.next();
            PortScanner portScanner = new PortScanner();
            portScanner.scan((String)temp.get(0), port, this);
        }
    }

    private int getLatency(String ip) {
        int latency = 0;
        for (int i = 0; i <= 3; i++) {
            int responseTime = Ping.runOnce(ip);
            if (responseTime >= 0 && i != 0) {
                latency += responseTime;
            }
        }

        return (int)(latency / 3);
    }

    @Override
    public void addDevice(String ip, String mac, String hostname) {
        dataDev = new ArrayList();
        dataDev.add(ip);
        dataDev.add(mac);
        dataDev.add(hostname);
        dataDev.add(getLatency(ip));
        devices.add(dataDev);
        dataDev = null;
        System.gc();
        display_ip();
    }

    @Override
    public void setPort(String ip, ArrayList<Integer> port) {
        dataPort = new ArrayList();
        dataPort.add(ip);
        dataPort.add(port);
        ports.add(dataPort);
        dataPort = null;
        System.gc();
        display_port();
    }

    public void display_ip() {
        System.out.println();
        System.out.println("+--- IP Information ---+");
        Iterator dev = devices.iterator();
        while (dev.hasNext()) {
            ArrayList temp = ((ArrayList)dev.next());
            Iterator info = temp.iterator();
            for (int i = 0; i < temp.size(); i++) {
                if (i == 0) {
                    System.out.print("IP Address: " + (String)temp.get(i));
                } else if (i == 1) {
                    System.out.print(" | MAC Address: " + (String)temp.get(i));
                } else if (i == 2) {
                    System.out.print(" | Hostname: " + (String)temp.get(i));
                } else if (i == 3) {
                    System.out.println(" | Latency: " + (int)temp.get(i) + " ms.");
                }
            }
        }
    }

    public void display_port() {
        System.out.println();
        System.out.println("+--- Port Information ---+");
        Iterator dev = ports.iterator();
        while (dev.hasNext()) {
            ArrayList temp = ((ArrayList)dev.next());
            Iterator info = temp.iterator();
            for (int i = 0; i < temp.size(); i++) {
                if (i == 0) {
                    System.out.print("IP Address: " + ((String)temp.get(i)).strip());
                } else if (i == 1) {
                    System.out.print(" | Port: ");
                    ArrayList<Integer> temp2 = (ArrayList)temp.get(i);
                    Iterator<Integer> port = temp2.iterator();
                    for (int i2 = 0; port.hasNext(); i2++) {
                        System.out.print(port.next() + " ");
                    }
                }
                System.out.println();
            }
        }
    }
}
