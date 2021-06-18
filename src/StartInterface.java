import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class StartInterface extends JFrame {
    private JPanel myPanel1 = new JPanel();
    private int choose = 0;
    private int delay = 20;
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
            var game = new GameInterface(delay);
            game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }});

        JButton optionButton2 = new JButton("exit");
        optionButton2.setBackground(Color.LIGHT_GRAY);
        optionButton2.addActionListener(event->{
            System.exit(0);
        });

        JButton optionButton3 = new JButton();
        optionButton3.setText("level");
        optionButton3.setBackground(new Color(135, 206, 235));
        optionButton3.addActionListener(event->{
            if (choose == 0) {
                optionButton3.setText("easy");
                choose = 1;
            } else if (choose == 1) {
                optionButton3.setText("hard");
                choose = 2;
                delay = 10;
            } else if(choose == 2) {
                optionButton3.setText("nightmare");
                choose = 0;
                delay = 5;
            }
        });

        add(optionButton1);
        add(optionButton2);
        add(optionButton3);
        optionButton1.setBounds(250, 100, 100, 50);
        optionButton2.setBounds(250, 150, 100, 50);
        optionButton3.setBounds(250, 200, 100, 50);




        add(myPanel1);
    }
}
