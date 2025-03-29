package main.ui.networktools;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class PingSettingsView extends JInternalFrame implements ActionListener {

    private JPanel fr, top, bot;
    private JSpinner pcSpn, toSpn;
    private JLabel pc, to, times, ms;
    private JButton apply, cancel;
    private GroupLayout topL;
    private SpinnerNumberModel pcnum, tonum;
    private PingView pingView;
    private JDesktopPane desktopPane;

    public PingSettingsView(JDesktopPane desktopPane, PingView pingView) {
        super("Ping Settings", false, true, false, false);
        this.desktopPane = desktopPane;
        this.pingView = pingView;

        fr = new JPanel();
        pc = new JLabel("    Ping Count:");
        to = new JLabel("    Timeout:");
        times = new JLabel("      times.");
        ms = new JLabel("      ms.");
        pcnum = new SpinnerNumberModel(5, 0, 1000000, 1);
        tonum = new SpinnerNumberModel(100, 0, 1000000, 1);
        pcSpn = new JSpinner(pcnum);
        toSpn = new JSpinner(tonum);
        apply = new JButton("Apply");
        apply.addActionListener(this);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);

        fr.setBorder(new CompoundBorder(new EmptyBorder(4, 4, 2, 4), BorderFactory.createTitledBorder("Ping Settings")));
        fr.setLayout(new BorderLayout());
        top = new JPanel();
        topL = new GroupLayout(top);
        top.setLayout(topL);
        topL.setHorizontalGroup(
                topL.createSequentialGroup()
                        .addGroup(topL.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(pc, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addComponent(to, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                        .addGroup(topL.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(pcSpn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addComponent(toSpn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                        .addGroup(topL.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(times, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ms, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
        );
        topL.setVerticalGroup(
                topL.createSequentialGroup()
                        .addGroup(topL.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(pc, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addComponent(pcSpn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addComponent(times, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                        .addGroup(topL.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(to, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addComponent(toSpn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ms, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
        );
        bot = new JPanel();
        bot.setLayout(new FlowLayout(FlowLayout.CENTER));
        bot.add(apply);
        bot.add(cancel);

        fr.add(top, BorderLayout.NORTH);
        fr.add(bot, BorderLayout.CENTER);

        add(fr);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cancel)) {
            dispose();
        } else if (e.getSource().equals(apply)) {
            int pingCount = ((Integer) pcSpn.getValue()).intValue();
            int timeout = ((Integer) toSpn.getValue()).intValue();
            pingView.setPingCount(pingCount);
            pingView.setTimeout(timeout);

            dispose();
        }
    }

    public int getPingCount() {
        return ((Integer) pcSpn.getValue()).intValue();
    }

    public int getTimeout() {
        return ((Integer) toSpn.getValue()).intValue();
    }
}
