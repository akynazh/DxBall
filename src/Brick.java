/**
 * @author jzh
 * @date 2021/6/21
 * class 砖块类
 */

public class Brick {
    public int x, y;
    public int width, height;
    public int type;
    public boolean exist;
    public boolean propFall;


    public Brick(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.exist = true;
        this.propFall = false;
    }
}