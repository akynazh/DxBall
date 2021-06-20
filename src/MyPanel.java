import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class MyPanel extends JPanel {
    private Ball ball;
    private Plank plank;
    private Brick bricks[] = new Brick[25];
    private int speed;
    private Timer timer;
    public static boolean flag;
    public static int bricksNum = 0;

    public MyPanel(int speed) {
        this.speed = speed;
        flag = false;
        //添加三种图形
        this.ball = new Ball(300, 200);
        Random r = new Random();

        for (int i = 0, x = 0; i < bricks.length; x += 24, i++) {
            int y = r.nextInt(100) + 20;
            bricks[i] = new Brick(x, y, 20, 15, true);
            bricks[i].setType(0);
        }
        bricks[5].setType(1);
        bricks[8].setType(1);
        bricks[16].setType(1);
        this.plank = new Plank(300, 420, 70, 20);

        //设置初始背景
        this.setBackground(Color.darkGray);

        //每隔一段时间刷新
        new Timer(1, event -> {
            repaint();
        }).start();
        timer = new Timer(1, event -> {
            repaint();
        });
        timer.start();

        //添加键盘响应事件
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT && plank.x >= 15) plank.x -= 15;
                else if (key == KeyEvent.VK_RIGHT && plank.x <= 585) plank.x += 15;
            }
        });

        setVisible(true);
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        int boom_x = -1, boom_y = -1;
        super.paint(g);
        g.setColor(Color.YELLOW);

        //判断是否与砖块碰撞，若碰撞则改变方向且去除砖块
        for (int i = 0; i < bricks.length; i++) {
            if (bricks[i].exist
                    && ball.x >= bricks[i].x - 8 && ball.x <= bricks[i].x + 25 + 8
                    && ball.y >= bricks[i].y - 8 && ball.y <= bricks[i].y + 10 + 8) {
                bricks[i].exist = false;
                if (bricks[i].type == 1) {
                    boom_x = bricks[i].x;
                    boom_y = bricks[i].y;
                }
                bricksNum++;
                ball.vd = !ball.vd;
                break;
            }
        }

        //判断是否与板碰撞，若碰撞则改变方向
        if (ball.x >= plank.x && ball.x <= plank.x + 70
                && ball.y >= plank.y - 8 && ball.y <= plank.y + 8) {
            ball.vd = !ball.vd;
        }

        //判断板是否没接到小球
        if (ball.y >= 430) {
            flag = true;
        }

        //绘制结束界面
        if (flag) {
            var overMessage = "Game Over";
            var finalScore = "Score: " + GameInterface.score;
            var finalTime = "Time: " + GameInterface.time;
            var f1 = new Font("Serif", Font.BOLD, 36);
            var f2 = new Font("Serif", Font.PLAIN, 18);
            g.setFont(f1);
            g.drawString(overMessage, 200, 180);
            g.setFont(f2);
            g.drawString(finalScore, 250, 230);
            g.drawString(finalTime, 250, 260);
        }

        //若碰壁则改变方向，否则小球正常运动
        ball.x = ball.hd ? ball.x - speed : ball.x + speed;
        if (ball.x <= 0 || ball.x >= 570) ball.hd = !ball.hd;
        ball.y = ball.vd ? ball.y + speed : ball.y - speed;
        if (ball.y <= 0 || ball.y >= 430) ball.vd = !ball.vd;

        if (!flag) {
            //绘制图形
            g.fillOval(ball.x, ball.y, 16, 16);
            g.fillRect(plank.x, plank.y, plank.width, plank.height);
            g.setColor(Color.GREEN);
            for (int i = 0; i < bricks.length; i++) {
                if (bricks[i].exist) {
                    if (boom_x > 0 && boom_y > 0){
                        if (bricks[i].x >= boom_x - 25 && bricks[i].x <= boom_x + 50
                            && bricks[i].y >= boom_y - 15 && bricks[i].y <= boom_y + 30) {
                            bricks[i].exist = false;
                            continue;
                        }
                    }
                    if (bricks[i].type == 1) {
                        Color c = g.getColor();
                        g.setColor(Color.RED);
                        g.fillRect(bricks[i].x, bricks[i].y, bricks[i].width, bricks[i].height);
                        g.setColor(c);
                    } else if (bricks[i].type == 0) {
                        Color c = g.getColor();
                        g.setColor((Color.CYAN));
                        g.fillRect(bricks[i].x, bricks[i].y, bricks[i].width, bricks[i].height);
                        g.setColor(c);
                    }
                }
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

    //球类
    private class Ball {
        int x, y;
        boolean hd, vd;

        public Ball(int x, int y) {
            this.x = x;
            this.y = y;
            hd = vd = false;
        }
    }

    //砖块类
    private class Brick {
        int x, y;
        int width, height;
        boolean exist;
        int type;

        public void setType(int type) {
            this.type = type;
        }

        public Brick(int x, int y, int width, int height, boolean exist) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.exist = true;
        }
    }

    //板类
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
