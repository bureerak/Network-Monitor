package main.ui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProfileEditorView extends JInternalFrame{
    JPanel topPanel, lowPanel;
    JLabel profileLabel, profileSelect;
    JTextField profileTxt;
    JButton addProfile, delete, select;
    JComboBox<String> comboBox;
    public ProfileEditorView() {
        super("Profile Editor | Network Monitor",false,true,false,false);
        profileLabel = new JLabel("Profile Name:");
        topPanel = new JPanel();
        lowPanel = new JPanel();
        comboBox = new JComboBox<String>();

        comboBox.setPreferredSize(new Dimension(300,30));

        topPanel.setBorder(new CompoundBorder(new EmptyBorder(7,7,1,7),BorderFactory.createTitledBorder("Select Profile")));
        lowPanel.setBorder(new CompoundBorder(new EmptyBorder(2,7,7,7),BorderFactory.createTitledBorder("Add Profile")));

        lowPanel.setLayout(new GridLayout(2,1));
        JPanel addProfileInfo = new JPanel();
        addProfile = new JButton("Add Profile");
        profileTxt = new JTextField(26);
        addProfile.setPreferredSize(new Dimension(165,27));
        addProfileInfo.add(profileLabel);
        addProfileInfo.add(profileTxt);
        addProfileInfo.setBorder(new EmptyBorder(15,0,0,0));

        JPanel submitPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        submitPane.setBorder(new EmptyBorder(0,0,0,10));
        submitPane.add(addProfile);

        lowPanel.add(addProfileInfo);
        lowPanel.add(submitPane);

        topPanel.setLayout(new GridLayout(2,1));
        JPanel subUpTop = new JPanel();
        JPanel subUpDown = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        profileSelect = new JLabel("Profile :");
        delete = new JButton("Delete");
        select = new JButton("Select");

        subUpTop.add(profileSelect);
        subUpTop.add(comboBox);
        subUpDown.add(delete); subUpDown.add(select);
        subUpDown.setBorder(new EmptyBorder(0,0,0,18));
        subUpTop.setBorder(new EmptyBorder(18,0,0,0));


        topPanel.add(subUpTop);
        topPanel.add(subUpDown);

        add(topPanel);
        add(lowPanel);
        setLayout(new GridLayout(2,1));
        setSize(new Dimension(460,350));
    }

}
