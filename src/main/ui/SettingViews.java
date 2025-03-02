package main.ui;
import main.database.JDBCLogin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class SettingViews extends JInternalFrame implements ActionListener {
    private JPanel dbP, msP;
    private JLabel userLabel, passLabel, miLabel;
    private GroupLayout dbLayout, msLayout;
    private JPasswordField password, username;
    private JComboBox<String> Interval;
    private JButton apply1Btn, apply2Btn;
    public SettingViews(){
        super("Setting | Network Monitor",false,true,false,false);

        dbP = new JPanel();
        dbP.setBorder(new CompoundBorder(new EmptyBorder(4,4,2,4),BorderFactory.createTitledBorder("Database Settings")));
        dbLayout = new GroupLayout(dbP);
        dbP.setLayout(dbLayout);
        dbLayout.setAutoCreateGaps(true);
        dbLayout.setAutoCreateContainerGaps(true);

        userLabel = new JLabel("New Password:");
        username = new JPasswordField();
        passLabel = new JLabel("Retype Password:");
        password = new JPasswordField();
        apply1Btn = new JButton("Apply");

        apply1Btn.addActionListener(this);

        dbLayout.setHorizontalGroup(
                dbLayout.createSequentialGroup()
                        .addGap(30)
                        .addGroup(dbLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(userLabel)
                                .addComponent(passLabel))
                        .addGroup(dbLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(username)
                                .addComponent(password)
                                .addComponent(apply1Btn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                        .addGap(30));

        dbLayout.setVerticalGroup(
                dbLayout.createSequentialGroup()
                        .addGap(30)
                        .addGroup(dbLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(userLabel)
                                .addComponent(username))
                        .addGap(6)
                        .addGroup(dbLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(passLabel)
                                .addComponent(password))
                        .addGap(6)
                        .addComponent(apply1Btn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                        .addGap(15));

        msP = new JPanel();
        msP.setBorder(new CompoundBorder(new EmptyBorder(2,4,4,4),BorderFactory.createTitledBorder("Monitoring Settings")));
        msLayout = new GroupLayout(msP);
        msP.setLayout(msLayout);
        msLayout.setAutoCreateGaps(true);
        msLayout.setAutoCreateContainerGaps(true);

        miLabel = new JLabel("Monitor Interval:");
        Interval = new JComboBox<>(new String[]{"1 minute","5 minutes", "10 minutes", "15 minutes"});
        apply2Btn = new JButton("Apply");
        apply2Btn.addActionListener(this);

        msLayout.setHorizontalGroup(
                msLayout.createSequentialGroup()
                        .addGap(30)
                        .addGroup(msLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(miLabel))
                        .addGroup(msLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(Interval)
                                .addComponent(apply2Btn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                        .addGap(30));

        msLayout.setVerticalGroup(
                msLayout.createSequentialGroup()
                        .addGap(15)
                        .addGroup(msLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(miLabel)
                                .addComponent(Interval))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(apply2Btn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                        .addGap(15));

        setLayout(new BorderLayout());
        add(dbP, BorderLayout.CENTER);
        add(msP, BorderLayout.SOUTH);
        setSize(420, 345);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(apply1Btn)) {
            if (Arrays.equals(username.getPassword(), password.getPassword()) && !String.valueOf(username.getPassword()).isBlank() && !String.valueOf(username.getPassword()).contains(" ") ) {
                if (JDBCLogin.changePass(password.getPassword())) {
                    JOptionPane.showMessageDialog(this,"Password has been successfully changed.","",JOptionPane.INFORMATION_MESSAGE);
                    username.setText("");
                    password.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this,"Both passwords should match or not be blank.","",JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource().equals(apply2Btn)) {
            HomeViews.setIntervalNumber(Interval.getSelectedItem().toString());
        }
    }
}