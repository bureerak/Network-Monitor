package main;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application extends JFrame implements ActionListener {

    JButton btn1, btn2, btn3;
    JTextField text1;

    public Application() {
        btn1 = new JButton("Dark");
        btn2 = new JButton("Light");
        btn3 = new JButton("Exit");
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        text1 = new JTextField();
        this.add(btn1);
        this.add(btn2);
        this.add(btn3);
        this.add(text1);
        this.setLayout(new FlowLayout());
        this.setTitle("IS-Scanner");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(1200, 700));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Application();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn3)){
            System.exit(0);
        } else if (e.getSource().equals(btn1)) {
            try {
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource().equals(btn2)) {
            try {
                UIManager.setLookAndFeel(new FlatMacLightLaf());
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
