import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class AdvaTrain {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Adva's game");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

        final Game game = new Game();
        jFrame.add(game);
        jFrame.addKeyListener(game);

        jFrame.pack();
        //jFrame.setSize(650, 600);
        jFrame.revalidate();
    }

}
