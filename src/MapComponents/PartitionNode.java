package MapComponents;

public class PartitionNode {

    int DIVISION_END_THRESHOLD = 50;
    private PartitionNode left;
    private PartitionNode right;
    private Coordinate upperLeft;
    private Coordinate lowerRight;

    public PartitionNode(int size) {
        upperLeft = new Coordinate(0,0);
        lowerRight = new Coordinate(size - 1, size - 1);
        split(size);
    }

    private PartitionNode(Coordinate ul, Coordinate lr) {
        upperLeft = ul;
        lowerRight = lr;
    }

    private void split (int size) {
        int height = lowerRight.getY() - upperLeft.getY();
        int width = lowerRight.getX() - upperLeft.getX();
        if (width * height > ((size * size) / DIVISION_END_THRESHOLD)) {
            Coordinate newul;
            Coordinate newlr;
            if(width > height) {
                int splitPoint = (int) (width / 4 + (width / 2) * Math.random());
                newul = new Coordinate(splitPoint + upperLeft.getX(), upperLeft.getY());
                newlr = new Coordinate(splitPoint + upperLeft.getX() - 1, lowerRight.getY());
            } else {
                int splitPoint = (int) (height / 4 + (height / 2) * Math.random());
                newul = new Coordinate(upperLeft.getX(),  upperLeft.getY() + splitPoint);
                newlr = new Coordinate(lowerRight.getX(), upperLeft.getY() + splitPoint - 1);
            }
            left = new PartitionNode(upperLeft, newlr);
            right = new PartitionNode(newul, lowerRight);
            left.split(size);
            right.split(size);
        }
    }

    public Coordinate getUpperLeft() {
        return upperLeft;
    }

    public Coordinate getLowerRight() {
        return lowerRight;
    }

    public PartitionNode getLeft() {
        return left;
    }

    public PartitionNode getRight() {
        return right;
    }
}
