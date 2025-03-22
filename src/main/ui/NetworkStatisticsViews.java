package main.ui;
import main.exporter.Print_info;
import main.ui.visualization.GraphPane;

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

        menuBar.add(menu1);
        menuBar.add(menu2);
        menu1.add(item1);
        menu1.add(item2);
        item1.add(subitem1);
        item1.add(subitem2);

        // Graph
        latency = new JRadioButtonMenuItem("Latency/Time");
        latency.setSelected(true);
        device = new JRadioButtonMenuItem("Device/Time");
        graph = new JMenu("Graph");
        graph.add(latency);
        graph.add(device);
        bg = new ButtonGroup();
        bg.add(latency);
        bg.add(device);

        menu2.add(graph);
        menu2.add(item3);

        add(new GraphPane(1));

        setJMenuBar(menuBar);
//        item2.addActionListener(e -> Print_info.print_panel(new JPanel()));

        setSize(new Dimension(600,500));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(item3)) {
            new NSVOptionView(this);
        }
    }
}

