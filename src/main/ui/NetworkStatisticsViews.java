package main.ui;
import main.database.UserPreference;
import main.database.graph.DataFetcher;
import main.database.graph.DeviceFetch;
import main.database.graph.LatencyFetch;
import main.exporter.ExportCSV;
import main.exporter.ExportJSON;
import main.ui.visualization.DeviceGraph;
import main.ui.visualization.LatencyGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkStatisticsViews extends JInternalFrame implements ActionListener {
    private JMenuBar menuBar;
    private JMenu menu1, menu2, item1, graph;
    private JMenuItem item2, subitem1, subitem2, item3;
    private ButtonGroup bg;
    private JRadioButtonMenuItem latency,device;
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
        subitem2 = new JMenuItem("JSON");
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
        graph = new JMenu("Graph");
        graph.add(latency);
        graph.add(device);
        bg = new ButtonGroup();
        bg.add(latency);
        bg.add(device);

        menu2.add(graph);
        menu2.add(item3);


        setJMenuBar(menuBar);
//        item2.addActionListener(e -> Print_info.print_panel(new JPanel()));
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
        }
        sub.validate();
        sub.repaint();
    }
}