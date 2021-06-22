import java.awt.*;

/**
 * @author jzh
 * @date 2021/6/21
 * class 球类
 */

public class Ball {
    public int x, y;
    public int r;
    public boolean hd, vd;
    public Color color;
    public int type;

    public Ball(int x, int y, int r, Color c) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.color = c;
        hd = vd = false;
    }
}