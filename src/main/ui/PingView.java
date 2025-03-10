package main.ui;

import main.NetworkTools.DeviceDisplay;
import main.NetworkTools.DeviceScanner;
import main.NetworkTools.IPCalculator;
import main.NetworkTools.InternalNetwork;
import main.NetworkTools.IPValidator;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PingView extends JInternalFrame implements ActionListener , DeviceDisplay {

    private JPanel top, bot, ip, scn, blank;
    private JScrollPane scroll;
    private JTable table;
    private JLabel ipRange;
    private JButton runButton, settingsButton;
    private JTextField ipField;
    private JProgressBar progressBar;
    private GridBagConstraints gbc;
    private DefaultTableModel tableModel;
    private TableColumn col0, col1, col2;

    private int scanCount;
    private int currentCount;
    IPCalculator ipCalculator;
    DeviceScanner scanner;

    public PingView() {
        super("Ping", false, true, false, false);

        ipCalculator = new IPCalculator(InternalNetwork.getIP(), "255.255.255.0");

        blank = new JPanel();

        top = new JPanel();
        top.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 15, 5, 15);

        ip = new JPanel();
        ip.setLayout(new FlowLayout(FlowLayout.LEFT));
        ipRange = new JLabel("IP Address:");
        ipField = new JTextField(31);
        ipField.setText(ipCalculator.getFirstIP()+"-"+ipCalculator.getLastIP());
        ip.add(ipRange);
        ip.add(ipField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        top.add(ip, gbc);

        scn = new JPanel();
        scn.setLayout(new FlowLayout(FlowLayout.RIGHT));
        runButton = new JButton("Run");
        runButton.addActionListener(this);
        settingsButton = new JButton("Settings");
        scn.add(settingsButton);
        scn.add(runButton);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        top.add(scn, gbc);

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        top.add(progressBar, gbc);
        top.setBorder(new EmptyBorder(20, 15, 20, 15));

        bot = new JPanel();
        bot.setBorder(new CompoundBorder(new EmptyBorder(4, 4, 2, 4), BorderFactory.createTitledBorder("Results")));
        bot.setLayout(new FlowLayout());

        String[] columnNames = {"", "Round", "Results"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        tableModel.addRow(new Object[]{"*", "", "", ""});

        scroll = new JScrollPane(table);
        col0 = table.getColumnModel().getColumn(0);
        col0.setPreferredWidth(51);
        col1 = table.getColumnModel().getColumn(1);
        col1.setPreferredWidth(133);
        col2 = table.getColumnModel().getColumn(2);
        col2.setPreferredWidth(133);

        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        scroll.setPreferredSize(new Dimension(450, 240)); // ป้องกันตารางล้น
        table.setPreferredScrollableViewportSize(scroll.getPreferredSize());

        bot.add(scroll);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(bot, BorderLayout.CENTER);
        setSize(new Dimension(520, 480));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void addDevice(String ip, String mac, String hostname) {

    }

}
