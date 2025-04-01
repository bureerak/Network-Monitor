package main.ui.networktools;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


import main.NetworkTools.InternalNetwork;
import main.NetworkTools.IPCalculator;
import main.exception.InvalidIPException;

public class IpAddress extends JInternalFrame implements ActionListener {
    private JPanel main , panel1,panel2,panel3,panel4, results ,re_ipaddress,re_subnet,re_network,re_broadcast,re_wildcard,re2_ipaddress,re2_subnet,re2_network,re2_broadcast,re2_wildcard ,re2_iprange,resultLabelContainer,iprangeLabelContainer,resultTextFieldContainer,ipLabelContainer,subLabelContainer;
    private JTextField tf_ip, tf_subnet , ipAddressField1,subnetMaskField1,networkField1, wildcardField1 , broadcastField1,ipAddressField2,subnetMaskField2,networkField2,wildcardField2,broadcastField2,iprangeField1,iprangeField2;
    private JLabel lb_ip, lb_subnet ,iplabel, subnetlabel , networklabel,broadcastlabel,wildcardlabel,iprangelabell,usablelabell ;

    private JButton calculate;
    public IpAddress() {
        super("IP Calculator",false,true,false,false);
        setSize(500, 580);
        main = new JPanel(new BorderLayout());
        //ส่วนแรก
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(3,1));
        lb_ip = new JLabel("IP Address: ");
        lb_subnet = new JLabel("Subnet Mask: ");
        tf_ip = new JTextField(28);
        tf_subnet = new JTextField(28);
        panel2 = new JPanel(new BorderLayout());
        panel3 = new JPanel(new BorderLayout());
        panel4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Icon.jpg")));;
        Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        setFrameIcon(new ImageIcon(scaledImage));
        calculate = new JButton("Calculate");
        ipLabelContainer = new JPanel();
        ipLabelContainer.setBorder(new CompoundBorder(new EmptyBorder(0,0,0,13),null));
        subLabelContainer = new JPanel();
        ipLabelContainer.add((lb_ip));
        subLabelContainer.add(lb_subnet);

        panel2.add(ipLabelContainer,BorderLayout.WEST);
        panel2.add(tf_ip,BorderLayout.CENTER);
        panel3.add(subLabelContainer,BorderLayout.WEST);
        panel3.add(tf_subnet,BorderLayout.CENTER);
        panel4.add(calculate );
        panel4.setBorder(new EmptyBorder(0, 0, 0, 12));
        panel1.setBorder(new EmptyBorder(20, 20, 5, 20));
        panel1.add(panel2);
        panel1.add(panel3);
        panel1.add(panel4,BorderLayout.EAST);

        //ส่วนสอง
        results = new JPanel(new BorderLayout());
        results.setBorder(new CompoundBorder(new EmptyBorder(7,7,7,7),BorderFactory.createTitledBorder("Results")));
        re_ipaddress = new JPanel(new BorderLayout());
        re_subnet = new JPanel(new BorderLayout());
        re_network = new JPanel(new BorderLayout());
        re_broadcast = new JPanel(new BorderLayout());
        re_wildcard = new JPanel(new BorderLayout());

        re2_ipaddress = new JPanel(new GridLayout(2,1));
        re2_subnet = new JPanel(new GridLayout(2,1));
        re2_network = new JPanel(new GridLayout(2,1));
        re2_broadcast = new JPanel(new GridLayout(2,1));
        re2_wildcard = new JPanel(new GridLayout(2,1));
        re2_iprange = new JPanel(new GridLayout(2,1));

        iplabel = new JLabel("IP Address:   ");
        subnetlabel = new JLabel("Subnet Mask:   ");
        networklabel = new JLabel("Network:    ");
        broadcastlabel = new JLabel("Broadcast:   ");
        wildcardlabel = new JLabel("Wildcard:   ");
        iprangelabell = new JLabel("IP Range:");
        usablelabell = new JLabel("Usable IP:");
        //text field
        ipAddressField1 = new JTextField();
        ipAddressField1.setEditable(false);
        ipAddressField1.setBackground(null);
        subnetMaskField1 = new JTextField();
        subnetMaskField1.setEditable(false);
        subnetMaskField1.setBackground(null);
        networkField1 = new JTextField();
        networkField1.setEditable(false);
        networkField1.setBackground(null);
        wildcardField1 = new JTextField();
        wildcardField1.setEditable(false);
        wildcardField1.setBackground(null);
        broadcastField1 = new JTextField();
        broadcastField1.setEditable(false);
        broadcastField1.setBackground(null);
        iprangeField1 = new JTextField();
        iprangeField1.setEditable(false);
        iprangeField1.setBackground(null);
        iprangeField2 = new JTextField();
        iprangeField2.setEditable(false);
        iprangeField2.setBackground(null);
        ipAddressField2 = new JTextField();
        ipAddressField2.setEditable(false);
        ipAddressField2.setBackground(null);

