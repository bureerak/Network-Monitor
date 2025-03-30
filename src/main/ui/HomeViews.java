package main.ui;

import main.NetworkTools.InternalNetwork;
import main.database.UserPreference;
import main.ui.visualization.LatencyGraph;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Objects;

public class HomeViews extends JInternalFrame implements ActionListener {
    JSplitPane split;
    JPanel tPane , upHalfPane, downHalfPane, subUpHalfPane;
    JButton profileEdit ,netTools ,netStat ,settings;
    ImageIcon titleImg;
    ProfileEditorView PEView;
    SettingViews SettingView;
    NetworkStatisticsViews NSView;
    NetworkToolsView NWToolsView;
    static JPanel bPane;
    static JLabel title, titleIcon, select, status, interval;

    public HomeViews(){
        super("Hub | Network Monitor",false,false,false,false);
        subUpHalfPane = new JPanel();
        ImageIcon icon = new ImageIcon("resources/Icon.jpg");
        Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        setFrameIcon(new ImageIcon(scaledImage));

        titleImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/titleIcon.png")));
        Image scaleImg = titleImg.getImage().getScaledInstance(28,28,Image.SCALE_SMOOTH);
        ImageIcon resizeIcon = new ImageIcon(scaleImg);
        titleIcon = new JLabel(resizeIcon);

        PEView = new ProfileEditorView();
        SettingView = new SettingViews();
        NSView = new NetworkStatisticsViews();
        NWToolsView = new NetworkToolsView();

        profileEdit = new JButton("Profile Editor");
        netTools = new JButton("Network Tools");
        netStat = new JButton("Network Statistics");
        settings = new JButton("Settings");

        settings.addActionListener(this);
        netTools.addActionListener(this);
        netStat.addActionListener(this);
        profileEdit.addActionListener(this);

        tPane = new JPanel(); tPane.setLayout(new GridLayout(2,1));
        upHalfPane = new JPanel(); upHalfPane.setLayout(new GridLayout(2,1));
        title = new JLabel("Network Monitor");
        title.setFont( new Font("Arial",Font.BOLD,22) );
        title.setBorder(new EmptyBorder(8,0,0,0));

        JPanel bxl = new JPanel(); bxl.setLayout(new BoxLayout(bxl,BoxLayout.LINE_AXIS));
        bxl.setBorder(new EmptyBorder(0,16,0,0));
        bxl.add(titleIcon);
        bxl.add(Box.createRigidArea(new Dimension(5,0)));
        bxl.add(title);

        upHalfPane.add(bxl);
        subUpHalfPane.add(profileEdit); subUpHalfPane.add(netTools); subUpHalfPane.add(netStat); subUpHalfPane.add(settings);
        upHalfPane.add(subUpHalfPane);

        downHalfPane = new JPanel();
        downHalfPane.setLayout( new GridLayout(1,2) );
        JPanel myInfo = new JPanel(new BorderLayout());
        myInfo.setBorder(new CompoundBorder(new EmptyBorder(7,7,7,2),BorderFactory.createTitledBorder("My Information")));
        JPanel monitor = new JPanel();
        monitor.setLayout(new BorderLayout());
        monitor.setBorder(new CompoundBorder(new EmptyBorder(7,2,7,7),BorderFactory.createTitledBorder("Monitor Information")));
        downHalfPane.add(myInfo);
        downHalfPane.add(monitor);

        JPanel InfoPane = new JPanel();
        InfoPane.setLayout(new BoxLayout(InfoPane,BoxLayout.Y_AXIS));

        JLabel hostName = new JLabel("Hostname: "+InternalNetwork.getHostName());
        JLabel ip = new JLabel("IP Address: "+InternalNetwork.getIP());
        JLabel mac = new JLabel("Mac Address: "+InternalNetwork.getMACAddress());

        InfoPane.add(hostName);
        InfoPane.add(Box.createRigidArea(new Dimension(0,7)));
        InfoPane.add(ip);
        InfoPane.add(Box.createRigidArea(new Dimension(0,7)));
        InfoPane.add(mac);
        InfoPane.setBorder(new EmptyBorder(5,25,5,5));

        myInfo.add(new JPanel(), BorderLayout.NORTH);
        myInfo.add(new JPanel(), BorderLayout.WEST);
        myInfo.add(new JPanel(), BorderLayout.EAST);
        myInfo.add(new JPanel(), BorderLayout.SOUTH);
        myInfo.add(InfoPane,BorderLayout.CENTER);

        select = new JLabel("Selected Profile: ~");
        status = new JLabel("Status: IDLE");
        interval = new JLabel("Monitor Interval: 5 minutes");

        JPanel helperPane = new JPanel();
        helperPane.setLayout(new BoxLayout(helperPane,BoxLayout.Y_AXIS));

        helperPane.add(select);
        helperPane.add(Box.createRigidArea(new Dimension(0,7)));
        helperPane.add(status);
        helperPane.add(Box.createRigidArea(new Dimension(0,7)));
        helperPane.add(interval);
        helperPane.setBorder(new EmptyBorder(2,30,0,0));

        monitor.add(new JPanel(), BorderLayout.NORTH);
        monitor.add(new JPanel(), BorderLayout.WEST);
        monitor.add(new JPanel(), BorderLayout.EAST);
        monitor.add(new JPanel(), BorderLayout.SOUTH);
        monitor.add(helperPane,BorderLayout.CENTER);

        tPane.add(upHalfPane);
        tPane.add(downHalfPane);

        bPane = new JPanel(new BorderLayout());
        bPane.add(new LatencyGraph(1, LatencyGraph.Fetch).getChartPanel());
        split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,tPane,bPane);
        split.setDividerSize(0); split.setDividerLocation(270); split.setEnabled(false);
        add(split);

