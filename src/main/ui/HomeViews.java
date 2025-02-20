package main.ui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class HomeViews extends JInternalFrame implements ActionListener {
    JSplitPane split;
    JPanel tPane ,bPane, upHalfPane, downHalfPane, subUpHalfPane;
    JButton profileEdit ,netTools ,netStat ,settings;
    JLabel title, titleIcon;
    ImageIcon titleImg;
    ProfileEditorView PEView;

    public HomeViews(){
        super("Main | Network Monitor",true,false,false,false);
        subUpHalfPane = new JPanel();

        titleImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/titleIcon.png")));
        Image scaleImg = titleImg.getImage().getScaledInstance(28,28,Image.SCALE_SMOOTH);
        ImageIcon resizeIcon = new ImageIcon(scaleImg);
        titleIcon = new JLabel(resizeIcon);

        PEView = new ProfileEditorView();

        profileEdit = new JButton("Profile Editor");
        netTools = new JButton("Network Tools");
        netStat = new JButton("Network Statistics");
        settings = new JButton("Settings");

        profileEdit.addActionListener(this);

        tPane = new JPanel(); tPane.setLayout(new GridLayout(2,1));
        upHalfPane = new JPanel(); upHalfPane.setLayout(new GridLayout(2,1));
        title = new JLabel("Network Monitor");
        title.setFont( new Font("Arial",Font.BOLD,22) );
        title.setBorder(new EmptyBorder(8,0,0,0));

        JPanel bxl = new JPanel(); bxl.setLayout(new BoxLayout(bxl,BoxLayout.LINE_AXIS));
        bxl.setBorder(new EmptyBorder(0,16,0,0));
        bxl.add(titleIcon);
        bxl.add(Box.createRigidArea(new Dimension(5,0)));
        bxl.add(title);

        upHalfPane.add(bxl);
        subUpHalfPane.add(profileEdit); subUpHalfPane.add(netTools); subUpHalfPane.add(netStat); subUpHalfPane.add(settings);
        upHalfPane.add(subUpHalfPane);

        downHalfPane = new JPanel(); downHalfPane.setLayout( new GridLayout(1,2) );
        JPanel myInfo = new JPanel();
        myInfo.setBorder(new CompoundBorder(new EmptyBorder(7,7,7,2),BorderFactory.createTitledBorder("My Information")));
        JPanel monitor = new JPanel();
        monitor.setBorder(new CompoundBorder(new EmptyBorder(7,2,7,7),BorderFactory.createTitledBorder("Monitor Information")));
        downHalfPane.add(myInfo);
        downHalfPane.add(monitor);

        tPane.add(upHalfPane);
        tPane.add(downHalfPane);

        bPane = new JPanel();

        bPane.setBorder(new CompoundBorder( new EmptyBorder(7,7,7,7), BorderFactory.createTitledBorder("Network Scanner")));

        split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,tPane,bPane);
        split.setDividerSize(0); split.setDividerLocation(270); split.setEnabled(false);
        add(split);

        Dimension dm = new Dimension(550,600);
        setSize(dm);
        setMinimumSize(dm);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(profileEdit) && !PEView.isVisible()){
            PEView.setVisible(true);
            getDesktopPane().add(PEView);
        }
    }
}
