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
        MFrame.setSize(new Dimension(1000,600));
        MFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MFrame.setLocationRelativeTo(null);
        MFrame.setVisible(true);
    }
}
