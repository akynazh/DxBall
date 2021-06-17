import javax.swing.*;
import java.awt.*;

public class GameTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            var start = new StartInterface();
            System.out.println("Start Game");
            start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
