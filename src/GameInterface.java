import javax.swing.*;

public class GameInterface extends JFrame {
    JPanel myBall = new MyBallPanel(new Ball());
    public GameInterface() {
        setTitle("DxBall");
        setVisible(true);
        setBounds(300, 80, 600, 500);
        add(myBall);
    }
}
