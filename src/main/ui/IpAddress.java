package main.ui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;


import main.NetworkTools.InternalNetwork;
import main.NetworkTools.IPCalculator;

public class IpAddress extends JInternalFrame implements ActionListener {
    private JPanel main , panel1,panel2,panel3,panel4, results ,re_ipaddress,re_subnet,re_network,re_broadcast,re_wildcard,re2_ipaddress,re2_subnet,re2_network,re2_broadcast,re2_wildcard ,re_iprange,re2_iprange,pllabel;
    private JTextField tf_ip, tf_subnet , ipAddressField1,subnetMaskField1,networkField1, wildcardField1 , broadcastField1,ipAddressField2,subnetMaskField2,networkField2,wildcardField2,broadcastField2,iprangeField1,iprangeField2;
    private JLabel lb_ip, lb_subnet ,iplabel, subnetlabel , networklabel,broadcastlabel,wildcardlabel ;

    private JButton calculate;
    public IpAddress() {
        super("IP Calculator",false,true,false,false);
        setSize(500, 580);
        main = new JPanel();
        main.setLayout(new BorderLayout());
        //ส่วนแรก
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(3,1));
        lb_ip = new JLabel("IP Address: ");
        lb_subnet = new JLabel("Subnet Mask: ");
        tf_ip = new JTextField(29);
        tf_subnet = new JTextField(28);
        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        panel3 = new JPanel();
        panel3.setLayout(new FlowLayout());
        panel4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        calculate = new JButton("Calculate");
        panel2.add(lb_ip);
        panel2.add(tf_ip);
        panel3.add(lb_subnet);
        panel3.add(tf_subnet);
        panel4.add(calculate );
        panel4.setBorder(new EmptyBorder(0, 0, 0, 12));
        panel1.setBorder(new EmptyBorder(20, 20, 5, 20));
        panel1.add(panel2);
        panel1.add(panel3);
        panel1.add(panel4,BorderLayout.EAST);

        //ส่วนสอง
        results = new JPanel(new GridLayout(6,1));
        results.setBorder(new CompoundBorder(new EmptyBorder(7,7,7,7),BorderFactory.createTitledBorder("Results")));
        re_subnet = new JPanel(new GridLayout(2,1));
        re_network = new JPanel(new GridLayout(2,1));
        re_broadcast = new JPanel(new GridLayout(2,1));
        re_wildcard = new JPanel(new GridLayout(2,1));
        re_iprange = new JPanel(new GridLayout(2,1));

        re2_ipaddress = new JPanel(new GridLayout(2,1));
        re2_subnet = new JPanel(new GridLayout(2,1));
        re2_network = new JPanel(new GridLayout(2,1));
        re2_broadcast = new JPanel(new GridLayout(2,1));
        re2_wildcard = new JPanel(new GridLayout(2,1));
        re2_iprange = new JPanel(new GridLayout(2,1));

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

        //label
        iplabel = new JLabel("IP Address:   ");
        subnetlabel = new JLabel("Subnet Mask:   ");
        networklabel = new JLabel("Network:    ");
        broadcastlabel = new JLabel("Broadcast:   ");
        wildcardlabel = new JLabel("Wildcard:   ");

