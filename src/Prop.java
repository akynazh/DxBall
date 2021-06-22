/**
 * @author jzh
 * @date 2021/6/21
 * class 道具类
 */

public class Prop {
    public int x, y;
    public int width, height;
    public int type;
    public boolean exist;

    public Prop(int x, int y, int width, int height, int type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.exist = true;
    }
}