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
                    if (y > 0 && occupied[y][x] >= -2 && occupied[y - 1][x] >= -2 && occupied[y][x] != occupied[y - 1][x]) {
                        g.fillRect(x * scalingFactor, y * scalingFactor - 1, scalingFactor, 3);
                    }
                    if (x < occupied.length - 1 && occupied[y][x] >= -2 && occupied[y][x + 1] >= -2 && occupied[y][x] != occupied[y][x + 1]) {
                        g.fillRect((x + 1) * scalingFactor - 1, y * scalingFactor, 3, scalingFactor);
                    }
                    if (y < occupied.length - 1 && occupied[y][x] >= -2 && occupied[y + 1][x] >= -2 && occupied[y][x] != occupied[y + 1][x]) {
                        g.fillRect(x * scalingFactor, (y + 1) * scalingFactor - 1, scalingFactor, 3);
                    }
                    if (x > 0 && occupied[y][x] >= -2 && occupied[y][x - 1] >= -2 && occupied[y][x] != occupied[y][x - 1]) {
                        g.fillRect(x * scalingFactor - 1, y * scalingFactor, 3, scalingFactor);
                    }
                }
            }
            for (int y = 0; y < map.getHeightWidth(); y++) {
                for (int x = 0; x < map.getHeightWidth(); x++) {
                    if (occupied[y][x] == -3) {
                        g.setColor(Color.WHITE);
                        g.fillRect(x * scalingFactor, y* scalingFactor - 3, scalingFactor, 7);
                        g.setColor(Color.BLACK);
                        g.drawRect(x * scalingFactor, y* scalingFactor - 3, scalingFactor, 7);
                        if (x > 0 && y > 0 && x < occupied.length - 1 && y < occupied.length - 1) {
                            if(occupied[y + 1][x] >= -2) {
                                if(occupied[y + 1][x] != occupied[y][x - 1] && occupied[y][x - 1] >= -2) {
                                    g.fillRect(x * scalingFactor - 1, y * scalingFactor, 3, scalingFactor);
                                }
                                if(occupied[y + 1][x] != occupied[y][x + 1] && occupied[y][x + 1] >= -2) {
                                    g.fillRect((x + 1) * scalingFactor - 1, y * scalingFactor, 3, scalingFactor);
                                }
                            } else {
                                if(occupied[y + 1][x] == -4) {
                                    g.fillRect(x * scalingFactor - 1, y * scalingFactor, 3, scalingFactor);
                                } else if (occupied[y + 1][x] == -6) {
                                    g.fillRect((x + 1) * scalingFactor - 1, y * scalingFactor, 3, scalingFactor);
                                }
                            }
                        }
                    } else if (occupied[y][x] == -4) {
                        g.setColor(Color.WHITE);
                        g.fillRect((x) * scalingFactor - 3, y* scalingFactor, 7, scalingFactor);
                        g.setColor(Color.BLACK);
                        g.drawRect((x) * scalingFactor - 3, y* scalingFactor, 7, scalingFactor);
                        if (x > 0 && y > 0 && x < occupied.length - 1 && y < occupied.length - 1) {
                            if(occupied[y][x + 1] >= -2) {
                                if(occupied[y][x + 1] != occupied[y - 1][x] && occupied[y - 1][x] >= -2) {
                                    g.fillRect(x * scalingFactor, y * scalingFactor - 1, scalingFactor, 3);
                                }
                                if(occupied[y][x + 1] != occupied[y + 1][x] && occupied[y + 1][x] >= -2) {
                                    g.fillRect(x * scalingFactor, (y + 1) * scalingFactor - 1, scalingFactor, 3);
                                }
                            } else {
                                if (occupied[y][x + 1] == -3) {
                                    g.fillRect(x * scalingFactor, y * scalingFactor - 1, scalingFactor, 3);
                                } else if (occupied[y][x + 1] == -5) {
                                    g.fillRect(x * scalingFactor, (y + 1) * scalingFactor - 1, scalingFactor, 3);
                                }
                            }
                        }
                    } else if (occupied[y][x] == -5) {
                        g.setColor(Color.WHITE);
                        g.fillRect(x * scalingFactor, (y + 1) * scalingFactor - 3, scalingFactor, 7);
                        g.setColor(Color.BLACK);
                        g.drawRect(x * scalingFactor, (y + 1) * scalingFactor - 3, scalingFactor, 7);
                        if (x > 0 && y > 0 && x < occupied.length - 1 && y < occupied.length - 1) {
                            if(occupied[y - 1][x] >= -2) {
                                if(occupied[y - 1][x] != occupied[y][x - 1] && occupied[y][x - 1] >= -2) {
                                    g.fillRect(x * scalingFactor - 1, y * scalingFactor, 3, scalingFactor);
                                }
                                if(occupied[y - 1][x] != occupied[y][x + 1] && occupied[y][x + 1] >= -2) {
                                    g.fillRect((x + 1) * scalingFactor - 1, y * scalingFactor, 3, scalingFactor);
                                }
                            } else {
                                if(occupied[y + 1][x] == -4) {
                                    g.fillRect((x + 1) * scalingFactor - 1, y * scalingFactor, 3, scalingFactor);
                                } else if (occupied[y + 1][x] == -6) {
                                    g.fillRect(x * scalingFactor - 1, y * scalingFactor, 3, scalingFactor);
                                }
                            }
                        }
                    } else if (occupied[y][x] == -6) {
                        g.setColor(Color.WHITE);
                        g.fillRect((x + 1) * scalingFactor - 3, y* scalingFactor, 7, scalingFactor);
                        g.setColor(Color.BLACK);
                        g.drawRect((x + 1) * scalingFactor - 3, y* scalingFactor, 7, scalingFactor);
                        if (x > 0 && y > 0 && x < occupied.length - 1 && y < occupied.length - 1) {
                            if(occupied[y][x - 1] >= -2) {
                                if(occupied[y][x - 1] != occupied[y - 1][x] && occupied[y - 1][x] >= -2) {
                                    g.fillRect(x * scalingFactor, y * scalingFactor - 1, scalingFactor, 3);
                                }
                                if(occupied[y][x - 1] != occupied[y + 1][x] && occupied[y + 1][x] >= -2) {
                                    g.fillRect(x * scalingFactor, (y + 1) * scalingFactor - 1, scalingFactor, 3);
                                }
                            } else {
                                if (occupied[y][x + 1] == -3) {
                                    g.fillRect(x * scalingFactor, y * scalingFactor - 1, scalingFactor, 3);
                                } else if (occupied[y][x + 1] == -5) {
                                    g.fillRect(x * scalingFactor, (y + 1) * scalingFactor - 1, scalingFactor, 3);
                                }
                            }
                        }
                    } else if (occupied[y][x] == -34) {
                        g.setColor(Color.WHITE);
                        g.fillRect(x * scalingFactor, y* scalingFactor - 3, scalingFactor, 7);
                        g.setColor(Color.BLACK);
                        g.drawRect(x * scalingFactor, y* scalingFactor - 3, scalingFactor, 7);
                        g.setColor(Color.WHITE);
                        g.fillRect((x) * scalingFactor - 3, y* scalingFactor, 7, scalingFactor);
                        g.setColor(Color.BLACK);
                        g.drawRect((x) * scalingFactor - 3, y* scalingFactor, 7, scalingFactor);
                    } else if (occupied[y][x] == -45) {
                        g.setColor(Color.WHITE);
                        g.fillRect((x) * scalingFactor - 3, y* scalingFactor, 7, scalingFactor);
                        g.setColor(Color.BLACK);
                        g.drawRect((x) * scalingFactor - 3, y* scalingFactor, 7, scalingFactor);
                        g.setColor(Color.WHITE);
                        g.fillRect(x * scalingFactor, (y + 1) * scalingFactor - 3, scalingFactor, 7);
                        g.setColor(Color.BLACK);
                        g.drawRect(x * scalingFactor, (y + 1) * scalingFactor - 3, scalingFactor, 7);
                    } else if (occupied[y][x] == -56) {
                        g.setColor(Color.WHITE);
                        g.fillRect(x * scalingFactor, (y + 1) * scalingFactor - 3, scalingFactor, 7);
                        g.setColor(Color.BLACK);
                        g.drawRect(x * scalingFactor, (y + 1) * scalingFactor - 3, scalingFactor, 7);
                        g.setColor(Color.WHITE);
                        g.fillRect((x + 1) * scalingFactor - 3, y* scalingFactor, 7, scalingFactor);
                        g.setColor(Color.BLACK);
                        g.drawRect((x + 1) * scalingFactor - 3, y* scalingFactor, 7, scalingFactor);
                    } else if (occupied[y][x] == -63) {
                        g.setColor(Color.WHITE);
                        g.fillRect((x + 1) * scalingFactor - 3, y* scalingFactor, 7, scalingFactor);
                        g.setColor(Color.BLACK);
                        g.drawRect((x + 1) * scalingFactor - 3, y* scalingFactor, 7, scalingFactor);
                        g.setColor(Color.WHITE);
                        g.fillRect(x * scalingFactor, y* scalingFactor - 3, scalingFactor, 7);
                        g.setColor(Color.BLACK);
                        g.drawRect(x * scalingFactor, y* scalingFactor - 3, scalingFactor, 7);
                    }
                }
            }
        }
    }
}
