import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameInterface extends JFrame {
    private MyPanel myPanel;
    public static double time = 0;
    public static int score = 0;
    private JMenu timeMenu;
    private JMenu scoreMenu;
    public static Timer timer;

    public GameInterface(int speed) {
        setTitle("DxBall");
        setVisible(true);
        setBounds(300, 80, 600, 500);
        myPanel = new MyPanel(speed);
        add(myPanel);

        //设置菜单栏
        var menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        //设置菜单选项
        var exitMenu = new JMenu("Exit");
        exitMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        scoreMenu = new JMenu("Score: " + score);
        scoreMenu.addActionListener(new TimeAndScoreActionListener());

        timeMenu = new JMenu("Time: " + time);
        timeMenu.addActionListener(new TimeAndScoreActionListener());

        var colorMenu = new JMenu("^_^Change BackgroundColor^_^");
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

        //添加菜单选项
        menuBar.add(scoreMenu);
        menuBar.add(timeMenu);
        menuBar.add(exitMenu);
        menuBar.add(colorMenu);
    }

    //监控游戏时间和得分情况
    private class TimeAndScoreActionListener implements ActionListener {
        public TimeAndScoreActionListener() {
            timer = new Timer(1000,this);
            timer.start();
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!MyPanel.flag) time += 0.5;
            if (!MyPanel.flag) score = MyPanel.bricksNum * 100;
            timeMenu.setText("Time: " + time + "s");
            scoreMenu.setText("Score: " + score);
        }
    }
}
