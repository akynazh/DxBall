import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class StartInterface extends JFrame {
    private JPanel myPanel1 = new JPanel();
    public StartInterface() {
        setTitle("DxBall");
        setVisible(true);
        setBounds(300, 80, 600, 500);
        myPanel1.setBackground(new Color(100, 149, 237));

        JLabel gameName = new JLabel("DxBall");
        gameName.setFont(new Font("Times New Roman", Font.BOLD, 50));
        myPanel1.add(gameName);

        JButton optionButton1 = new JButton("start");
        optionButton1.setBackground(Color.white);
        optionButton1.addActionListener(event->{{
            dispose();
            var game = new GameInterface();
            game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }});

        JButton optionButton2 = new JButton("exit");
        optionButton2.setBackground(Color.LIGHT_GRAY);
        optionButton2.addActionListener(event->{
            System.exit(0);
        });

        JButton optionButton3 = new JButton("level");
        optionButton3.setBackground(new Color(135, 206, 235));

        add(optionButton1);
        add(optionButton2);
        add(optionButton3);
        optionButton1.setBounds(250, 100, 100, 50);
        optionButton2.setBounds(250, 150, 100, 50);
        optionButton3.setBounds(250, 200, 100, 50);


        JButton colorOption = new JButton("color");
        add(colorOption);
        colorOption.setBounds(0, 0, 70, 15);
        colorOption.addActionListener(event->{
            Random r1 = new Random(), r2 = new Random(), r3 = new Random();
            int r, g, b;
            r = r1.nextInt(256);
            g = r2.nextInt(256);
            b = r3.nextInt(256);
            myPanel1.setBackground(new Color(r, g, b));
            repaint();
        });

        add(myPanel1);
    }
}
