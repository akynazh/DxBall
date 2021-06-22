import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * @author jzh
 * @date 2021/6/21
 * class 游戏界面类
 */

public class GameInterface extends JFrame {
    public MyPanel myPanel;
    private final JMenu timeMenu;
    private final JMenu scoreMenu;
    public static final int GAME_X = 200, GAME_Y = 60, GAME_WIDTH = 900, GAME_HEIGHT = 700;

    public static double time = 0;
    public static int score = 0;
    public int passNum;
    public Timer timer;
    public boolean success, overFlag, continuePlay;

    public GameInterface(int speed, int passNum) {
        success = overFlag = continuePlay = false;
        this.passNum = passNum;
        setTitle("DxBall");
        setVisible(true);
        setBounds(GAME_X, GAME_Y, GAME_WIDTH, GAME_HEIGHT);
        myPanel = new MyPanel(speed, passNum);
        add(myPanel);

        // 设置菜单栏
        var menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        // 设置菜单选项
        var exitMenu = new JMenu("退出游戏");
        exitMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        scoreMenu = new JMenu("得分: " + score);
        scoreMenu.addActionListener(new TimeAndScoreActionListener());

        timeMenu = new JMenu("用时: " + time + "s");
        timeMenu.addActionListener(new TimeAndScoreActionListener());

        var colorMenu = new JMenu("改变游戏背景");
        colorMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Random r1 = new Random(), r2 = new Random(), r3 = new Random();
                int r, g, b;
                r = r1.nextInt(256);
                g = r2.nextInt(256);
                b = r3.nextInt(256);
                myPanel.setBackground(new Color(r, g, b));
                repaint();
            }
        });

        // 添加菜单选项
        menuBar.add(scoreMenu);
        menuBar.add(timeMenu);
        menuBar.add(exitMenu);
        menuBar.add(colorMenu);
    }

    // 监控游戏时间，得分情况，闯关情况
    private class TimeAndScoreActionListener implements ActionListener {
        public TimeAndScoreActionListener() {
            timer = new Timer(1000, this);
            timer.start();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!myPanel.overFlag) time += 0.5;
            if (!myPanel.overFlag) score = myPanel.bricksNum * 100 + myPanel.additionalScore;
            if (myPanel.overFlag) {
                overFlag = true;
                if (myPanel.success) success = true;
                if (myPanel.continuePlay) continuePlay = true;
            }
            timeMenu.setText("用时: " + time + "s");
            scoreMenu.setText("得分: " + score);
        }
    }
}
