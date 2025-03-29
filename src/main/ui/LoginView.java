package main.ui;

import main.database.JDBCLogin;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class LoginView implements ActionListener, KeyListener {
    JFrame frame;
    JLabel label;
    ImageIcon img;
    JPanel panel;
    JButton btnLogin, btnCancel;
    JTextField userText;
    JPasswordField pass;
    public LoginView(){
        img = new ImageIcon(Objects.requireNonNull(getClass().getResource("/login.jpg")));
        label = new JLabel(img);
        btnCancel = new JButton("Cancel"); btnCancel.addActionListener(this);
        btnLogin = new JButton("Login"); btnLogin.addActionListener(this);

        JLabel welcomeText = new JLabel(" Taravichet at your service ");
        welcomeText.setFont(new Font( "Arial",Font.BOLD,22));
        welcomeText.setBorder(new EmptyBorder(8,0,0,0));

        panel = new JPanel();
        Border margin = new EmptyBorder(7,7,7,7);
        panel.setBorder(new CompoundBorder(margin,BorderFactory.createTitledBorder("Login")));
        panel.setLayout(new GridLayout(3,1,2,2));

        JLabel userLabel = new JLabel("Username:");
        userText = new JTextField(24);
        JLabel passLabel = new JLabel("Password:");
        pass = new JPasswordField(24);

        JPanel subPane1 = new JPanel();
        JPanel subPane2 = new JPanel();
        JPanel midPane = new JPanel();
        JPanel topPane = new JPanel();
        JPanel bottomPane = new JPanel();

        bottomPane.setLayout(new GridLayout(1,2));

        topPane.add(welcomeText);

        subPane1.add(userLabel);
        subPane1.add(userText);
        subPane2.add(passLabel);
        subPane2.add(pass);

        midPane.setLayout(new GridLayout(2,1));
        midPane.add(subPane1);
        midPane.add(subPane2);

        JPanel btmRightPane = new JPanel();
        btmRightPane.setBorder(new EmptyBorder(12,0,0,4));
        btmRightPane.add(btnLogin);
        btmRightPane.add(btnCancel);

        bottomPane.add(new JPanel());
        bottomPane.add(btmRightPane);

        panel.add(topPane);
        panel.add(midPane);
        panel.add(bottomPane);

        userText.addKeyListener(this);
        pass.addKeyListener(this);
        btnLogin.addKeyListener(this);
        btnCancel.setFocusable(false);

        frame = new JFrame("Login | Network Monitor");
        JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, label, panel);
        jsp.setDividerLocation(170);
        jsp.setDividerSize(0);
        jsp.setResizeWeight(0.5);
        jsp.setEnabled(false);
        ImageIcon icon = new ImageIcon("resources/Icon.jpg");
        frame.setIconImage(icon.getImage());

        frame.add(jsp);

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(420,440));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if ( src.equals(btnLogin) ) {
            if ( JDBCLogin.loginJDBC(userText.getText(), pass.getPassword()) ) {
                SwingUtilities.invokeLater(() -> {
                    new DesktopPane();
                    frame.dispose();
                });
            } else {
                JOptionPane.showMessageDialog(frame,"login failed", "Alert",JOptionPane.ERROR_MESSAGE);
            }
        } else if ( src.equals(btnCancel) ){
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            btnLogin.requestFocus();
            if ( JDBCLogin.loginJDBC(userText.getText(), pass.getPassword()) ) {
                SwingUtilities.invokeLater(() -> {
                    new DesktopPane();
                    frame.dispose();
                });
            } else {
                JOptionPane.showMessageDialog(frame,"login failed", "Alert",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
