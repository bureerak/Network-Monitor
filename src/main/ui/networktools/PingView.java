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

    private JPanel top, bot, ip, scn;
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

        top = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 15, 5, 15);

        ip = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ipRange = new JLabel("IP Address:");
        ipField = new JTextField(31);
        ipField.setText(InternalNetwork.getIP());
        ip.add(ipRange);
        ip.add(ipField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        top.add(ip, gbc);

        scn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(this);
        runButton = new JButton("Run");
        runButton.addActionListener(this);
        scn.add(settingsButton);
        scn.add(runButton);

        gbc.gridy = 1;
        top.add(scn, gbc);

        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(200, 20));
        progressBar.setForeground(Color.GREEN);
        gbc.gridy = 2;
        top.add(progressBar, gbc);

        bot = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new String[]{"", "Round", "Results"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        scroll = new JScrollPane(table);
        bot.add(scroll, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(bot, BorderLayout.CENTER);
        setSize(new Dimension(520, 480));
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            progressBar.setValue(0);
            tableModel.setRowCount(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(settingsButton)) {
            PingSettingsView settingsView = new PingSettingsView(getDesktopPane(), this);
            getDesktopPane().add(settingsView);
            settingsView.setVisible(true);
        } else if (e.getSource().equals(runButton)) {
            startPinging();
        }
    }

    public void startPinging() {
        String ipAddress = ipField.getText();

        tableModel.setRowCount(0);
        progressBar.setValue(0);

        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
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
                progressBar.setValue(chunks.get(chunks.size() - 1));
            }

            @Override
            protected void done() {
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    TableColumn column = table.getColumnModel().getColumn(i);
                    column.setPreferredWidth(100);
                }
                scroll.revalidate();
                scroll.repaint();
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
}
