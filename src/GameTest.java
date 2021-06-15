import javax.swing.*;
import java.awt.*;

public class GameTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            var game = new GameInterface();
            game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
