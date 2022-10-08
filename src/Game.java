import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel implements KeyListener {
    private static final int BOARD_WIDTH_UNIT = 20;
    private static final int BOARD_HEIGHT_UNIT = 15;
    private static final int TRAIN_LENGTH_UNIT = 5;

    private final Board board;
    private Train train;
    private Target target;
    private final JLabel infoLabel;

    public Game() {
        board = new Board(BOARD_WIDTH_UNIT, BOARD_HEIGHT_UNIT);

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setFocusable(true);
        final Dimension boardDimensionPixels = board.getDimensionPixels();
        setPreferredSize(new Dimension(boardDimensionPixels.width + 50, boardDimensionPixels.height + 200));

        final JButton restartButton = new JButton(new RestartAction());
        restartButton.setFocusable(false);

        infoLabel = new JLabel();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(restartButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(infoLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(board);

        add(Box.createHorizontalStrut(20));
        add(panel);

        init();
    }

    private void init() {
        target = Target.createRandomTarget(BOARD_WIDTH_UNIT, BOARD_HEIGHT_UNIT);
        train = new Train(TRAIN_LENGTH_UNIT, new Emplacement(0, 0, Direction.RIGHT));
        board.clearSuccess();
        board.drawTarget(target);
        board.drawTrain(train);
        updateInfoLabel(TRAIN_LENGTH_UNIT);
    }

    private void updateInfoLabel(int trainLength) {
        infoLabel.setText(String.format("The train has %d wagons.", trainLength-1));
    }
    private class RestartAction extends AbstractAction {

        private RestartAction() {
            super("Restart");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            init();
        }
    }

    private boolean isAllowedNewTrainPosition(Emplacement newTrainEmplacement) {
        return newTrainEmplacement.x >= 0 && newTrainEmplacement.x < BOARD_WIDTH_UNIT &&
                newTrainEmplacement.y >= 0 && newTrainEmplacement.y < BOARD_HEIGHT_UNIT &&
                !train.isAt(newTrainEmplacement);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (isAllowedNewTrainPosition(train.nextUpPosition())) {
                    train.moveUp();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (isAllowedNewTrainPosition(train.nextDownPosition())) {
                    train.moveDown();
                }
                break;
            case KeyEvent.VK_LEFT:
                if (isAllowedNewTrainPosition(train.nextLeftPosition())) {
                    train.moveLeft();
                }
                break;
            case KeyEvent.VK_RIGHT :
                if (isAllowedNewTrainPosition(train.nextRightPosition())) {
                    train.moveRight();
                }
                break;
        }
        board.drawTrain(train);

        checkIfTargetReached();
    }

    private void checkIfTargetReached() {
        if (train.isAt(target.getTargetLocation())) {
            updateInfoLabel(train.increaseLength());
            while (train.isAt(target.getTargetLocation())) {
                target = Target.createRandomTarget(BOARD_WIDTH_UNIT, BOARD_HEIGHT_UNIT);
            }
            board.drawTarget(target);
            board.drawSuccess();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
