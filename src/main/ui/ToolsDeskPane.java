package main.ui;

import javax.swing.*;
import java.awt.*;

public class ToolsDeskPane {
    JFrame MFrame;
    JDesktopPane desktop;
    HomeViews HView;
    public ToolsDeskPane() {
        desktop = new JDesktopPane();
        HView = new HomeViews();
        MFrame = new JFrame("Hub | Network Monitor");

        desktop.add(HView);
        MFrame.add(desktop);

        Dimension dm = new Dimension(1280,800);
        MFrame.setSize(dm);

        HView.setLocation((dm.width - HView.getWidth()) / 2 , (dm.height - HView.getHeight()) / 2);
        HView.setVisible(true);

        MFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MFrame.setLocationRelativeTo(null);
        MFrame.setVisible(true);
    }
}
