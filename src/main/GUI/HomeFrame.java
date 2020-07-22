package main.GUI;

import main.mapcomponents.Map;
import main.Size;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomeFrame extends JFrame {

    private Size size;
    private SizeBar sizeBar;
    private UtilityBar utilityBar;
    private MapPanel mapPanel;

    //
    public HomeFrame() {
        super("Dungeon Generation Window");
        setLayout(new BorderLayout());
        setSize(1232, 1360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mapPanel = new MapPanel();
        mapPanel.setSize(new Dimension(1200,1200));
        mapPanel.setBackground(Color.BLACK);
        sizeBar = new SizeBar(this);
        utilityBar = new UtilityBar(this);
        add(utilityBar, BorderLayout.NORTH);
        add(sizeBar, BorderLayout.SOUTH);
        add(mapPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    //
    protected void makeMap() {
        if(size != null) {
            Map map = new Map(size);
            mapPanel.setMap(map);
            mapPanel.setPreferredSize(new Dimension(1200, 1200));
            mapPanel.repaint();
            revalidate();
        }
    }

    protected void setSize(Size size) {
        this.size = size;
    }
}
