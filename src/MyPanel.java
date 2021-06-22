import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;

/**
 * @author jzh
 * @date 2021/6/21
 * class 游戏面板类
 */

public class MyPanel extends JPanel {
    public final int BRICKSNUM = 25;
    public final int GAME_X = GameInterface.GAME_X, GAME_Y = GameInterface.GAME_Y,
            GAME_WIDTH = GameInterface.GAME_WIDTH, GAME_HEIGHT = GameInterface.GAME_HEIGHT;
    public final int BRICK_WIDTH = 25, BRICK_HEIGHT = 10;
    public final int PLANK_WIDTH = 70, PLANK_HEIGHT = 20,
            PLANK_X = GAME_WIDTH / 2 - PLANK_WIDTH / 2, PLANK_Y = GAME_HEIGHT - PLANK_HEIGHT - GAME_Y;
    public final int BALL_R = 16;
    public final Ball ball;
    public final Plank plank;
    public final Brick[] bricks = new Brick[BRICKSNUM];
    public final LinkedList<Prop> props = new LinkedList<>();

    public int speed, passNum;
    public boolean overFlag, startFlag, success, continuePlay, addFlag;
    public int bricksNum = 0, additionalScore = 0, originSpeed;
    public Timer timer;

    public MyPanel(int speed, int passNum) {
        this.speed = speed;
        this.passNum = passNum;
        originSpeed = speed;
        overFlag = startFlag = success = continuePlay = addFlag = false;
        setBounds(GAME_X, GAME_Y, GAME_WIDTH, GAME_HEIGHT);

        // 添加三种图形
        Random r = new Random();
        for (int i = 0, x = 0; i < bricks.length; i++) {
            int y = r.nextInt(200) + 50;
            if (speed <= 4) y = r.nextInt(200) + 50;
            else if (speed == 5) y = r.nextInt(200) + 75;
            else if (speed == 6) y = r.nextInt(200) + 100;
            bricks[i] = new Brick(x, y, BRICK_WIDTH, BRICK_HEIGHT);
            bricks[i].type = 0;
            if (x < GAME_WIDTH - BRICK_WIDTH - 10) x += BRICK_WIDTH + 10;
            else x = 0;
        }
        for (int i = 0; i < 10; i++) {
            bricks[r.nextInt(bricks.length)].type = r.nextInt(10);
        }
        if (originSpeed == 5 || originSpeed == 6) {
            this.plank = new Plank(PLANK_X, PLANK_Y, PLANK_WIDTH / 2, PLANK_HEIGHT);
        } else {
            this.plank = new Plank(PLANK_X, PLANK_Y, PLANK_WIDTH, PLANK_HEIGHT);
        }
        this.ball = new Ball(plank.x + plank.width / 2 - 6, plank.y - BALL_R, BALL_R, Color.RED);

        // 设置初始背景
        this.setBackground(Color.darkGray);

        // 每隔一段时间刷新
        timer = new Timer(1, event -> repaint());
        timer.start();

        // 添加键盘响应事件
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT && plank.x >= 15) plank.x -= 15;
                else if (key == KeyEvent.VK_RIGHT && plank.x <= GAME_WIDTH - plank.width - 15) plank.x += 15;
            }
        });

        setVisible(true);
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 判断玩家是否移动砖块，移动则开始游戏
        if (!startFlag) {
            if (plank.x != PLANK_X) {
                startFlag = true;
                ball.y -= speed;
            }
        }

        // 炸弹球获取的爆炸坐标
        int boom_x = 0, boom_y = 0;

        //玩家移动砖块即开始游戏
        if (startFlag) {
            // 判断是否与砖块碰撞，若碰撞则改变方向且去除砖块
            for (Brick brick : bricks) {
                if (brick.exist
                        && ball.x >= brick.x && ball.x <= brick.x + brick.width
                        && ball.y >= brick.y - ball.r && ball.y <= brick.y + brick.height + ball.r) {
                    brick.exist = false;
                    bricksNum++;
                    if (ball.type != 4) ball.vd = !ball.vd;
                    if (ball.type == 3) {
                        boom_x = brick.x;
                        boom_y = brick.y;
                    }
                    if (brick.type != 0) {
                        brick.propFall = true;
                    }
                    break;
                }
            }

            // 判断是否与板碰撞，若碰撞则改变方向
            if (ball.x >= plank.x && ball.x <= plank.x + plank.width
                    && ball.y >= plank.y - ball.r && ball.y <= plank.y) {
                ball.vd = !ball.vd;
            }

            // 判断板是否没接到小球
            if (ball.y >= GAME_HEIGHT - GAME_Y - ball.r) {
                overFlag = true;
            }

            // 判断是否打完所有砖块
            if (bricksNum == bricks.length) {
                overFlag = true;
            }

            // 若碰壁则改变方向，否则小球正常运动
            ball.x = ball.hd ? ball.x - speed : ball.x + speed;
            if (ball.x <= ball.r || ball.x >= GAME_WIDTH - ball.r * 2) ball.hd = !ball.hd;
            ball.y = ball.vd ? ball.y + speed : ball.y - speed;
            if (ball.y <= ball.r || ball.y >= GAME_HEIGHT - ball.r) ball.vd = !ball.vd;

            // 绘制结束界面
            if (overFlag) {
                var overMessage1 = "成功";
                var overMessage2 = "失败";
                var finalScore = "得分: " + GameInterface.score;
                var finalTime = "用时: " + GameInterface.time + "s";
                var title = "等级: ";
                var pass = "过关数: ";
                if (originSpeed == 3) {
                    if (GameInterface.time <= 20 && GameInterface.score >= 3500) title += "新手";
                    else title += "菜鸟";
                } else if (originSpeed == 4) {
                    if (GameInterface.time <= 20 && GameInterface.score >= 3500) title += "专业";
                    else title += "新手";
                } else if (originSpeed == 5) {
                    if (GameInterface.score <= 2000) title += "新手";
                    else if (GameInterface.time > 30) title += "大师";
                    else title += "专业";
                } else {
                    if (GameInterface.score <= 1000) title += "新手";
                    else if (GameInterface.score <= 3000) title += "专业";
                    else if (GameInterface.time > 15) title += "大师";
                    else title += "专业";
                }
                var f1 = new Font("宋体", Font.BOLD, 90);
                var f2 = new Font("宋体", Font.PLAIN, 40);
                g.setFont(f1);
                g.setColor(Color.RED);
                if (GameInterface.score >= 2000) {
                    success = true;
                    g.drawString(overMessage1, GAME_WIDTH / 3, GAME_HEIGHT / 4);
                } else {
                    success = false;
                    g.drawString(overMessage2, GAME_WIDTH / 3, GAME_HEIGHT / 4);
                }
                g.setFont(f2);
                g.drawString(finalScore, GAME_WIDTH / 3, GAME_HEIGHT / 4 + 90);
                g.drawString(finalTime, GAME_WIDTH / 3, GAME_HEIGHT / 4 + 140);
                g.drawString(title, GAME_WIDTH / 3, GAME_HEIGHT / 4 + 190);
                if (success) pass += passNum + 1;
                else pass += passNum;
                g.drawString(pass, GAME_WIDTH / 3, GAME_HEIGHT / 4 + 240);

                JButton continueButton = new JButton("继续闯关");
                continueButton.setBackground(Color.GREEN);
                continueButton.setBounds(GAME_WIDTH / 3, GAME_HEIGHT / 4 + 270, 150, 50);
                continueButton.addActionListener(event -> continuePlay = true);

                JButton overButton = new JButton();
                overButton.setBackground(Color.orange);
                overButton.setBounds(GAME_WIDTH / 3, GAME_HEIGHT / 4 + 270, 150, 50);
                overButton.addActionListener(event -> System.exit(0));

                if (!addFlag) {
                    if (originSpeed < 6 && success) add(continueButton);
                    else if (originSpeed == 6 && success) {
                        overButton.setText("游戏通关");
                        add(overButton);
                    } else {
                        overButton.setText("闯关失败");
                        add(overButton);
                    }
                    addFlag = true;
                }
            }
        }

        // 如果游戏未结束则绘制图形
        if (!overFlag) {
            g.setColor(ball.color);
            g.fillOval(ball.x, ball.y, ball.r, ball.r);
            g.setColor(new Color(205, 79, 57));
            g.fillRect(plank.x, plank.y, plank.width, plank.height);
            g.setColor(new Color(131, 111, 255));
            for (Brick brick : bricks) {
                if (brick.exist) {
                    if (ball.type != 3) {
                        g.fillRect(brick.x, brick.y, brick.width, brick.height);
                    } else {
                        if (brick.x >= boom_x - brick.width && brick.x <= boom_x + 2 * brick.width &&
                                brick.y >= boom_y - brick.height && brick.y <= boom_y + 2 * brick.height) {
                            brick.exist = false;
                        } else {
                            g.fillRect(brick.x, brick.y, brick.width, brick.height);
                        }
                    }
                }
                if (brick.propFall) {
                    int propType = brick.type;
                    Prop prop = new Prop(brick.x, brick.y, brick.width, brick.height, propType);
                    if (prop.type == 1 || prop.type == 2) g.setColor(Color.GREEN);
                    else if (prop.type == 3) g.setColor(Color.RED);
                    else if (prop.type == 4) g.setColor(Color.cyan);
                    else if (prop.type == 5) g.setColor(Color.ORANGE);
                    else if (prop.type == 6) g.setColor(Color.blue);
                    else if (prop.type == 7 || prop.type == 8 || prop.type == 9) g.setColor(Color.BLACK);
                    g.fillOval(prop.x, prop.y, prop.width, prop.height);
                    props.add(prop);
                    brick.propFall = false;
                }
            }

            // 绘制掉落道具
            for (Prop p : props) {
                p.y += originSpeed - 1;
                if (p.type == 1 || p.type == 2) g.setColor(Color.GREEN);
                else if (p.type == 3) g.setColor(Color.RED);
                else if (p.type == 4) g.setColor(Color.gray);
                else if (p.type == 5) g.setColor(Color.ORANGE);
                else if (p.type == 6) g.setColor(Color.blue);
                else if (p.type == 7 || p.type == 8 || p.type == 9) g.setColor(Color.BLACK);
                g.fillOval(p.x, p.y, p.width, p.height);
                // 如果板接收到道具
                if (p.y >= plank.y && p.y <= plank.y + 20 && p.x >= plank.x && p.x <= plank.x + 70) {
                    int propType = p.type;
                    additionalScore += 500;
                    if (propType == 1) {
                        // 板变长
                        plank.width += 15;
                        p.exist = false;
                    } else if (propType == 2) {
                        // 球变大
                        ball.r += 5;
                        p.exist = false;
                    } else if (propType == 3) {
                        // 炸弹球
                        ball.color = Color.BLACK;
                        ball.type = 3;
                        p.exist = false;
                    } else if (propType == 4) {
                        // 穿刺球
                        ball.color = Color.MAGENTA;
                        ball.type = 4;
                        p.exist = false;
                    } else if (propType == 5) {
                        // 火箭球
                        ball.color = Color.orange;
                        speed++;
                        p.exist = false;
                    } else if (propType == 6) {
                        // 冰球
                        ball.color = Color.blue;
                        if (speed >= 3) speed--;
                        p.exist = false;
                    } else if (propType == 7) {
                        // 球变小
                        if (ball.r > 8) ball.r -= 2;
                        p.exist = false;
                    } else if (propType == 8) {
                        // 板变短
                        if (plank.width > 30) plank.width -= 20;
                        p.exist = false;
                    } else if (propType == 9) {
                        // 死亡
                        overFlag = true;
                        p.exist = false;
                    }
                } else if (p.y > GAME_HEIGHT) p.exist = false;
            }
            props.removeIf(p -> !p.exist);
        }
    }

    // 双缓冲解决小球闪烁问题
    Image offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);

    public void update(Graphics g) {
        Graphics gOffScreen = offScreenImage.getGraphics();
        gOffScreen.fillRect(GAME_X, GAME_Y, GAME_WIDTH, GAME_HEIGHT);
        paint(gOffScreen);
    }
}






