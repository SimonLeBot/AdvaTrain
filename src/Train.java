import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Train {
    private int length;
    private final List<Emplacement> emplacements = new ArrayList<>();

    public  Train(int length, Emplacement initialEmplacement) {
        this.length = length;
        emplacements.add(initialEmplacement);
    }

    public Emplacement getLocomotivePosition() {
        if (emplacements.isEmpty()) {
            throw new IllegalArgumentException("The train has not been placed on the map yet");
        }
        return emplacements.get(emplacements.size() - 1);
    }

    public List<Emplacement> getWagonPositions() {
        if (emplacements.isEmpty()) {
            throw new IllegalArgumentException("The train has not been placed on the map yet");
        }
        return new ArrayList<>(emplacements.subList(0, emplacements.size() - 1));
    }

    public boolean isAt(Emplacement emplacement) {
        return emplacements.stream().anyMatch(p -> p.x == emplacement.x && p.y == emplacement.y);
    }

    public boolean isAt(Point point) {
        return isAt(new Emplacement(point.x, point.y, Direction.UNKNOWN));
    }

    public int increaseLength() {
        return ++length;
    }

    public void moveLeft() {
        emplacements.add(nextLeftPosition());
        trimPositionsIfRequired();
    }

    public Emplacement nextLeftPosition() {
        Emplacement lastHeadEmplacement = getLocomotivePosition();
        return new Emplacement(lastHeadEmplacement.x - 1, lastHeadEmplacement.y, Direction.LEFT);
    }

    public void moveRight() {
        emplacements.add( nextRightPosition());
        trimPositionsIfRequired();
    }

    public Emplacement nextRightPosition() {
        Emplacement lastHeadEmplacement = getLocomotivePosition();
        return new Emplacement(lastHeadEmplacement.x + 1, lastHeadEmplacement.y, Direction.RIGHT);
    }

    public void moveUp() {
        emplacements.add(nextUpPosition());
        trimPositionsIfRequired();
    }

    public Emplacement nextUpPosition() {
        Emplacement lastHeadEmplacement = getLocomotivePosition();
        return new Emplacement(lastHeadEmplacement.x, lastHeadEmplacement.y - 1, Direction.UP);
    }

    public void moveDown() {
        emplacements.add(nextDownPosition());
        trimPositionsIfRequired();
    }

    public Emplacement nextDownPosition() {
        Emplacement lastHeadEmplacement = getLocomotivePosition();
        return new Emplacement(lastHeadEmplacement.x, lastHeadEmplacement.y + 1, Direction.DOWN);
    }


   private void trimPositionsIfRequired() {
        if (emplacements.size() > length) {
            emplacements.remove(0);
        }
    }

}
