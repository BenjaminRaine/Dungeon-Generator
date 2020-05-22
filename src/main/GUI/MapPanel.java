package main.GUI;

import main.mapcomponents.Map;
import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    private Map map;

    MapPanel() {
        super();
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        int scalingFactor = 1200 / map.getHeightWidth();
        int[][] occupied = map.getOccupied();

        for(int y = 0; y < map.getHeightWidth(); y++) {
            for(int x = 0; x < map.getHeightWidth(); x++) {
                if(occupied[y][x] != -1) {
                    g.setColor(Color.WHITE);
                    g.fillRect(x*scalingFactor, y*scalingFactor, scalingFactor, scalingFactor);
                    g.setColor(Color.CYAN);
                    g.drawRect(x*scalingFactor, y*scalingFactor, scalingFactor, scalingFactor);
                    repaint();
                }
            }
        }
    }
}
