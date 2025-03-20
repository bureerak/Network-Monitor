package main;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import main.ui.LoginView;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatMacLightLaf());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginView();
            //new ToolsDeskPane();
        });
    }

}
