package Engine;

/**
 * Created with IntelliJ IDEA.
 * User: SED
 * Date: 31.10.13
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
import java.awt.Graphics2D;
public interface Chunk {
    public enum CONTENT{EMPTY, AIR, STONE, DIRT, GRASS, TREE, ROOT, LEAF, SEED}
    public void draw(Graphics2D g2d);
    public int getX();
    public int getY();
    public void setX(int nx);
    public void setY(int ny);
    public CONTENT contains();
}
