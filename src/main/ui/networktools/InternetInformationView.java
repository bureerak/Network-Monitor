package main.ui.networktools;

import main.NetworkTools.ExternalNetwork;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InternetInformationView extends JInternalFrame {
    public InternetInformationView(){
        super("Internet Information",false,true,false,false);
        ExternalNetwork externalNetwork = new ExternalNetwork();
        ImageIcon icon = new ImageIcon("resources/Icon.jpg");
        Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        setFrameIcon(new ImageIcon(scaledImage));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder("Internet Information"),
                BorderFactory.createEmptyBorder(10, 30, 10, 10)
        ));

        JLabel ipLabel = new JLabel("IP: " + externalNetwork.getIP());
        JLabel ispLabel = new JLabel("ISP: " + externalNetwork.getISP());
        JLabel countryLabel = new JLabel("Country: " + externalNetwork.getCountry());
        JLabel cityLabel = new JLabel("City: " + externalNetwork.getCity());

        ipLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ispLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        countryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        cityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(ipLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
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