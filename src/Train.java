import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Train {
    private int length;
    private final List<Point> positions = new ArrayList<>();

    public Train(int length, Point initialPosition) {
        this.length = length;
        positions.add(initialPosition);
    }

    public List<Point> getPositions() {
        return new ArrayList<>(positions);
    }

    public boolean isAt(Point point) {
        return positions.contains(point);
    }

    public int increaseLength() {
        return ++length;
    }

    public void moveLeft() {
        positions.add(nextLeftPosition());
        trimPositionsIfRequired();
    }

    public Point nextLeftPosition() {
        Point lastHeadPosition = getHeadPosition();
        return new Point(lastHeadPosition.x - 1, lastHeadPosition.y);
    }

    public void moveRight() {
        positions.add( nextRightPosition());
        trimPositionsIfRequired();
    }

    public Point nextRightPosition() {
        Point lastHeadPosition = getHeadPosition();
        return new Point(lastHeadPosition.x + 1, lastHeadPosition.y);
    }

    public void moveUp() {
        positions.add(nextUpPosition());
        trimPositionsIfRequired();
    }

    public Point nextUpPosition() {
        Point lastHeadPosition = getHeadPosition();
        return new Point(lastHeadPosition.x, lastHeadPosition.y - 1);
    }

    public void moveDown() {
        positions.add(nextDownPosition());
        trimPositionsIfRequired();
    }

    public Point nextDownPosition() {
        Point lastHeadPosition = getHeadPosition();
        return new Point(lastHeadPosition.x, lastHeadPosition.y + 1);
    }


    public Point getHeadPosition() {
        if (positions.isEmpty()) {
            throw new IllegalArgumentException("The train has not been placed on the map yet");
        }
        return positions.get(positions.size() - 1);
    }

    private void trimPositionsIfRequired() {
        if (positions.size() > length) {
            positions.remove(0);
        }
    }
}
