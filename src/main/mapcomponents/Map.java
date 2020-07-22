package main.mapcomponents;

import main.Size;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {

    //Remember to declare constants as such
    private PartitionNode head;
    private ArrayList<Room> rooms;
    private Size mapSize;
    private int[][] occupied;
    private HashMap<Integer, Room> roomIdx;
    private ArrayList<Hallway> hallways;
    private ArrayList<Room> unconnected;
    private ArrayList<Room> connected;

    public Map(Size mapSize) {
        rooms = new ArrayList<>();
        hallways = new ArrayList<>();
        unconnected = new ArrayList<>();
        connected = new ArrayList<>();
        this.mapSize = mapSize;
        int side = getHeightWidth();
        occupied = new int[side][side];
        for (int[] i: occupied) {
            for(int x = 0; x < occupied.length; x++) {
                 i[x] = -1;
            }
        }
        head = new PartitionNode(side);
        createMainRooms(head);
        unconnected.addAll(rooms);
        createHallways();
    }

    public int getHeightWidth() {
        switch (mapSize) {
            case Tiny:
                return 10;
            case Small:
                return 20;
            case Medium:
                return 30;
            case Large:
                return 40;
            case Huge:
                return 50;
            default:
                return 60;
        }
    }

    public int[][] getOccupied() {
        return occupied;
    }


    void createMainRooms(PartitionNode curr) {
        if (curr.getLeft() != null) {
            createMainRooms(curr.getLeft());
            createMainRooms(curr.getRight());
        } else {
            Room newRoom;
            Coordinate lr = curr.getLowerRight();
            Coordinate ul = curr.getUpperLeft();
            int parWidth = lr.getX() - ul.getX();
            int parHeight = lr.getY() - ul.getY();
            int randPointX = (int) (parWidth * Math.random() + ul.getX());
            int randPointY = (int) (parHeight * Math.random() + ul.getY());
            Coordinate randPoint = new Coordinate(randPointX, randPointY);
            if (lr.getX() - randPointX < randPointX - ul.getX() && lr.getY() - randPointY < randPointY - ul.getY()) {
                newRoom = new Room(ul, randPoint);
            } else if (lr.getX() - randPointX < randPointX - ul.getX() && lr.getY() - randPointY >= randPointY - ul.getY()) {
                Coordinate roomul = new Coordinate(ul.getX(), randPointY);
                Coordinate roomlr = new Coordinate(randPointX, lr.getY());
                newRoom = new Room(roomul, roomlr);
            } else if (lr.getX() - randPointX >= randPointX - ul.getX() && lr.getY() - randPointY < randPointY - ul.getY()) {
                Coordinate roomul = new Coordinate(randPointX, ul.getY());
                Coordinate roomlr = new Coordinate(lr.getX(), randPointY);
                newRoom = new Room(roomul, roomlr);
            } else {
                newRoom = new Room(randPoint, lr);
            }
            rooms.add(newRoom);
            occupy(newRoom);
        }
    }

    void occupy(Room room) {
        for(int y = room.getUpperLeft().getY(); y <= room.getLowerRight().getY(); y++) {
            for(int x = room.getUpperLeft().getX(); x <= room.getLowerRight().getX(); x++) {
                occupied[y][x] = rooms.size() - 1;
            }
        }
    }

    void occupy(Hallway hallway) {
        if(hallway.getNorth() && hallway.getWest()) {
            occupyNW(hallway);
        } else if(hallway.getSouth() && hallway.getWest()){
            occupySW(hallway);
        } else if(hallway.getSouth() && hallway.getEast()){
            occupySE(hallway);
        } else if(hallway.getNorth() && hallway.getEast()){
            occupyNE(hallway);
        } else if(hallway.getNorth()){
            int constX = hallway.getStart().getX();
            for(int y = hallway.getStart().getY(); y <= hallway.getEnd().getY(); y++) {
                occupied[y][constX] = -2;
            }
            assignDoor(hallway.getStart().getX(), hallway.getStart().getY(), 3);
            assignDoor(hallway.getEnd().getX(), hallway.getEnd().getY(), 1);
        } else if(hallway.getWest()){
            int constY = hallway.getStart().getY();
            for(int x = hallway.getStart().getX(); x <= hallway.getEnd().getX(); x++) {
                occupied[constY][x] = -2;
            }
            assignDoor(hallway.getStart().getX(), hallway.getStart().getY(), 4);
            assignDoor(hallway.getEnd().getX(), hallway.getEnd().getY(), 2);
        } else if(hallway.getSouth()) {
            int constX = hallway.getStart().getX();
            for(int y = hallway.getStart().getY(); y >= hallway.getEnd().getY(); y--) {
                occupied[y][constX] = -2;
            }
            assignDoor(hallway.getStart().getX(), hallway.getStart().getY(), 1);
            assignDoor(hallway.getEnd().getX(), hallway.getEnd().getY(), 3);
        } else if(hallway.getEast()) {
            int constY = hallway.getStart().getY();
            for(int x = hallway.getStart().getX(); x >= hallway.getEnd().getX(); x--) {
                occupied[constY][x] = -2;
            }
            assignDoor(hallway.getStart().getX(), hallway.getStart().getY(), 2);
            assignDoor(hallway.getEnd().getX(), hallway.getEnd().getY(), 4);
        }
    }


    private void occupyNW(Hallway hallway) {
        int idxS = occupied[hallway.getStart().getY()][hallway.getStart().getX()];
        int idxE = occupied[hallway.getEnd().getY()][hallway.getEnd().getX()];
        int constY = hallway.getStart().getY();
        boolean intersectsUp = false;
        for (int x = hallway.getStart().getX(); x <= hallway.getEnd().getX(); x++) {
            if(occupied[constY][x] != -1 && occupied[constY][x] != idxS && occupied[constY][x] != idxE && occupied[constY][x] != -2) {
                intersectsUp = true;
                break;
            }
        }
        int constX = hallway.getEnd().getX();
        for (int y = hallway.getStart().getY(); y <= hallway.getEnd().getX(); y++) {
            if(occupied[y][constX] != -1 && occupied[y][constX] != idxS && occupied[y][constX] != idxE && occupied[y][constX] != -2) {
                intersectsUp = true;
                break;
            }
        }
        if (!intersectsUp) {
            for (int x = hallway.getStart().getX(); x <= hallway.getEnd().getX(); x++) {
                occupied[constY][x] = -2;
            }
            for (int y = hallway.getStart().getY(); y <= hallway.getEnd().getY(); y++) {
                occupied[y][constX] = -2;
            }
            assignDoor(hallway.getStart().getX(), hallway.getStart().getY(), 4);
            assignDoor(hallway.getEnd().getX(), hallway.getEnd().getY(), 1);
        } else {
            constX = hallway.getStart().getX();
            constY = hallway.getEnd().getY();
            for (int y = hallway.getStart().getY(); y <= hallway.getEnd().getY(); y++) {
                occupied[y][constX] = -2;
            }
            for (int x = hallway.getStart().getX(); x <= hallway.getEnd().getX(); x++) {
                occupied[constY][x] = -2;
            }
            assignDoor(hallway.getStart().getX(), hallway.getStart().getY(), 3);
            assignDoor(hallway.getEnd().getX(), hallway.getEnd().getY(), 2);
        }
    }



    private void occupyNE(Hallway hallway) {
        int idxS = occupied[hallway.getStart().getY()][hallway.getStart().getX()];
        int idxE = occupied[hallway.getEnd().getY()][hallway.getEnd().getX()];
        int constY = hallway.getStart().getY();
        boolean intersectsUp = false;
        for (int x = hallway.getStart().getX(); x >= hallway.getEnd().getX(); x--) {
            if(occupied[constY][x] != -1 && occupied[constY][x] != idxS && occupied[constY][x] != idxE && occupied[constY][x] != -2) {
                intersectsUp = true;
                break;
            }
        }
        int constX = hallway.getEnd().getX();
        for (int y = hallway.getStart().getY(); y <= hallway.getEnd().getX(); y++) {
            if(occupied[y][constX] != -1 && occupied[y][constX] != idxS && occupied[y][constX] != idxE && occupied[y][constX] != -2) {
                intersectsUp = true;
                break;
            }
        }
        if (!intersectsUp) {
            for (int x = hallway.getStart().getX(); x >= hallway.getEnd().getX(); x--) {
                occupied[constY][x] = -2;
            }
            for (int y = hallway.getStart().getY(); y <= hallway.getEnd().getY(); y++) {
                occupied[y][constX] = -2;
            }
            assignDoor(hallway.getStart().getX(), hallway.getStart().getY(), 2);
            assignDoor(hallway.getEnd().getX(), hallway.getEnd().getY(), 1);
        } else {
            constX = hallway.getStart().getX();
            constY = hallway.getEnd().getY();
            for (int y = hallway.getStart().getY(); y <= hallway.getEnd().getY(); y++) {
                occupied[y][constX] = -2;
            }
            for (int x = hallway.getStart().getX(); x >= hallway.getEnd().getX(); x--) {
                occupied[constY][x] = -2;
            }
            assignDoor(hallway.getStart().getX(), hallway.getStart().getY(), 3);
            assignDoor(hallway.getEnd().getX(), hallway.getEnd().getY(), 4);
        }
    }



    private void occupySW(Hallway hallway) {
        int idxS = occupied[hallway.getStart().getY()][hallway.getStart().getX()];
        int idxE = occupied[hallway.getEnd().getY()][hallway.getEnd().getX()];
        int constY = hallway.getStart().getY();
        boolean intersectsUp = false;
        for (int x = hallway.getStart().getX(); x <= hallway.getEnd().getX(); x++) {
            if(occupied[constY][x] != -1 && occupied[constY][x] != idxS && occupied[constY][x] != idxE && occupied[constY][x] != -2) {
                intersectsUp = true;
                break;
            }
        }
        int constX = hallway.getEnd().getX();
        for (int y = hallway.getStart().getY(); y >= hallway.getEnd().getX(); y--) {
            if(occupied[y][constX] != -1 && occupied[y][constX] != idxS && occupied[y][constX] != idxE && occupied[y][constX] != -2) {
                intersectsUp = true;
                break;
            }
        }
        if (!intersectsUp) {
            for (int x = hallway.getStart().getX(); x <= hallway.getEnd().getX(); x++) {
                occupied[constY][x] = -2;
            }
            for (int y = hallway.getStart().getY(); y >= hallway.getEnd().getY(); y--) {
                occupied[y][constX] = -2;
            }
            assignDoor(hallway.getStart().getX(), hallway.getStart().getY(), 4);
            assignDoor(hallway.getEnd().getX(), hallway.getEnd().getY(), 3);
        } else {
            constX = hallway.getStart().getX();
            constY = hallway.getEnd().getY();
            for (int y = hallway.getStart().getY(); y >= hallway.getEnd().getY(); y--) {
                occupied[y][constX] = -2;
            }
            for (int x = hallway.getStart().getX(); x <= hallway.getEnd().getX(); x++) {
                occupied[constY][x] = -2;
            }
            assignDoor(hallway.getStart().getX(), hallway.getStart().getY(), 1);
            assignDoor(hallway.getEnd().getX(), hallway.getEnd().getY(), 2);
        }
    }



    private void occupySE(Hallway hallway) {
        int idxS = occupied[hallway.getStart().getY()][hallway.getStart().getX()];
        int idxE = occupied[hallway.getEnd().getY()][hallway.getEnd().getX()];
        int constY = hallway.getStart().getY();
        boolean intersectsUp = false;
        for (int x = hallway.getStart().getX(); x >= hallway.getEnd().getX(); x--) {
            if(occupied[constY][x] != -1 && occupied[constY][x] != idxS && occupied[constY][x] != idxE && occupied[constY][x] != -2) {
                intersectsUp = true;
                break;
            }
        }
        int constX = hallway.getEnd().getX();
        for (int y = hallway.getStart().getY(); y >= hallway.getEnd().getX(); y--) {
            if(occupied[y][constX] != -1 && occupied[y][constX] != idxS && occupied[y][constX] != idxE && occupied[y][constX] != -2) {
                intersectsUp = true;
                break;
            }
        }
        if (!intersectsUp) {
            for (int x = hallway.getStart().getX(); x >= hallway.getEnd().getX(); x--) {
                occupied[constY][x] = -2;
            }
            for (int y = hallway.getStart().getY(); y >= hallway.getEnd().getY(); y--) {
                occupied[y][constX] = -2;
            }
            assignDoor(hallway.getStart().getX(), hallway.getStart().getY(), 2);
            assignDoor(hallway.getEnd().getX(), hallway.getEnd().getY(), 3);
        } else {
            constX = hallway.getStart().getX();
            constY = hallway.getEnd().getY();
            for (int y = hallway.getStart().getY(); y >= hallway.getEnd().getY(); y--) {
                occupied[y][constX] = -2;
            }
            for (int x = hallway.getStart().getX(); x >= hallway.getEnd().getX(); x--) {
                occupied[constY][x] = -2;
            }
            assignDoor(hallway.getStart().getX(), hallway.getStart().getY(), 1);
            assignDoor(hallway.getEnd().getX(), hallway.getEnd().getY(), 4);
        }
    }

    private void createHallways() {
        connected.add(unconnected.get(0));
        unconnected.remove(0);
        connectMinimum();
    }

    private void connectMinimum() {
        while (!unconnected.isEmpty()) {
            int minDist = (int) Math.sqrt((getHeightWidth() * getHeightWidth() * 2)) + 1;
            Room minUnconnected = unconnected.get(0);
            Room connectingTo = connected.get(0);
            for (Room u : unconnected) {
                for (Room c : connected) {
                    int dist = (int) Math.sqrt(Math.pow((u.getCenter().getX() - c.getCenter().getX()), 2) + Math.pow((u.getCenter().getY() - c.getCenter().getY()), 2));
                    if (dist <= minDist) {
                        minDist = dist;
                        minUnconnected = u;
                        connectingTo = c;
                    }
                }
            }
            Hallway newConnection = new Hallway(minUnconnected, connectingTo);
            occupy(newConnection);
            minUnconnected.connect(connectingTo);
            connectingTo.connect(minUnconnected);
            connected.add(minUnconnected);
            unconnected.remove(minUnconnected);
        }
    }

    public void assignDoor(int x, int y, int doorLocation) {
        switch (doorLocation) {
            case 1:
                if (occupied[y][x] == -2) {
                    occupied[y][x] = -3;
                } else if (occupied[y][x] == -4) {
                    occupied[y][x] = -34;
                } else if (occupied[y][x] == -6) {
                    occupied[y][x] = -63;
                }
                break;
            case 2:
                if (occupied[y][x] == -2) {
                    occupied[y][x] = -4;
                } else if (occupied[y][x] == -3) {
                    occupied[y][x] = -34;
                } else if (occupied[y][x] == -5) {
                    occupied[y][x] = -45;
                }
                break;
            case 3:
                if (occupied[y][x] == -2) {
                    occupied[y][x] = -5;
                } else if (occupied[y][x] == -4) {
                    occupied[y][x] = -45;
                } else if (occupied[y][x] == -6)  {
                    occupied[y][x] = -56;
                }
                break;
            default:
                if (occupied[y][x] == -2) {
                    occupied[y][x] = -6;
                } else if (occupied[y][x] == -5) {
                    occupied[y][x] = -56;
                } else if (occupied[y][x] == -3) {
                    occupied[y][x] = -63;
                }
        }

    }

    //A function that prints out a map with room index numbers for testing purposes
    public void printOccupied() {
        for(int y = 0; y < getHeightWidth(); y++) {
            String printString = "";
            for(int x = 0; x < getHeightWidth(); x++) {
                if (occupied[y][x] <= -2) {
                    printString += " OO";
                } else if (occupied[y][x] >= 100) {
                    printString += occupied[y][x];
                } else if (occupied[y][x] >= 10) {
                    printString += " " + occupied[y][x];
                } else if (occupied[y][x] >= 0){
                    printString += " " + occupied[y][x] + " ";
                } else {
                    printString += "   ";
                }
            }
            System.out.println(printString);
        }
    }
}
