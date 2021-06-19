import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyPanel extends JPanel implements KeyListener{
    private Ball ball;
    private Plank plank;
    private Brick bricks[] = new Brick[10];

    public MyPanel(int delay) {
        this.ball = new Ball(300, 400);
        for (int i = 0, j = 0; i < 600; i += 60, j++) {
            bricks[j] = new Brick(i, 20, 25, 10, true);
        }
        this.plank = new Plank(300, 420, 70, 20);
        new Timer(delay, event->{repaint();}).start();
        this.setBackground(Color.darkGray);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.YELLOW);

        boolean flag = false;
        for (int i = 0; i < bricks.length; i++) {
            if (ball.x >= bricks[i].x - 7 && ball.x <= bricks[i].x + 25 + 7
                && ball.y <= bricks[i].y + 10 + 7 && ball.y >= bricks[i].y - 7) {
                flag = true;
                bricks[i].exist = false;
                ball.vd = !ball.vd;
                ball.x = ball.hd ? ball.x - 5 : ball.x + 5;
                ball.y = ball.vd ? ball.y - 5 : ball.y + 5;
                break;
            }
        }
        if (!flag) {
            ball.x = ball.hd ? ball.x - 5 : ball.x + 5;
            if (ball.x <= 0 || ball.x >= 570) ball.hd = !ball.hd;
            ball.y = ball.vd ? ball.y - 5 : ball.y + 5;
            if (ball.y <= 0 || ball.y >= 430) ball.vd = !ball.vd;
        }
        g.fillOval(ball.x, ball.y, 15, 15);
        g.fillRect(plank.x, plank.y, plank.width, plank.height);

        g.setColor(Color.GREEN);
        for (int i = 0; i < bricks.length; i++) {
            if (bricks[i].exist) {
                g.fillRect(bricks[i].x, bricks[i].y, bricks[i].width, bricks[i].height);
            }
        }
    }

    //双缓冲解决小球闪烁问题
    Image offScreenImage = this.createImage(600, 500);
    public void update(Graphics g) {
        Graphics gOffScreen = offScreenImage.getGraphics();
        gOffScreen.fillRect(0, 0, 600, 500);
        paint(gOffScreen);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        int key = e.getKeyCode();
//        if (key == KeyEvent.VK_LEFT && plank.x > 0) plank.x -= 5;
//        else if (key == KeyEvent.VK_RIGHT && plank.x < 600) plank.x += 5;
    }

    @Override
    public void keyReleased(KeyEvent e) {

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

    private class Brick {
        int x, y;
        int width , height;
        boolean exist;
        public Brick(int x, int y, int width, int height, boolean exist) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.exist = true;
        }
    }

    private class Plank {
        int x, y;
        int width, height;
        public Plank(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
