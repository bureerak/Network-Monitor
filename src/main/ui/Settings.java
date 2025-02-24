package main.ui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Settings extends JInternalFrame implements ActionListener {
    private JPanel dbP, msP;
    private JLabel hostLabel, userLabel, passLabel, miLabel;
    private GroupLayout dbLayout, msLayout;
    private JTextField host, username;
    private JPasswordField password;
    private JComboBox<String> Interval;
    private JButton apply1Btn, apply2Btn;
    public Settings(){
        super("Setting | Network Monitor",false,true,false,false);

        dbP = new JPanel();
        dbP.setBorder(BorderFactory.createTitledBorder("Database Settings"));
        dbLayout = new GroupLayout(dbP);
        dbP.setLayout(dbLayout);
        dbLayout.setAutoCreateGaps(true);
        dbLayout.setAutoCreateContainerGaps(true);

        hostLabel = new JLabel("Host:");
        host = new JTextField();
        userLabel = new JLabel("Username:");
        username = new JTextField();
        passLabel = new JLabel("Password:");
        password = new JPasswordField();
        apply1Btn = new JButton("Apply");

        dbLayout.setHorizontalGroup(
                dbLayout.createSequentialGroup()
                        .addGap(30)
                        .addGroup(dbLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(hostLabel)
                                .addComponent(userLabel)
                                .addComponent(passLabel))
                        .addGroup(dbLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(host)
                                .addComponent(username)
                                .addComponent(password)
                                .addComponent(apply1Btn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                        .addGap(30));

        dbLayout.setVerticalGroup(
                dbLayout.createSequentialGroup()
                        .addGap(15)
                        .addGroup(dbLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(hostLabel)
                                .addComponent(host))
                        .addGap(6)
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
        msP.setBorder(BorderFactory.createTitledBorder("Monitoring Settings"));
        msLayout = new GroupLayout(msP);
        msP.setLayout(msLayout);
        msLayout.setAutoCreateGaps(true);
        msLayout.setAutoCreateContainerGaps(true);

        miLabel = new JLabel("Monitor Interval:");
        Interval = new JComboBox<>(new String[]{"1 minute", "2 minutes", "3 minutes"});
        apply2Btn = new JButton("Apply");

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
    }
}