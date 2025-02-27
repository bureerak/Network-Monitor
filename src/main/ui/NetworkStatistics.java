package main.ui;
import main.exporter.Print_info;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkStatistics extends JInternalFrame implements ActionListener {
    private JMenuBar menuBar;
    private JMenu menu1, menu2, item1;
    private JMenuItem item2, subitem1, subitem2;
    public NetworkStatistics(){
        super("Network Statistics | Network Monitor",false,true,false,false);
        menuBar = new JMenuBar();
        menu1 = new JMenu("File");
        menu2 = new JMenu("Options");
        item1 = new JMenu("Export");
        item2 = new JMenuItem("Print");
        subitem1 = new JMenuItem("CSV");
        subitem2 = new JMenuItem("JSON");

        menuBar.add(menu1);
        menuBar.add(menu2);
        menu1.add(item1);
        menu1.add(item2);
        item1.add(subitem1);
        item1.add(subitem2);

        setJMenuBar(menuBar);
        //item2.addActionListener(e -> Print_info.print_panel(panel));  ใช้งานได้แล้วจู้ รอpanel อยู่จู๊ ใช้เพื่อ print panel

        setSize(new Dimension(600,500));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

