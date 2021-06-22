import javax.swing.*;
import java.awt.*;

/**
 * @author jzh
 * @date 2021/6/21
 * class 测试游戏类
 */

public class GameTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame startInterface = new StartInterface();
            startInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
