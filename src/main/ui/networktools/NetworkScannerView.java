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

public class NetworkScannerView extends JInternalFrame implements ActionListener, DeviceDisplay {

    private JPanel top, bot, ip, scn;
    private JScrollPane scroll;
    private JTable table;
    private JLabel ipRange;
    private JButton scanButton;
    private JTextField ipField;
    private JProgressBar progressBar;
    private GridBagConstraints gbc;
    private DefaultTableModel tableModel;
    private TableColumn col0, col1, col2, col3;

    private int currentCount;
    IPCalculator ipCalculator;
    DeviceScanner scanner;

    public NetworkScannerView() {
        super("Network Scanner", false, true, false, false);
        ImageIcon icon = new ImageIcon("resources/Icon.jpg");
        Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        setFrameIcon(new ImageIcon(scaledImage));
        ipCalculator = new IPCalculator(InternalNetwork.getIP(), "255.255.255.0");

        top = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 15, 5, 15);

        ip = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ipRange = new JLabel("IP Range:");
        ipField = new JTextField(31);
        ipField.setText(ipCalculator.getFirstIP() + "-" + ipCalculator.getLastIP());
        ip.add(ipRange);
        ip.add(ipField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        top.add(ip, gbc);

        scn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        scanButton = new JButton("Scan");
        scanButton.addActionListener(this);
        scn.add(scanButton);

        gbc.gridy = 1;
        top.add(scn, gbc);

        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(200, 20));
        progressBar.setForeground(Color.GREEN);

        gbc.gridy = 2;
        top.add(progressBar, gbc);
        top.setBorder(new EmptyBorder(20, 15, 20, 15));

        bot = new JPanel(new FlowLayout());
        bot.setBorder(new CompoundBorder(new EmptyBorder(4, 4, 2, 4), BorderFactory.createTitledBorder("Results")));

        String[] columnNames = {"", "IP", "MAC Address", "Hostname"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        tableModel.addRow(new Object[]{"", "", "", ""});

        scroll = new JScrollPane(table);
        col0 = table.getColumnModel().getColumn(0);
        col0.setPreferredWidth(51);
        col1 = table.getColumnModel().getColumn(1);
        col1.setPreferredWidth(133);
        col2 = table.getColumnModel().getColumn(2);
        col2.setPreferredWidth(133);
        col3 = table.getColumnModel().getColumn(3);
        col3.setPreferredWidth(133);

        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        scroll.setPreferredSize(new Dimension(450, 240));
        table.setPreferredScrollableViewportSize(scroll.getPreferredSize());

        bot.add(scroll);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(bot, BorderLayout.CENTER);
        setSize(new Dimension(520, 480));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(scanButton)) {
            String ipInput = ipField.getText();
            String[] range = ipInput.split("-");
            if (range.length < 2 ||
                    !IPValidator.isValidIPv4(range[0]) ||
                    !IPValidator.isValidIPv4(range[1])) {
                JOptionPane.showMessageDialog(this, "Invalid Input IP", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            clearTable();
            setCurrentCount();

            scanner = new DeviceScanner();

            progressBar.setIndeterminate(true);
            ipField.setEditable(false);
            scanButton.setEnabled(false);

            new Thread(() -> {
                scanner.scan(ipInput, this);
                SwingUtilities.invokeLater(() -> {
                    scanButton.setEnabled(true);
                    ipField.setEditable(true);
                    progressBar.setIndeterminate(false);
                });
            }).start();
        }
    }

    public void clearTable() {
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{"", "", "", ""});
    }

    @Override
    public void addDevice(String ip, String mac, String hostname) {
        SwingUtilities.invokeLater(() -> {
            if (tableModel.getRowCount() == 1 && tableModel.getValueAt(0, 1).equals("")) {
                tableModel.removeRow(0);
            }
            Object[] row = {currentCount, ip, mac, hostname};
            tableModel.addRow(row);
            currentCount++;
        });
    }

    @Override
    public void dispose() {
        progressBar.setIndeterminate(false);
        scanButton.setEnabled(true);
        ipField.setEditable(true);
        super.dispose();
    }

    public void setCurrentCount() {
        this.currentCount = 1;
    }
}