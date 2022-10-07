import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private static final int UNIT_SIZE_PIXELS = 20;

    private final Dimension dimensionPixels;
    private Target target;
    private Train train;
    private Point successPoint;
    private long lastSuccessTime;

    public Board(int width, int height) {
        this.dimensionPixels = new Dimension(width * UNIT_SIZE_PIXELS, height * UNIT_SIZE_PIXELS);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setSize(dimensionPixels);
        setMinimumSize(dimensionPixels);
        setMaximumSize(dimensionPixels);
        setPreferredSize(dimensionPixels);
    }

    public void drawTarget(Target target) {
        this.target = target;
        repaint();
    }

    public void drawTrain(Train train) {
        this.train = train;
        repaint();
    }

    public void drawSuccess() {
        successPoint = train.getHeadPosition();
        lastSuccessTime = System.currentTimeMillis();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, dimensionPixels.width-1, dimensionPixels.height-1);

        g2d.setColor(Color.BLUE);
        for (Point trainPoint : train.getPositions()) {
            Rectangle trainRectangle = getRectangle(trainPoint);
            g2d.fillRect(trainRectangle.x, trainRectangle.y, trainRectangle.width, trainRectangle.height);
        }

        g2d.setColor(Color.RED);
        Rectangle targetRectangle = getRectangle(target.getTargetLocation());
        g2d.fillRect(targetRectangle.x, targetRectangle.y, targetRectangle.width, targetRectangle.height);

        if (successPoint != null) {
            g2d.setColor(Color.ORANGE);
            Rectangle successRectangle = getRectangle(successPoint);
            g2d.fillRect(successRectangle.x, successRectangle.y, successRectangle.width, successRectangle.height);
            if (System.currentTimeMillis() - lastSuccessTime > 3000 || !train.isAt(successPoint)) {
                successPoint = null;
            }
        }

    }

    private Rectangle getRectangle(Point point) {
        return new Rectangle(point.x * UNIT_SIZE_PIXELS, point.y * UNIT_SIZE_PIXELS, UNIT_SIZE_PIXELS, UNIT_SIZE_PIXELS);
    }


}
