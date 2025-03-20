package main.ui;

import main.database.JDBCProfileEdit;
import main.database.UserPreference;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class ProfileEditorView extends JInternalFrame implements ActionListener {
    JPanel topPanel, lowPanel;
    JLabel profileLabel, profileSelect;
    JTextField profileTxt;
    JButton addProfile, delete, select, refresh;
    JComboBox<String> comboBox;

    public ProfileEditorView() {
        super("Profile Editor | Network Monitor",false,true,false,false);
        profileLabel = new JLabel("Profile Name:");
        topPanel = new JPanel();
        lowPanel = new JPanel();
        comboBox = new JComboBox<>();
        refresh = new JButton("Refresh");
        comboBox.setMaximumRowCount(4);

        comboBox.setPreferredSize(new Dimension(300,30));

        topPanel.setBorder(new CompoundBorder(new EmptyBorder(7,7,1,7),BorderFactory.createTitledBorder("Select Profile")));
        lowPanel.setBorder(new CompoundBorder(new EmptyBorder(2,7,7,7),BorderFactory.createTitledBorder("Add Profile")));

        lowPanel.setLayout(new GridLayout(2,1));
        JPanel addProfileInfo = new JPanel();
        addProfile = new JButton("Add Profile");
        profileTxt = new JTextField(26);
        addProfile.setPreferredSize(new Dimension(165,27));
        addProfile.addActionListener(this);
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
        delete = new JButton("Delete"); delete.addActionListener(this);
        select = new JButton("Start");
        select.addActionListener(this);

        subUpTop.add(profileSelect);
        subUpTop.add(comboBox);
        subUpDown.add(refresh); refresh.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(addProfile) && !profileTxt.getText().isBlank()) {
            if (profileTxt.getText().contains(" ")) {
                JOptionPane.showMessageDialog(this,"White spaces are prohibited.", "",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JDBCProfileEdit.addProfile(profileTxt.getText());
            profileTxt.setText("");
            viewRefresh();
            System.out.println("Insert Success");
        } else if (e.getSource().equals(refresh)) {
            viewRefresh();
        } else if (e.getSource().equals(delete) && comboBox.getSelectedItem() != null) {
            String[] name = comboBox.getSelectedItem().toString().split("\\s");
            if ( !JDBCProfileEdit.isUsable(name[0]) ){
                JOptionPane.showMessageDialog(this,"This profile is currently in use.");
                viewRefresh();
                return;
            }
            if ( name[0].equals( UserPreference.getProfile() )) {
                UserPreference.setProfile("None");
                UserPreference.setProfileID(0);
                HomeViews.setSelect();
            }
            JDBCProfileEdit.deleteProfile(name[0]);
            viewRefresh();
            System.out.println("* Delete Success");
        } else if (e.getSource().equals(select) && comboBox.getSelectedItem() != null) {
            String[] name = comboBox.getSelectedItem().toString().split("\\s");
            boolean canSelect = !UserPreference.getProfile().equals(name[0]) && JDBCProfileEdit.selectProfile(name[0]);
            if (canSelect) {
                UserPreference.setProfile(name[0]);
                viewRefresh();
                System.out.println(UserPreference.getProfile() + " Selected");
                HomeViews.setSelect();
                JOptionPane.showMessageDialog(this,UserPreference.getProfile() + " now currently in use.","",JOptionPane.INFORMATION_MESSAGE);
                UserPreference.setStatus(UserPreference.SAVING);
                HomeViews.updateGraph();
            } else {
                JOptionPane.showMessageDialog(this,"Something went wrong.","",JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private void viewRefresh() {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> resArr = JDBCProfileEdit.refresh(arrayList);
        Iterator<String> iterator = resArr.iterator();

        comboBox.removeAllItems();
        while (iterator.hasNext()) {
            comboBox.addItem(iterator.next());
        }
    }

}
