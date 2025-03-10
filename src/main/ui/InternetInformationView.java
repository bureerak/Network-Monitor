package main.ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InternetInformationView extends JInternalFrame {
    public InternetInformationView(){

        super("Internet Information",false,true,false,false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder("Internet Information"),
                BorderFactory.createEmptyBorder(10, 30, 10, 10) // ปรับระยะห่างจากขอบ
        ));

        JLabel ipLabel = new JLabel("IP: x.x.x.x");
        JLabel ispLabel = new JLabel("ISP: xxx");
        JLabel countryLabel = new JLabel("Country: xxx");
        JLabel cityLabel = new JLabel("City: xxx");

        ipLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ispLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        countryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        cityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(ipLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // เพิ่มระยะห่างแนวตั้ง
        panel.add(ispLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(countryLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(cityLabel);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setSize(350, 190);


    }
}
