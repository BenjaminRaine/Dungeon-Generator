package main.mapcomponents;

public class Hallway {
    private Room roomA;
    private Room roomB;
    Coordinate start;
    Coordinate end;
    boolean north;
    boolean east;
    boolean south;
    boolean west;

    public Hallway(Room roomA, Room roomB) {
        this.roomA = roomA;
        this.roomB = roomB;
        findHallwayDirection();
    }

    private void findHallwayDirection() {
        Coordinate ulA = roomA.getUpperLeft();
        Coordinate ulB = roomB.getUpperLeft();
        Coordinate lrA = roomA.getLowerRight();
        Coordinate lrB = roomB.getLowerRight();
        north = lrA.getY() < ulB.getY();
        east = ulA.getX() > lrB.getX() ;
        south = ulA.getY() > lrB.getY();
        west = lrA.getX() < ulB.getX();
        if (north && east) {
            connectionNE();
        } else if (north && west) {
            connectionNW();
        } else if (south && east) {
            connectionSE();
        } else if (south && west) {
            connectionSW();
        } else if (north) {
            connectionN();
        } else if (west) {
            connectionW();
        } else if (east) {
            connectionE();
        } else {
            connectionS();
        }
    }

    private void connectionN() {
        int startX;
        int endX;
        if(roomA.getLowerRight().getX() < roomB.getLowerRight().getX()) {
            endX = roomA.getLowerRight().getX();
        } else {
            endX = roomB.getLowerRight().getX();
        }
        if(roomA.getUpperLeft().getX() < roomB.getUpperLeft().getX()) {
            startX = roomB.getUpperLeft().getX();
        } else {
            startX = roomA.getUpperLeft().getX();
        }
        int randXPoint = (int) (startX + (endX - startX) * Math.random());
        start = new Coordinate(randXPoint, roomA.getLowerRight().getY());
        end = new Coordinate(randXPoint, roomB.getUpperLeft().getY());
    }

    private void connectionNE() {
        start = new Coordinate(roomA.getUpperLeft().getX(), roomA.getLowerRight().getY());
        end = new Coordinate(roomB.getLowerRight().getX(), roomB.getUpperLeft().getY());
    }

    private void connectionE() {
        int startY;
        int endY;
        if(roomA.getLowerRight().getY() < roomB.getLowerRight().getY()) {
            endY = roomA.getLowerRight().getY();
        } else {
            endY = roomB.getLowerRight().getY();
        }
        if(roomA.getUpperLeft().getY() < roomB.getUpperLeft().getY()) {
            startY = roomB.getUpperLeft().getY();
        } else {
            startY = roomA.getUpperLeft().getY();
        }
        int randYPoint = (int) (startY + (endY - startY) * Math.random());
        start = new Coordinate(roomA.getUpperLeft().getX(), randYPoint);
        end = new Coordinate(roomB.getLowerRight().getX(), randYPoint);
    }

    private void connectionSE() {
        start = roomA.getUpperLeft();
        end = roomB.getLowerRight();
    }

    private void connectionS() {
        int startX;
        int endX;
        if(roomA.getLowerRight().getX() < roomB.getLowerRight().getX()) {
            endX = roomA.getLowerRight().getX();
        } else {
            endX = roomB.getLowerRight().getX();
        }
        if(roomA.getUpperLeft().getX() < roomB.getUpperLeft().getX()) {
            startX = roomB.getUpperLeft().getX();
        } else {
            startX = roomA.getUpperLeft().getX();
        }
        int randXPoint = (int) (startX + (endX - startX) * Math.random());
        start = new Coordinate(randXPoint, roomA.getUpperLeft().getY());
        end = new Coordinate(randXPoint, roomB.getLowerRight().getY());
    }

    private void connectionSW() {
        start = new Coordinate(roomA.getLowerRight().getX(), roomA.getUpperLeft().getY());
        end = new Coordinate(roomB.getUpperLeft().getX(), roomB.getLowerRight().getY());
    }

    private void connectionW() {
        int startY;
        int endY;
        if(roomA.getLowerRight().getY() < roomB.getLowerRight().getY()) {
            endY = roomA.getLowerRight().getY();
        } else {
            endY = roomB.getLowerRight().getY();
        }
        if(roomA.getUpperLeft().getY() < roomB.getUpperLeft().getY()) {
            startY = roomB.getUpperLeft().getY();
        } else {
            startY = roomA.getUpperLeft().getY();
        }
        int randYPoint = (int) (startY + (endY - startY) * Math.random());
        start = new Coordinate(roomA.getLowerRight().getX(), randYPoint);
        end = new Coordinate(roomB.getUpperLeft().getX(), randYPoint);
    }

    private void connectionNW() {
        start = roomA.getLowerRight();
        end = roomB.getUpperLeft();
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public boolean getNorth() {
        return north;
    }

    public boolean getEast() {
        return east;
    }

    public boolean getSouth() {
        return south;
    }

    public boolean getWest() {
        return west;
    }

}