        pllabel = new JPanel(new BorderLayout());
        pllabel.add(iplabel, BorderLayout.NORTH);
        //ใช้ setLayout สำหรับPanel แต่ละตัวของ result
        re_ipaddress = new JPanel(new GridBagLayout());//ทำเป็น gridbag gridbag จะทำงานตามแนวแกน x y
        GridBagConstraints gbc = new GridBagConstraints();//ประกาศตัวที่ใช้สำหรับการปรับแต่งของที่จะใส่ใน gridbag
        gbc.gridx = 0; // กำหนดว่าใช้ตัวที่ค่า x = 0, โดยที่ค่า y ก็จะเริ่มที่ 0
        gbc.weightx = 0.1; // กำหนด scale ตามแนวแกน x ของ panel (เช่นจาก 100% -> 10%(0.1))
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL; //กำหนดให้เต็มไปในแนวไหน เช่นอันนี้ไปในแนวนอน
        re_ipaddress.add(pllabel,gbc); //เพิ่มค่าตัวแรกเข้าไปก็คือ label คำว่า ipaddress : โดยที่ใส่ gbc ที่เราปรับแต่งไว้ไปด้วย
        //หลังจากใส่ตัวที่ 1 แล้วเราก็จะต้องปรับ gbc ใหม่เพื่อให้มันตรงกับสิ่งที่เราต้องการให้ตัวถัดไปเป็นเช่นขนาด หรือตำแหน่ง
        gbc.gridx = 1; // ทำการปรับแต่สำหรับตัวต่อไป โดยที่รอบที่ x = 1 นึกภาพตามก็จะเป็น แนวแกน x มี 0 1 2 อันนี้คือสำหรับใส่ 3 ตัวแต่ถ้าเราจะใส่ 2 ตัวใน layout นี้ก็จะเป็น 0 1 รอบนี้เรากำหนดให้มันไปตำแหน่งที่ 1 หรือก็คือขวาของ label
        gbc.weightx = 1.2; // กำหนดค่า scale ตามแนว x เหมือนเดิมโดยที่รอบที่ให้ 1 ไปหรือก็คือมากสุดเท่าที่จะเป็นไปได้
        gbc.fill = GridBagConstraints.HORIZONTAL;// กำหนดให้ไปแนวนอน
        re_ipaddress.add(re2_ipaddress,gbc); // เพิ่่ม panel ของ textfield ที่เป็น grid row 2 ตัวเข้าไป โดยให้ใช้การปรับแต่งจากข้างบน ก็คือจะไปที่ตำแหน่งที่ x = 1 หรือก็คือขวาของ label
        re_ipaddress.setBorder(new EmptyBorder(12, 20, 2, 20));
        // re_ipaddress.setBackground(Color.GREEN);  // สำหรับกรณีที่ต้องการปรับสีพื้นหลังเพื่อเช็คขนาดของ panel

        re_subnet = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        re_subnet.add(subnetlabel,gbc);
        gbc.gridx = 1;
        gbc.weightx = 3.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        re_subnet.add(re2_subnet,gbc);
        re_subnet.setBorder(new EmptyBorder(0, 20, 0, 20));

        re_network = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        re_network.add(networklabel,gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        re_network.add(re2_network,gbc);
        re_network.setBorder(new EmptyBorder(0, 20, 0, 20));

        re_broadcast = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        re_broadcast.add(broadcastlabel,gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        re_broadcast.add(re2_broadcast,gbc);
        re_broadcast.setBorder(new EmptyBorder(0, 20, 0, 20));

        re_wildcard= new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        re_wildcard.add(wildcardlabel,gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        re_wildcard.add(re2_wildcard,gbc);
        re_wildcard.setBorder(new EmptyBorder(0, 20, 0, 20));

        //test
        JPanel re_iprangee = new JPanel(new GridLayout(2, 1)); // GridLayout 2 แถว 2 คอลัมน์ ระยะห่าง 5
        JLabel iprangelabell = new JLabel("IP Range:");
        JLabel usablelabell = new JLabel("Usable IP:");
        re_iprangee.add(iprangelabell);
        re_iprangee.add(usablelabell);

        re_iprange= new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        re_iprange.add(re_iprangee,gbc);
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.weightx = 0.609;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        re_iprange.add(re2_iprange,gbc);
        re_iprange.setBorder(new EmptyBorder(0, 20, 12, 20));

        results.add(re_ipaddress);
        results.add(re_subnet);
        results.add(re_network);
        results.add(re_broadcast);
        results.add(re_wildcard);
        results.add(re_iprange);
//        results.setBorder(new EmptyBorder(0, 15, 20, 15));

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
            IPCalculator calc = new IPCalculator(tf_ip.getText(), tf_subnet.getText());
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