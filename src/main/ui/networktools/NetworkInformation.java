package main.ui.networktools;

import main.NetworkTools.InternalNetwork;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public class NetworkInformation extends JInternalFrame {
    private JPanel frame, i, h, m;
    private JLabel ip, host, mac, ipR, hostR, macR;
    public NetworkInformation() {
        super("Network Information", false,true,false,false);
        ip = new JLabel("IP:");
        ipR = new JLabel(InternalNetwork.getIP());
        host = new JLabel("Hostname:");
        hostR = new JLabel(InternalNetwork.getHostName());
        mac = new JLabel("MAC Address:");
        macR = new JLabel(InternalNetwork.getMACAddress());
        i = new JPanel();
        i.setLayout(new FlowLayout(FlowLayout.LEFT));
        i.add(ip);
        i.add(ipR);
        h = new JPanel();
        h.setLayout(new FlowLayout(FlowLayout.LEFT));
        h.add(host);
        h.add(hostR);
        m = new JPanel();
        m.setLayout(new FlowLayout(FlowLayout.LEFT));
        m.add(mac);
        m.add(macR);

        i.setBorder(new EmptyBorder(20, 25, 0, 6));
        h.setBorder(new EmptyBorder(6, 25, 6, 6));
        m.setBorder(new EmptyBorder(6, 25, 20, 6));

        frame = new JPanel();
        frame.setBorder(new CompoundBorder(new EmptyBorder(4,4,2,4),BorderFactory.createTitledBorder("Network Information")));
        frame.setLayout(new BorderLayout());
        frame.add(i, BorderLayout.NORTH);
        frame.add(h, BorderLayout.CENTER);
        frame.add(m, BorderLayout.SOUTH);
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Icon.jpg")));;
        Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        setFrameIcon(new ImageIcon(scaledImage));
        setLayout(new BorderLayout());
        add(frame, BorderLayout.CENTER);
        setSize(new Dimension(400,200));
    }

}