        subnetMaskField2 = new JTextField();
        subnetMaskField2.setEditable(false);
        subnetMaskField2.setBackground(null);
        networkField2 = new JTextField();
        networkField2.setEditable(false);
        networkField2.setBackground(null);
        wildcardField2 = new JTextField();
        wildcardField2.setEditable(false);
        wildcardField2.setBackground(null);
        broadcastField2 = new JTextField();
        broadcastField2.setEditable(false);
        broadcastField2.setBackground(null);

        //settext
        re2_ipaddress.add(ipAddressField1);
        re2_ipaddress.add(ipAddressField2);
        re2_subnet.add(subnetMaskField1);
        re2_subnet.add(subnetMaskField2);
        re2_network.add(networkField1);
        re2_network.add(networkField2);
        re2_broadcast.add(broadcastField1);
        re2_broadcast.add(broadcastField2);
        re2_wildcard.add(wildcardField1);
        re2_wildcard.add(wildcardField2);
        re2_iprange.add(iprangeField1);
        re2_iprange.add(iprangeField2);



        re_ipaddress.add(iplabel,BorderLayout.NORTH);
        re_subnet.add(subnetlabel,BorderLayout.NORTH);
        re_network.add(networklabel,BorderLayout.NORTH);
        re_broadcast.add(broadcastlabel,BorderLayout.NORTH);
        re_wildcard.add(wildcardlabel,BorderLayout.NORTH);
        //label
        resultLabelContainer = new JPanel(new GridLayout(6,1));
        resultLabelContainer.setBorder(new CompoundBorder(new EmptyBorder(6,5,0,2),null));
        results.add(resultLabelContainer,BorderLayout.WEST);
        resultLabelContainer.add(re_ipaddress);
        resultLabelContainer.add(re_subnet);
        resultLabelContainer.add(re_network);
        resultLabelContainer.add(re_broadcast);
        resultLabelContainer.add(re_wildcard);
        iprangeLabelContainer = new JPanel(new GridLayout(2,1));
        iprangeLabelContainer.add(iprangelabell);
        iprangeLabelContainer.add(usablelabell);
        resultLabelContainer.add(iprangeLabelContainer);
        //textfield
        resultTextFieldContainer = new JPanel(new GridLayout(6, 1));
        resultTextFieldContainer.setBorder(new CompoundBorder(new EmptyBorder(0,2,2,5),null));
        resultTextFieldContainer.add(re2_ipaddress);
        resultTextFieldContainer.add(re2_subnet);
        resultTextFieldContainer.add(re2_network);
        resultTextFieldContainer.add(re2_broadcast);
        resultTextFieldContainer.add(re2_wildcard);
        resultTextFieldContainer.add(re2_iprange);
        results.add(resultTextFieldContainer,BorderLayout.CENTER);


        //test
        main.add(panel1,BorderLayout.NORTH); //ให้ก้อนบนในรูปไปเกาะด้านบน
        main.add(results);// ให้อยู่ตรงกลางจะได้ยืนได้รอบด้าน
        add(main);

        tf_ip.setText(InternalNetwork.getIP());
        tf_subnet.setText("255.255.255.0");
        calculate.addActionListener(this);
        setLocation(650, 8);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculate) {
            IPCalculator calc;
            try {
                calc = new IPCalculator(tf_ip.getText(), tf_subnet.getText());

            } catch (InvalidIPException ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage(),"",JOptionPane.ERROR_MESSAGE);
                return;
            }
            ipAddressField1.setText(calc.getIP());
            ipAddressField2.setText(calc.getBinaryIP());
            subnetMaskField1.setText(calc.getSubnetMask());
            subnetMaskField2.setText(calc.getBinarySubnet());
            networkField1.setText(calc.getNetworkID());
            networkField2.setText(calc.getBinaryNetwork());
            broadcastField1.setText(calc.getBroadcastIP());
            broadcastField2.setText(calc.getBinaryBroadcast());
            wildcardField1.setText(calc.getWildcardIP());
            wildcardField2.setText(calc.getBinaryWildcard());
            iprangeField1.setText(calc.getFirstIP()+"-"+calc.getLastIP());
            iprangeField2.setText(String.valueOf(calc.getUsableIP()));
        }

    }

}
