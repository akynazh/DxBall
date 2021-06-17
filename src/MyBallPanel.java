import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class MyBallPanel extends JPanel {
    Ball ball;

    public MyBallPanel(Ball ball) {
        this.ball = ball;
        this.ball.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        repaint();
        g.drawOval(ball.x, ball.y, 15, 15);
    }
}

class Ball extends Thread {
    int x = 300, y = 500;
    @Override
    public void run() {
        while(true) {
            if (x >= 600 || y >= 500) {
                x = 0;
                y = 0;
            } else {
               x += 5;
               y += 5;
            }
            try {
                sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
