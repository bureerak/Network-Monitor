package main.ui;

import main.database.JDBCProfileEdit;
import main.database.UserPreference;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Objects;

public class DesktopPane implements WindowListener {
    JFrame MFrame;
    JDesktopPane desktop;
    HomeViews HView;
    public DesktopPane() {
        desktop = new JDesktopPane();
        HView = new HomeViews();
        MFrame = new JFrame("Network Monitor");
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Icon.jpg")));;
        MFrame.setIconImage(icon.getImage());
        desktop.add(HView);
        MFrame.add(desktop);

        Dimension dm = new Dimension(1280,800);
        MFrame.setSize(dm);
        HView.setVisible(true);

        MFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MFrame.addWindowListener(this);
        MFrame.setLocationRelativeTo(null);
        MFrame.setVisible(true);
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    /**
     * clear Select from all table then closed
     * @param e the event to be processed
     */
    @Override
    public void windowClosing(WindowEvent e) {
        JDBCProfileEdit.unselectProfile();
        UserPreference.setStatus(UserPreference.IDLE);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
