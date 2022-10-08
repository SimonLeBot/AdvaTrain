import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static java.awt.Image.SCALE_SMOOTH;

public class Board extends JPanel {
    private static final int UNIT_SIZE_PIXELS = 50;

    private final Dimension dimensionPixels;
    private Target target;
    private Train train;
    private Point successPoint;
    private long lastSuccessTime;

    private static Image locomotiveImageUp;
    private static Image locomotiveImageDown;
    private static Image locomotiveImageLeft;
    private static Image locomotiveImageRight;
    private static Image wagonImageHorizontal;
    private static Image wagonImageVertical;

    static {
        try {
            locomotiveImageUp = ImageIO.read(new File("assets/images/locomotiveUp.png")).getScaledInstance(UNIT_SIZE_PIXELS, UNIT_SIZE_PIXELS, SCALE_SMOOTH);
            locomotiveImageDown = ImageIO.read(new File("assets/images/locomotiveDown.png")).getScaledInstance(UNIT_SIZE_PIXELS, UNIT_SIZE_PIXELS, SCALE_SMOOTH);
            locomotiveImageLeft = ImageIO.read(new File("assets/images/locomotiveLeft.png")).getScaledInstance(UNIT_SIZE_PIXELS, UNIT_SIZE_PIXELS, SCALE_SMOOTH);
            locomotiveImageRight = ImageIO.read(new File("assets/images/locomotiveRight.png")).getScaledInstance(UNIT_SIZE_PIXELS, UNIT_SIZE_PIXELS, SCALE_SMOOTH);
            wagonImageHorizontal = ImageIO.read(new File("assets/images/wagonHorizontal.png")).getScaledInstance(UNIT_SIZE_PIXELS, UNIT_SIZE_PIXELS, SCALE_SMOOTH);
            wagonImageVertical = ImageIO.read(new File("assets/images/wagonVertical.png")).getScaledInstance(UNIT_SIZE_PIXELS, UNIT_SIZE_PIXELS, SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("Could not load the images. " + e.getMessage());
        }
    }

    public Board(int width, int height) {
        this.dimensionPixels = new Dimension(width * UNIT_SIZE_PIXELS, height * UNIT_SIZE_PIXELS);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setSize(dimensionPixels);
        setMinimumSize(dimensionPixels);
        setMaximumSize(dimensionPixels);
        setPreferredSize(dimensionPixels);
    }

    public Dimension getDimensionPixels() {
        return dimensionPixels;
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
        successPoint = train.getLocomotivePosition().getPoint();
        lastSuccessTime = System.currentTimeMillis();
    }

    public void clearSuccess() {
        successPoint = null;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, dimensionPixels.width-1, dimensionPixels.height-1);

        if (successPoint != null) {
            Rectangle successRectangle = getRectangle(successPoint);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Yeah!", successRectangle.x + 10, successRectangle.y - 10);
            g2d.setColor(Color.ORANGE);
            g2d.fillRect(successRectangle.x, successRectangle.y, successRectangle.width, successRectangle.height);
            if (System.currentTimeMillis() - lastSuccessTime > 3000 || !train.isAt(successPoint)) {
                successPoint = null;
            }
        }

        final Emplacement locomotiveEmplacement = train.getLocomotivePosition();
        Rectangle locomotiveRectangle = getRectangle(locomotiveEmplacement.getPoint());
        switch (locomotiveEmplacement.direction) {
            case UP:
                g2d.drawImage(locomotiveImageUp, locomotiveRectangle.x, locomotiveRectangle.y, null);break;
            case DOWN:
                g2d.drawImage(locomotiveImageDown, locomotiveRectangle.x, locomotiveRectangle.y, null);break;
            case LEFT:
                g2d.drawImage(locomotiveImageLeft, locomotiveRectangle.x, locomotiveRectangle.y, null);break;
            case RIGHT:
            default:
                g2d.drawImage(locomotiveImageRight, locomotiveRectangle.x, locomotiveRectangle.y, null);break;
        }

        for (Emplacement wagonEmplacement : train.getWagonPositions()) {
            Rectangle trainRectangle = getRectangle(wagonEmplacement.getPoint());
            switch (wagonEmplacement.direction) {
                case LEFT:
                case RIGHT:
                    g2d.drawImage(wagonImageHorizontal, trainRectangle.x, trainRectangle.y, null);
                    break;
                default:
                    g2d.drawImage(wagonImageVertical, trainRectangle.x, trainRectangle.y, null);
            }
        }

        g2d.setColor(Color.RED);
        Rectangle targetRectangle = getRectangle(target.getTargetLocation());
        g2d.fillRect(targetRectangle.x, targetRectangle.y, targetRectangle.width, targetRectangle.height);
    }

    private Rectangle getRectangle(Point point) {
        return new Rectangle(point.x * UNIT_SIZE_PIXELS, point.y * UNIT_SIZE_PIXELS, UNIT_SIZE_PIXELS, UNIT_SIZE_PIXELS);
    }


}
