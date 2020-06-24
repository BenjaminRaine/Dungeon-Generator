package main.GUI;

import main.mapcomponents.Map;
import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    private Map map;

    MapPanel() {
        super();
        setLayout(new FlowLayout());
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 1200);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        if(map != null) {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,1200,1200);
            int scalingFactor = 1200 / map.getHeightWidth();
            int[][] occupied = map.getOccupied();
            for (int y = 0; y < map.getHeightWidth(); y++) {
                for (int x = 0; x < map.getHeightWidth(); x++) {
                    if (occupied[y][x] != -1) {
                        g.setColor(Color.WHITE);
                        g.fillRect(x * scalingFactor, y * scalingFactor, scalingFactor, scalingFactor);
                        g.setColor(Color.CYAN);
                        g.drawRect(x * scalingFactor, y * scalingFactor, scalingFactor, scalingFactor);
                        revalidate();
                    }
                    g.setColor(Color.BLACK);
                    if (y > 0 && occupied[y][x] >= 0 && occupied[y - 1][x] > -2 && occupied[y][x]!= occupied[y - 1][x]) {
                        g.fillRect(x*scalingFactor, y*scalingFactor - 1, scalingFactor, 3);
                    }
                    if (x < occupied.length - 1 && occupied[y][x] >= 0 && occupied[y][x + 1] > -2 && occupied[y][x] != occupied[y][x + 1]) {
                        g.fillRect((x+1)*scalingFactor - 1, y*scalingFactor, 3, scalingFactor);
                    }
                    if (y < occupied.length - 1 && occupied[y][x] >= 0 && occupied[y + 1][x] > -2 && occupied[y][x] != occupied[y+1][x]) {
                        g.fillRect(x*scalingFactor, (y+1)*scalingFactor - 1, scalingFactor, 3);
                    }
                    if (x > 0 && occupied[y][x] >= 0 && occupied[y][x - 1] > -2 && occupied[y][x] != occupied[y][x - 1]) {
                        g.fillRect(x*scalingFactor - 1, y*scalingFactor, 3, scalingFactor);
                    }
                    //if () {

                    //}
                }
            }
        }
    }
}
