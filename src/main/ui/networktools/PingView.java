package main.ui.networktools;

import main.NetworkTools.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class PingView extends JInternalFrame implements ActionListener, DeviceDisplay {

    private JPanel top, bot, ip, scn, blank;
    private JScrollPane scroll;
    private JTable table;
    private JLabel ipRange;
    private JButton runButton, settingsButton;
    private JTextField ipField;
    private JProgressBar progressBar;
    private GridBagConstraints gbc;
    private DefaultTableModel tableModel;

    private int pingCount = 5;
    private int timeout = 100;
    IPCalculator ipCalculator;

    public PingView() {
        super("Ping", false, true, false, false);

        ipCalculator = new IPCalculator(InternalNetwork.getIP(), "255.255.255.0");

        top = new JPanel();
        top.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 15, 5, 15);

        ip = new JPanel();
        ip.setLayout(new FlowLayout(FlowLayout.LEFT));
        ipRange = new JLabel("IP Address:");
        ipField = new JTextField(31);
        ipField.setText(ipCalculator.getFirstIP() + "-" + ipCalculator.getLastIP());
        ip.add(ipRange);
        ip.add(ipField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        top.add(ip, gbc);

        scn = new JPanel();
        scn.setLayout(new FlowLayout(FlowLayout.RIGHT));
        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(this);
        runButton = new JButton("Run");
        runButton.addActionListener(this);
        scn.add(settingsButton);
        scn.add(runButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        top.add(scn, gbc);

        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(200, 20));
        progressBar.setForeground(Color.GREEN);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        top.add(progressBar, gbc);

        bot = new JPanel();
        bot.setLayout(new FlowLayout());
        tableModel = new DefaultTableModel(new String[]{"", "Round", "Results"}, 0);
        table = new JTable(tableModel);
        scroll = new JScrollPane(table);
        bot.add(scroll);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(bot, BorderLayout.CENTER);
        setSize(new Dimension(520, 480));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == settingsButton) {
            PingSettingsView settingsView = new PingSettingsView(getDesktopPane(), this);
            getDesktopPane().add(settingsView);
            settingsView.setVisible(true);
        } else if (e.getSource() == runButton) {
            startPinging();
        }
    }

    public void startPinging() {
        String ipAddress = ipField.getText().split("-")[0];

        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < pingCount; i++) {
                    int result = Ping.runOnce(ipAddress, timeout);
                    tableModel.addRow(new Object[]{"*", "Round " + (i + 1), result});
                    int progress = (i + 1) * 100 / pingCount;
                    publish(progress);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                progressBar.setValue(progress);
            }
        };

        worker.execute();
    }

    public void setPingCount(int count) {
        this.pingCount = count;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public void addDevice(String ip, String mac, String hostname) {
    }

    @Override
    public void updateProgress(int scanned) {
    }
}
