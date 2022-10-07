import java.awt.*;
import java.util.Random;

public class Target {
     private final int x, y;
     private Target(int x, int y) {
          this.x = x;
          this.y = y;
     }

     public static Target createRandomTarget(int width, int height) {
          Random rand = new Random(System.currentTimeMillis());
          return new Target(rand.nextInt(width), rand.nextInt(height));
     }

     public Point getTargetLocation() {
          return new Point(x, y);
     }

}
