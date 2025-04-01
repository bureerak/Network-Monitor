package main.ui;
import main.database.UserPreference;
import main.database.graph.DataFetcher;
import main.database.graph.DeviceFetch;
import main.database.graph.LatencyFetch;
import main.database.graph.PortFetch;
import main.exporter.ExportCSV;
import main.exporter.ExportJSON;
import main.exporter.Print_info;
import main.ui.visualization.DeviceGraph;
import main.ui.visualization.LatencyGraph;
import main.ui.visualization.PortsGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkStatisticsViews extends JInternalFrame implements ActionListener {
    private JMenuBar menuBar;
    private JMenu menu1, menu2, item1, graph;
    private JMenuItem item2, subitem1, subitem2, item3;
    private ButtonGroup bg;
    private JRadioButtonMenuItem latency,device,port;
    private DataFetcher fetcher;
    private JPanel sub;
    public NetworkStatisticsViews(){
        super("Network Statistics | Network Monitor",false,true,false,false);
        menuBar = new JMenuBar();
        menu1 = new JMenu("File");
        menu2 = new JMenu("Options");
        item1 = new JMenu("Export");
        item2 = new JMenuItem("Print");
        item3 = new JMenuItem("Range & Profile");
        item3.addActionListener(this);
        subitem1 = new JMenuItem("CSV");
        ImageIcon iconCsv = new ImageIcon("resources/csv.png");
        subitem1.setIcon(iconCsv);
        subitem2 = new JMenuItem("JSON");
        ImageIcon iconJson = new ImageIcon("resources/json.png");
        subitem2.setIcon(iconJson);
        ImageIcon icon = new ImageIcon("resources/Icon.jpg");
        Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        setFrameIcon(new ImageIcon(scaledImage));
        sub = new JPanel(new BorderLayout());
        add(sub);

        menuBar.add(menu1);
        menuBar.add(menu2);
        menu1.add(item1);
        menu1.add(item2);
        item1.add(subitem1);
        item1.add(subitem2);

        // Graph
        latency = new JRadioButtonMenuItem("Latency/Time");
        latency.setActionCommand("Latency/Time");
        latency.setSelected(true);
        device = new JRadioButtonMenuItem("Device/Time");
        device.setActionCommand("Device/Time");
        port = new JRadioButtonMenuItem("Ports/Time");
        port.setActionCommand("Ports/Time");
        graph = new JMenu("Graph");
        graph.add(latency);
        graph.add(device);
        graph.add(port);
        bg = new ButtonGroup();
        bg.add(latency);
        bg.add(device);
        bg.add(port);

        menu2.add(graph);
        menu2.add(item3);


        setJMenuBar(menuBar);
        item2.addActionListener(e -> Print_info.print_panel(sub));
        subitem1.addActionListener(e -> ExportCSV.exportData(UserPreference.getProfileID(), bg.getSelection().getActionCommand()));
        subitem2.addActionListener(e -> ExportJSON.exportData(UserPreference.getProfileID(), bg.getSelection().getActionCommand()));
        setSize(new Dimension(600,500));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fetcher = LatencyFetch.getInstance(); /// condition Here
        if (bg.getSelection().getActionCommand().equals("Latency/Time")) {
            fetcher = LatencyFetch.getInstance();
        } else if (bg.getSelection().getActionCommand().equals("Device/Time")) {
            fetcher = DeviceFetch.getInstance();
        } else if (bg.getSelection().getActionCommand().equals("Ports/Time")) {
            fetcher = PortFetch.getInstance();
        }
        if (e.getSource().equals(item3)) {
            new NSVOptionView(this,fetcher);
        }
    }

    public void updateGraph(int id){
        sub.removeAll();
        if (bg.getSelection().getActionCommand().equals("Latency/Time")) {
            sub.add(new LatencyGraph( id, LatencyGraph.NotFetch).getChartPanel());
        } else if (bg.getSelection().getActionCommand().equals("Device/Time")) {
            sub.add(new DeviceGraph( id, DeviceGraph.NotFetch).getChartPanel());
        } else if (bg.getSelection().getActionCommand().equals("Ports/Time")) {
            sub.add(new PortsGraph(id, PortsGraph.NotFetch).getChartPanel());
        }
        sub.validate();
        sub.repaint();
    }
}