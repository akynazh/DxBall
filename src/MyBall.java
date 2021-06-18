import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class MyBall extends JPanel {
    private Ball ball;

    public MyBall(int delay) {
        this.ball = new Ball(300, 400);
        new Timer(delay, event->{repaint();}).start();
        this.setBackground(Color.darkGray);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.YELLOW);

        ball.x = ball.hd ? ball.x - 5 : ball.x + 5;
        if (ball.x <= 0 || ball.x >= 570) ball.hd = !ball.hd;
        ball.y = ball.vd ? ball.y - 5 : ball.y + 5;
        if (ball.y <= 0 || ball.y >= 430) ball.vd = !ball.vd;

        g.fillOval(ball.x, ball.y, 15, 15);
    }

    //双缓冲解决小球闪烁问题
    Image offScreenImage = null;
    public void update(Graphics g) {
        if(offScreenImage == null) {
            offScreenImage = this.createImage(600, 500);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        gOffScreen.fillRect(0, 0, 600, 500);
        paint(gOffScreen);
        //g.drawImage(offScreenImage, 0, 0, null);
    }

    private class Ball {
        public int x, y;
        boolean hd, vd;

        public Ball(int x, int y) {
            this.x = x;
            this.y = y;
            hd = vd = false;
        }
    }
}