        Dimension dm = new Dimension(550,600);
        setSize(dm);
        setMinimumSize(dm);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(profileEdit)){
            if (!PEView.isVisible()) {
                PEView.setVisible(true);
                getDesktopPane().add(PEView);
                try {
                    PEView.setSelected(true);
                } catch (PropertyVetoException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    PEView.setSelected(true);
                } catch (PropertyVetoException ex) {
                    throw new RuntimeException(ex);
                }
            }

        } else if (e.getSource().equals(settings)) {
            if (!SettingView.isVisible()) {
                SettingView.setVisible(true);
                getDesktopPane().add(SettingView);
                try {
                    SettingView.setSelected(true);
                } catch (PropertyVetoException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    SettingView.setSelected(true);
                } catch (PropertyVetoException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (e.getSource().equals(netStat)) {
            if (!NSView.isVisible()) {
                NSView.setVisible(true);
                getDesktopPane().add(NSView);
                try {
                    NSView.setSelected(true);
                } catch (PropertyVetoException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    NSView.setSelected(true);
                } catch (PropertyVetoException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (e.getSource().equals(netTools)) {
            if (!NWToolsView.isVisible()) {
                NWToolsView.setVisible(true);
                getDesktopPane().add(NWToolsView);
                try {
                    NWToolsView.setSelected(true);
                    NWToolsView.toFront();
                } catch (PropertyVetoException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    NWToolsView.setSelected(true);
                    NWToolsView.toFront();
                } catch (PropertyVetoException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public static void setSelect() {
        select.setText("Selected Profile: " + UserPreference.getProfile());
        select.setToolTipText(UserPreference.getProfile());
    }

    public static void setIntervalNumber(String i) {
        String[] arr = i.split("\\s");
        UserPreference.setInterval( Integer.parseInt(arr[0]) );
        interval.setText("Monitor Interval: "+UserPreference.getInterval()+" minutes");
    }

    public static void setStatus(String s) {
        status.setText(s);
    }

    public static void updateGraph(){
        bPane.removeAll();
        bPane.add(new LatencyGraph(UserPreference.getProfileID(), LatencyGraph.Fetch).getChartPanel());
        bPane.revalidate();
        bPane.repaint();
    }

}
