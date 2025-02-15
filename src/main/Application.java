package main;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import main.ui.RegisterViews;
import main.ui.ToolsDeskPane;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new RegisterViews();
        new ToolsDeskPane();
    }

}
