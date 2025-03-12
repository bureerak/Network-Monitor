//package main.ui;
//
//import javax.swing.*;
//import javax.swing.border.CompoundBorder;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.beans.PropertyVetoException;
//
//public class NetworkToolsView extends JInternalFrame implements ActionListener {
//    private JPanel top, bot;
//    private JButton nwInf, intInf, ping, ipCal, nwScn, pScn;
//    private GroupLayout botL;
//
//    PortScannerView pScanner;
//    PingView pPing;
//    NetworkInformation infoView;
//    InternetInformationView internetInfoView;
//    NetworkScannerView nwScannerView;
//    IpAddress ipAddress;
//    public NetworkToolsView() {
//        super("Network Tools", false, true, false, false);
//
//        infoView = new NetworkInformation();
//        internetInfoView = new InternetInformationView();
//        nwScannerView = new NetworkScannerView();
//        pScanner = new PortScannerView();
//        pPing = new PingView();
//        ipAddress = new IpAddress();
//
//        nwInf = new JButton("Network Info");
//        intInf = new JButton("Internet Info");
//        nwInf.setPreferredSize(new Dimension(150, 35));
//        intInf.setPreferredSize(new Dimension(150, 35));
//        nwInf.addActionListener(this);
//        intInf.addActionListener(this);
//        top = new JPanel();
//        top.setBorder(new CompoundBorder(new EmptyBorder(4, 4, 2, 4), BorderFactory.createTitledBorder("Information")));
//        top.setLayout(new FlowLayout(FlowLayout.CENTER, 6, 20));
//        top.add(nwInf);
//        top.add(intInf);
//
//        ping = new JButton("Ping");
//        ipCal = new JButton("IP Calculator");
//        nwScn = new JButton("Network Scanner");
//        pScn = new JButton("Port Scanner");
//        ping.addActionListener(this);
//        ipCal.addActionListener(this);
//        nwScn.addActionListener(this);
//        pScn.addActionListener(this);
//
//        bot = new JPanel();
//        bot.setBorder(new CompoundBorder(new EmptyBorder(4, 4, 2, 4), BorderFactory.createTitledBorder("Tools")));
//        botL = new GroupLayout(bot);
//        bot.setLayout(botL);
//        botL.setHorizontalGroup(
//                botL.createSequentialGroup()
//                        .addGap(0, Short.MAX_VALUE, Short.MAX_VALUE)
//                        .addGroup(botL.createParallelGroup(GroupLayout.Alignment.LEADING)
//                                .addComponent(ping, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
//                                .addComponent(nwScn, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
//                        .addGap(6)
//                        .addGroup(botL.createParallelGroup(GroupLayout.Alignment.TRAILING)
//                                .addComponent(ipCal, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
//                                .addComponent(pScn, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
//                        .addGap(0, Short.MAX_VALUE, Short.MAX_VALUE)
//        );
//        botL.setVerticalGroup(
//                botL.createSequentialGroup()
//                        .addGap(0, Short.MAX_VALUE, Short.MAX_VALUE)
//                        .addGroup(botL.createParallelGroup(GroupLayout.Alignment.BASELINE)
//                                .addComponent(ping, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
//                                .addComponent(ipCal, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
//                        .addGap(6)
//                        .addGroup(botL.createParallelGroup(GroupLayout.Alignment.BASELINE)
//                                .addComponent(nwScn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
//                                .addComponent(pScn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
//                        .addGap(0, Short.MAX_VALUE, Short.MAX_VALUE)
//        );
//
//        setLayout(new BorderLayout());
//        add(top, BorderLayout.NORTH);
//        add(bot, BorderLayout.CENTER);
//        setSize(new Dimension(400, 280));
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource().equals(nwInf)) {
//            if (!infoView.isVisible()) {
//                infoView.setVisible(true);
//                getDesktopPane().add(infoView);
//                try {
//                    infoView.setSelected(true);
//                } catch (PropertyVetoException ex) {
//                    throw new RuntimeException(ex);
//                }
//            } else {
//                try {
//                    infoView.setSelected(true);
//                } catch (PropertyVetoException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//
//        }else if (e.getSource().equals(intInf)) {
//            if (!internetInfoView.isVisible()) {
//                internetInfoView.setVisible(true);
//                getDesktopPane().add(internetInfoView);
//                try {
//                    internetInfoView.setSelected(true);
//                } catch (PropertyVetoException ex) {
//                    throw new RuntimeException(ex);
//                }
//            } else {
//                try {
//                    internetInfoView.setSelected(true);
//                } catch (PropertyVetoException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        } else if (e.getSource().equals(ping)) {
//            if (!pPing.isVisible()) {
//                pPing.setVisible(true);
//                getDesktopPane().add(pPing);
//                try {
//                    pPing.setSelected(true);
//                } catch (PropertyVetoException ex) {
//                    throw new RuntimeException(ex);
//                }
//            } else {
//                try {
//                    pPing.setSelected(true);
//                } catch (PropertyVetoException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        } else if (e.getSource().equals(ipCal)) {
//            if (!ipAddress.isVisible()) {
//                ipAddress.setVisible(true);
//                getDesktopPane().add(ipAddress);
//                try {
//                    ipAddress.setSelected(true);
//                } catch (PropertyVetoException ex) {
//                    throw new RuntimeException(ex);
//                }
//            } else {
//                try {
//                    ipAddress.setSelected(true);
//                } catch (PropertyVetoException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        } else if (e.getSource().equals(nwScn)) {
//            nwScannerView.clearTable();
//            nwScannerView.setCurrentCount();
//            if (!nwScannerView.isVisible()) {
//                nwScannerView.setVisible(true);
//                getDesktopPane().add(nwScannerView);
//                try {
//                    nwScannerView.setSelected(true);
//                } catch (PropertyVetoException ex) {
//                    throw new RuntimeException(ex);
//                }
//            } else {
//                try {
//                    nwScannerView.setSelected(true);
//                } catch (PropertyVetoException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        } else if (e.getSource().equals(pScn)) {
//            if (!pScanner.isVisible()) {
//                pScanner.setVisible(true);
//                getDesktopPane().add(pScanner);
//                try {
//                    pScanner.setSelected(true);
//                } catch (PropertyVetoException ex) {
//                    throw new RuntimeException(ex);
//                }
//            } else {
//                try {
//                    pScanner.setSelected(true);
//                } catch (PropertyVetoException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        }
//    }
//}