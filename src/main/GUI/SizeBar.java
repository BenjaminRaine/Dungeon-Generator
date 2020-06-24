package main.GUI;

import main.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SizeBar extends JPanel implements ActionListener {

    private JButton tinyBtn;
    private JButton smallBtn;
    private JButton mediumBtn;
    private JButton largeBtn;
    private JButton hugeBtn;
    private JButton colossalBtn;
    private HomeFrame panel;

    public SizeBar(HomeFrame home) {
        panel = home;
        initializeButtons();
        buttonListenerHelper();
        setLayout(new FlowLayout());
        add(tinyBtn);
        add(smallBtn);
        add(mediumBtn);
        add(largeBtn);
        add(hugeBtn);
        add(colossalBtn);
    }

    private void initializeButtons() {
        tinyBtn = new JButton("Tiny");
        smallBtn = new JButton("Small");
        mediumBtn = new JButton("Medium");
        largeBtn = new JButton("Large");
        hugeBtn = new JButton("Huge");
        colossalBtn = new JButton("Colossal");
    }

    private void buttonListenerHelper() {
        tinyBtn.addActionListener(this);
        smallBtn.addActionListener(this);
        mediumBtn.addActionListener(this);
        largeBtn.addActionListener(this);
        hugeBtn.addActionListener(this);
        colossalBtn.addActionListener(this);
    }

    //EFFECTS: Holds the main functions of the program, calls back to home panel with different tasks
    //MODIFIES: HomePanel
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (clicked == tinyBtn) {
            panel.setSize(Size.Tiny);
        } else if (clicked == smallBtn) {
            panel.setSize(Size.Small);
        } else if (clicked == mediumBtn) {
            panel.setSize(Size.Medium);
        } else if (clicked == largeBtn) {
            panel.setSize(Size.Large);
        } else if (clicked == hugeBtn) {
            panel.setSize(Size.Huge);
        } else if (clicked == colossalBtn) {
            panel.setSize(Size.Colossal);
        }
    }
}
