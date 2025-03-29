package main.ui.networktools;

import main.NetworkTools.*;
import main.exception.InvalidPortException;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PortScannerView extends JInternalFrame implements ActionListener, PortDisplay, InternalFrameListener {
    private JPanel top, bot, ipports, scan;
    private JScrollPane scroll;
    private JTable table;
    private JLabel ipLabel, portLabel;
    private JButton scanButton;
    private JTextField ipField, portField;
    private JProgressBar progressBar;
    private DefaultTableModel tableModel;
    private int scanCount;
    private int currentCount;
    private PortScanner scanner;

    public PortScannerView() {
        super("Port Scanner", false, true, false, false);
        setLayout(new BorderLayout());

        top = new JPanel(new BorderLayout());
        top.setBorder(new EmptyBorder(20, 25, 0, 25));

        ipports = new JPanel();
        ipLabel = new JLabel("IP Address:");
        ipField = new JTextField(31);
        ipField.setText(InternalNetwork.getIP());

        portLabel = new JLabel("Ports:");
        portField = new JTextField(31);
        portField.setText("80, 443, 1000-2000");

        scan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        scanButton = new JButton("Scan");
        scan.add(scanButton);
        scanButton.addActionListener(this);

        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(200, 20));
        progressBar.setForeground(Color.GREEN);
        progressBar.setValue(0);

        ipports.setLayout(new GridLayout(2, 2, 5, 5));
        ipports.add(ipLabel);
        ipports.add(ipField);
        ipports.add(portLabel);
        ipports.add(portField);

        top.add(ipports, BorderLayout.NORTH);
        top.add(scan, BorderLayout.CENTER);
        top.add(progressBar, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        bot = new JPanel(new BorderLayout());
        bot.setBorder(new CompoundBorder(new EmptyBorder(25, 10, 20, 10), BorderFactory.createTitledBorder("Results")));

        String[] col = {""};
        tableModel = new DefaultTableModel(col, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // ทำให้เซลล์ทั้งหมดไม่สามารถแก้ไขได้
            }
        };
        table = new JTable(tableModel);
        table.getTableHeader().setUI(null); // Hide the table header
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(450, 240));
        bot.add(scroll, BorderLayout.CENTER);
        add(bot, BorderLayout.CENTER);
        setSize(new Dimension(550, 500));
        addInternalFrameListener(this);

    }

    public void setProgressBar(){
        this.progressBar.setValue(0);
    }

    public void setScanCount(int count) {
        this.scanCount = count;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == scanButton) {
            String ip = ipField.getText().trim();
            String port = portField.getText().trim();

            scanner = new PortScanner();
            scanButton.setEnabled(false); // Disable the scan button to prevent multiple clicks
            ipField.setEditable(false);
            portField.setEditable(false);
            progressBar.setValue(0); // Reset the ProgressBar to 0
            tableModel.setRowCount(0); // Clear table data

            SwingWorker<Void, Integer> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        scanner.scan(ip, port, PortScannerView.this);
                    } catch (InvalidPortException e) {
                        JOptionPane.showMessageDialog(null,e.getMessage(),"",JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                    currentCount++;
                    int progress = (int) ((currentCount / (double) scanCount) * 100);
                    progressBar.setValue(progress);
                    return null;
                }

                @Override
                protected void done() {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setValue(0);
                        scanButton.setEnabled(true);
                        ipField.setEditable(true);
                        portField.setEditable(true);
                    });
                }
            };
            worker.execute();
        }
    }

    @Override
    public void setPort(String ip, ArrayList<Integer> ports) {
        SwingUtilities.invokeLater(() -> {
            for (int port : ports) {
                tableModel.addRow(new Object[]{port + " is open."});
            }
        });
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
        progressBar.setValue(0);
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        ipField.setText(InternalNetwork.getIP());
        portField.setText("80, 443, 1000-2000");
        SwingUtilities.invokeLater(() -> progressBar.setValue(0));
        tableModel.setRowCount(0);
        scanButton.setEnabled(true);
        ipField.setEditable(true);
        portField.setEditable(true);
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {

    }
}
