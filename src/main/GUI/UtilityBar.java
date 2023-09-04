package main.GUI;


import main.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UtilityBar extends JPanel implements ActionListener {

    private JButton generateBtn;
    //private JButton saveBtn;
    private HomeFrame panel;

    public UtilityBar(HomeFrame home) {
        panel = home;
        initializeButtons();
        buttonListenerHelper();
        setLayout(new FlowLayout());
        add(generateBtn);
        //add(saveBtn);
    }

    private void initializeButtons() {
        generateBtn = new JButton("Generate Map");
        //saveBtn = new JButton("Save Map");
    }

    private void buttonListenerHelper() {
        generateBtn.addActionListener(this);
        //saveBtn.addActionListener(this);
    }

    //EFFECTS: Holds the main functions of the program, calls back to home panel with different tasks
    //MODIFIES: HomePanel
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (clicked == generateBtn) {
            panel.makeMap();
        } //else if (clicked == saveBtn) {
            ///
        //}
    }
}