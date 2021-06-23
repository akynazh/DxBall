import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

/**
 * @author jzh
 * @date 2021/6/21
 * class 开始界面类
 */

public class StartInterface extends JFrame {
    private int choose = 0, speed = 3, passNum = 0;
    public final int GAME_X = GameInterface.GAME_X, GAME_Y = GameInterface.GAME_Y,
            GAME_WIDTH = GameInterface.GAME_WIDTH, GAME_HEIGHT = GameInterface.GAME_HEIGHT;

    public StartInterface() {
        setTitle("DxBall");
        setVisible(true);
        setBounds(GAME_X, GAME_Y, GAME_WIDTH, GAME_HEIGHT);
        JPanel myPanel = new JPanel();
        myPanel.setBackground(new Color(82, 139, 139));

        // 游戏名称
        JLabel gameName = new JLabel("DxBall");
        gameName.setFont(new Font("Times New Roman", Font.BOLD, 100));
        myPanel.add(gameName);

        // 规则目录
        JButton textButton = new JButton();
        textButton.setBackground(Color.gray);
        textButton.setText("<html>" +
                "1.基本原则：接住小球并打下尽可能多的砖块。<br>" +
                "2.键盘操作：按左右键即可移动砖块。<br>" +
                "3.关卡设置：菜鸟；新手；专业；大师。<br>" +
                "4.过关要求：获得超过2000分则可过关。<br>" +
                "5.加分规则1：每打一个砖块加100分。<br>" +
                "6.加分规则2：每接收到一个道具加500分。<br>" +
                "7.技巧1：低难度下尽可能在短时间内获得高分。<br>" +
                "8.技巧2：高难度下尽可能存活更长时间。<br>" +
                "9.道具说明：撞击砖块会随机掉落道具。<br>" +
                "10.道具类型：<br>" +
                "板变长（绿）；球变大（白）；炸弹球（红）；<br>" +
                "穿刺球（粉）；火箭球（橙）；冰球；（蓝）；<br>" +
                "球变小（紫）；板变短（棕）；死亡（黑）。<br>" +
                "<html/>");

        // 开始游戏按钮
        JButton optionButton1 = new JButton("开始游戏");
        optionButton1.setBackground(Color.white);
        optionButton1.addActionListener(event -> {
            Queue<GameInterface> gameInterfaces = new ArrayDeque<>();
            gameInterfaces.add(new GameInterface(speed, 0));
            assert gameInterfaces.peek() != null;
            gameInterfaces.peek().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // 检测闯关成功与否
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    assert gameInterfaces.peek() != null;
                    if (gameInterfaces.peek().success && gameInterfaces.peek().continuePlay) {
                        gameInterfaces.peek().timer.stop();
                        assert gameInterfaces.peek() != null;
                        gameInterfaces.peek().remove(myPanel);
                        assert gameInterfaces.peek() != null;
                        gameInterfaces.peek().dispose();
                        gameInterfaces.poll();
                        speed++;
                        passNum++;
                        GameInterface.time = 0;
                        GameInterface.score = 0;
                        gameInterfaces.add(new GameInterface(speed, passNum));
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (gameInterfaces.peek().restartFlag) {
                        gameInterfaces.peek().timer.stop();
                        assert gameInterfaces.peek() != null;
                        gameInterfaces.peek().remove(myPanel);
                        assert gameInterfaces.peek() != null;
                        gameInterfaces.peek().dispose();
                        gameInterfaces.poll();
                        GameInterface.time = 0;
                        GameInterface.score = 0;
                        passNum = 0;
                        speed = 3;
                        timer.cancel();
                    }
                }
            }, 1000, 2000);
        });

        // 退出游戏按钮
        JButton optionButton2 = new JButton("退出游戏");
        optionButton2.setBackground(Color.LIGHT_GRAY);
        optionButton2.addActionListener(event -> System.exit(0));

        // 难度选择按钮
        JButton optionButton3 = new JButton("选择难度");
        optionButton3.setBackground(new Color(135, 206, 235));
        optionButton3.addActionListener(event -> {
            if (choose == 0) {
                optionButton3.setText("菜鸟");
                choose = 1;
                speed = 3;
            } else if (choose == 1) {
                optionButton3.setText("新手");
                choose = 2;
                speed = 4;
            } else if (choose == 2) {
                optionButton3.setText("专业");
                choose = 3;
                speed = 5;
            } else if (choose == 3) {
                optionButton3.setText("大师");
                choose = 0;
                speed = 6;
            }
        });

        // 查看规则按钮
        JButton optionButton4 = new JButton();
        optionButton4.setText("游戏规则");
        optionButton4.setBackground(new Color(84, 255, 159));
        optionButton4.addActionListener(event -> {
            add(textButton);
            textButton.setBounds(GAME_WIDTH / 2 - 300 / 2, GAME_HEIGHT / 4 + 180, 300, 250);
        });

        // 添加按钮
        add(optionButton1);
        add(optionButton2);
        add(optionButton3);
        add(optionButton4);
        optionButton1.setBounds(GAME_WIDTH / 2 - 100 / 2, GAME_HEIGHT / 4, 100, 45);
        optionButton2.setBounds(GAME_WIDTH / 2 - 100 / 2, GAME_HEIGHT / 4 + 45, 100, 45);
        optionButton3.setBounds(GAME_WIDTH / 2 - 100 / 2, GAME_HEIGHT / 4 + 90, 100, 45);
        optionButton4.setBounds(GAME_WIDTH / 2 - 100 / 2, GAME_HEIGHT / 4 + 135, 100, 45);

        // 添加面板
        add(myPanel);
    }
}
