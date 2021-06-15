import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameInterface extends JFrame {
    private JPanel myPanel = new JPanel();
    public GameInterface() {
        setTitle("DxBall");
        setVisible(true);
        setBounds(100, 100, 400, 700);
        JButton optionButton1 = new JButton("开始游戏");
        optionButton1.setBackground(Color.CYAN);
        JButton optionButton2 = new JButton("退出游戏");
        optionButton2.setBackground(Color.RED);
        optionButton2.addActionListener(new exitListener());
        JButton optionButton3 = new JButton("更换背景");
        optionButton3.setBackground(Color.YELLOW);
        optionButton3.addActionListener(new colorListener());
        myPanel.add(optionButton1);
        myPanel.add(optionButton2);
        myPanel.add(optionButton3);
        myPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));
        add(myPanel);
    }
    class colorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Random r1 = new Random(), r2 = new Random(), r3 = new Random();
            int r, g, b;
            r = r1.nextInt(256);
            g = r2.nextInt(256);
            b = r3.nextInt(256);
            myPanel.setBackground(new Color(r, g, b));
        }
    }

    class exitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
