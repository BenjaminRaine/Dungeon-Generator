import MapComponents.*;
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

    Map(Size mapSize) {
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
        indexRooms();
        createHallways();
    }

    int getHeightWidth() {
        switch (mapSize) {
            case Tiny:
                return 30;
            case Small:
                return 50;
            case Medium:
                return 80;
            case Large:
                return 120;
            case Huge:
                return 150;
            case Colossal:
                return 300;
            default:
                System.out.println("An invalid size error has occurred");
                return 80;
        }
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
            int idxS = occupied[hallway.getStart().getY()][hallway.getStart().getX()];
            int idxE = occupied[hallway.getEnd().getY()][hallway.getEnd().getX()];
            int constY = hallway.getStart().getY();
            boolean intersectsUp = false;
            for (int x = hallway.getStart().getX(); x <= hallway.getEnd().getX(); x++) {
                if(occupied[constY][x] != -1 && occupied[constY][x] != idxS && occupied[constY][x] != idxE && occupied[constY][x] != 999999) {
                    intersectsUp = true;
                    break;
                }
            }
            int constX = hallway.getEnd().getX();
            for (int y = hallway.getStart().getY(); y <= hallway.getEnd().getX(); y++) {
                if(occupied[y][constX] != -1 && occupied[y][constX] != idxS && occupied[y][constX] != idxE && occupied[y][constX] != 999999) {
                    intersectsUp = true;
                    break;
                }
            }
            if (!intersectsUp) {
                for (int x = hallway.getStart().getX(); x <= hallway.getEnd().getX(); x++) {
                    occupied[constY][x] = 999999;
                }
                for (int y = hallway.getStart().getY(); y <= hallway.getEnd().getY(); y++) {
                    occupied[y][constX] = 999999;
                }
            } else {
                constX = hallway.getStart().getX();
                constY = hallway.getEnd().getY();
                for (int y = hallway.getStart().getY(); y <= hallway.getEnd().getY(); y++) {
                    occupied[y][constX] = 999999;
                }
                for (int x = hallway.getStart().getX(); x <= hallway.getEnd().getX(); x++) {
                    occupied[constY][x] = 999999;
                }
            }
            System.out.println("NW " + idxS + " - " + idxE);

        } else if(hallway.getSouth() && hallway.getWest()){
            int idxS = occupied[hallway.getStart().getY()][hallway.getStart().getX()];
            int idxE = occupied[hallway.getEnd().getY()][hallway.getEnd().getX()];
            int constY = hallway.getStart().getY();
            boolean intersectsUp = false;
            for (int x = hallway.getStart().getX(); x <= hallway.getEnd().getX(); x++) {
                if(occupied[constY][x] != -1 && occupied[constY][x] != idxS && occupied[constY][x] != idxE && occupied[constY][x] != 999999) {
                    intersectsUp = true;
                    break;
                }
            }
            int constX = hallway.getEnd().getX();
            for (int y = hallway.getStart().getY(); y >= hallway.getEnd().getX(); y--) {
                if(occupied[y][constX] != -1 && occupied[y][constX] != idxS && occupied[y][constX] != idxE && occupied[y][constX] != 999999) {
                    intersectsUp = true;
                    break;
                }
            }
            if (!intersectsUp) {
                for (int x = hallway.getStart().getX(); x <= hallway.getEnd().getX(); x++) {
                    occupied[constY][x] = 999999;
                }
                for (int y = hallway.getStart().getY(); y >= hallway.getEnd().getY(); y--) {
                    occupied[y][constX] = 999999;
                }
            } else {
                constX = hallway.getStart().getX();
                constY = hallway.getEnd().getY();
                for (int y = hallway.getStart().getY(); y >= hallway.getEnd().getY(); y--) {
                    occupied[y][constX] = 999999;
                }
                for (int x = hallway.getStart().getX(); x <= hallway.getEnd().getX(); x++) {
                    occupied[constY][x] = 999999;
                }
            }
            System.out.println("SW " + idxS + " - " + idxE);
        } else if(hallway.getSouth() && hallway.getEast()){
            int idxS = occupied[hallway.getStart().getY()][hallway.getStart().getX()];
            int idxE = occupied[hallway.getEnd().getY()][hallway.getEnd().getX()];
            int constY = hallway.getStart().getY();
            boolean intersectsUp = false;
            for (int x = hallway.getStart().getX(); x >= hallway.getEnd().getX(); x--) {
                if(occupied[constY][x] != -1 && occupied[constY][x] != idxS && occupied[constY][x] != idxE && occupied[constY][x] != 999999) {
                    intersectsUp = true;
                    break;
                }
            }
            int constX = hallway.getEnd().getX();
            for (int y = hallway.getStart().getY(); y >= hallway.getEnd().getX(); y--) {
                if(occupied[y][constX] != -1 && occupied[y][constX] != idxS && occupied[y][constX] != idxE && occupied[y][constX] != 999999) {
                    intersectsUp = true;
                    break;
                }
            }
            if (!intersectsUp) {
                for (int x = hallway.getStart().getX(); x >= hallway.getEnd().getX(); x--) {
                    occupied[constY][x] = 999999;
                }
                for (int y = hallway.getStart().getY(); y >= hallway.getEnd().getY(); y--) {
                    occupied[y][constX] = 999999;
                }
            } else {
                constX = hallway.getStart().getX();
                constY = hallway.getEnd().getY();
                for (int y = hallway.getStart().getY(); y >= hallway.getEnd().getY(); y--) {
                    occupied[y][constX] = 999999;
                }
                for (int x = hallway.getStart().getX(); x >= hallway.getEnd().getX(); x--) {
                    occupied[constY][x] = 999999;
                }
            }
            System.out.println("SE " + idxS + " - " + idxE);
        } else if(hallway.getNorth() && hallway.getEast()){
            int idxS = occupied[hallway.getStart().getY()][hallway.getStart().getX()];
            int idxE = occupied[hallway.getEnd().getY()][hallway.getEnd().getX()];
            int constY = hallway.getStart().getY();
            boolean intersectsUp = false;
            for (int x = hallway.getStart().getX(); x >= hallway.getEnd().getX(); x--) {
                if(occupied[constY][x] != -1 && occupied[constY][x] != idxS && occupied[constY][x] != idxE && occupied[constY][x] != 999999) {
                    intersectsUp = true;
                    break;
                }
            }
            int constX = hallway.getEnd().getX();
            for (int y = hallway.getStart().getY(); y <= hallway.getEnd().getX(); y++) {
                if(occupied[y][constX] != -1 && occupied[y][constX] != idxS && occupied[y][constX] != idxE && occupied[y][constX] != 999999) {
                    intersectsUp = true;
                    break;
                }
            }
            if (!intersectsUp) {
                for (int x = hallway.getStart().getX(); x >= hallway.getEnd().getX(); x--) {
                    occupied[constY][x] = 999999;
                }
                for (int y = hallway.getStart().getY(); y <= hallway.getEnd().getY(); y++) {
                    occupied[y][constX] = 999999;
                }
            } else {
                constX = hallway.getStart().getX();
                constY = hallway.getEnd().getY();
                for (int y = hallway.getStart().getY(); y <= hallway.getEnd().getY(); y++) {
                    occupied[y][constX] = 999999;
                }
                for (int x = hallway.getStart().getX(); x >= hallway.getEnd().getX(); x--) {
                    occupied[constY][x] = 999999;
                }
            }
            System.out.println("NE " + idxS + " - " + idxE);
        } else if(hallway.getNorth()){
            int constX = hallway.getStart().getX();
            for(int y = hallway.getStart().getY(); y <= hallway.getEnd().getY(); y++) {
                occupied[y][constX] = 999999;
            }
        } else if(hallway.getWest()){
            int constY = hallway.getStart().getY();
            for(int x = hallway.getStart().getX(); x <= hallway.getEnd().getX(); x++) {
                occupied[constY][x] = 999999;
            }
        } else if(hallway.getSouth()) {
            int constX = hallway.getStart().getX();
            for(int y = hallway.getStart().getY(); y >= hallway.getEnd().getY(); y--) {
                occupied[y][constX] = 999999;
            }
        } else if(hallway.getEast()) {
            int constY = hallway.getStart().getY();
            for(int x = hallway.getStart().getX(); x >= hallway.getEnd().getX(); x--) {
                occupied[constY][x] = 999999;
            }
        }
    }


    //This creates a Hashmap of each room by index similar to rooms
    //This main advantage of this over rooms is it eliminates any rooms that have been totally eclipsed
    void indexRooms() {
        roomIdx = new HashMap<Integer, Room>();
        for(int y = 0;  y < getHeightWidth(); y++) {
            for(int x = 0; x < getHeightWidth(); x++) {
                if (occupied[y][x] >= 0) {
                    roomIdx.put(occupied[y][x], rooms.get(occupied[y][x]));
                }
            }
        }
        unconnected.addAll(roomIdx.values());
    }

    void createHallways() {
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

    //A function that prints out a map with room index numbers for testing purposes
    void printOccupied() {
        for(int y = 0; y < getHeightWidth(); y++) {
            String printString = "";
            for(int x = 0; x < getHeightWidth(); x++) {
                if (occupied[y][x] == 999999) {
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
