import javax.swing.*;
import java.awt.*;

public class MyBrick extends JPanel {
    private MyBrick brick;

    public MyBrick() {

    }

    public void paint() {

    }

    private class Brick {
        int x, y;
        int width , height;
        public Brick(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
