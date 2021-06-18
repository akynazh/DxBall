import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class GameInterface extends JFrame {
    private MyBall myBall;
    private MyBrick myBrick;
    private int time = 0;
    private int score = 0;

    public GameInterface(int delay) {
        setTitle("DxBall");
        setVisible(true);
        setBounds(300, 80, 600, 500);
        myBall = new MyBall(delay);
        add(myBall);

        var menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        var exitMenu = new JMenu("Exit");
        exitMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        var scoreMenu = new JMenu("Score: " + score);
        var timeMenu = new JMenu("Time: " + time);
        var colorMenu = new JMenu("|Change BackgroundColor|");
        colorMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Random r1 = new Random(), r2 = new Random(), r3 = new Random();
                int r, g, b;
                r = r1.nextInt(256);
                g = r2.nextInt(256);
                b = r3.nextInt(256);
                myBall.setBackground(new Color(r, g, b));
                repaint();
            }
        });

        menuBar.add(scoreMenu);
        menuBar.add(timeMenu);
        menuBar.add(exitMenu);
        menuBar.add(colorMenu);
    }
}
