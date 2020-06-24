package main.mapcomponents;

import java.util.ArrayList;

public class Room {

    private Coordinate upperLeft;
    private Coordinate lowerRight;
    private Coordinate center;
    private ArrayList<Room> connectedRooms;

    public Room(Coordinate ul, Coordinate lr) {
        upperLeft = ul;
        lowerRight = lr;
        int xCoord = ul.getX() + (lr.getX() - ul.getX())/2;
        int yCoord = ul.getY() + (lr.getY() - ul.getY())/2;
        center = new Coordinate(xCoord, yCoord);
        connectedRooms = new ArrayList<Room>();
    }

    public Coordinate getUpperLeft() {
        return upperLeft;
    }

    public Coordinate getLowerRight() {
        return lowerRight;
    }

    public Coordinate getCenter() {
        return center;
    }

    public void connect(Room room) {
        connectedRooms.add(room);
    }

    public boolean isConnected(Room room) {
        for(Room i: connectedRooms) {
            if(i == room) {
                return true;
            }
        }
        return false;
    }
}
