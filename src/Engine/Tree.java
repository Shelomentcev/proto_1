package Engine;
import java.awt.*;
import java.util.ArrayList;
/**
 * Created with IntelliJ IDEA.
 * User: SED
 * Date: 30.10.13
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public class Tree {
    private Map map = null;
    Tree(){
        map = null;
    }
    Tree(Map m){
        map = m;
    }
    public boolean plantTree(int x, int y){
        WorldChunk seed = map.getChunk(x, y);
        if(seed!=null){
             /*
            [7][0][1]
            [6][X][2]
            [5][4][3]
            */
            WorldChunk[] neighbors = map.getChunkNeighbors(x, y);
            try{
                if(neighbors != null){
                    if(neighbors[0] == null || neighbors[0].contains()!= WorldChunk.CONTENT.AIR)
                        return false;
                    if(neighbors[2] == null || neighbors[2].contains() == WorldChunk.CONTENT.AIR)
                        return false;
                    if(neighbors[4] == null || neighbors[4].contains() == WorldChunk.CONTENT.AIR || neighbors[4].contains() == WorldChunk.CONTENT.STONE)
                        return false;
                    if(neighbors[6] == null || neighbors[6].contains() == WorldChunk.CONTENT.AIR)
                        return false;
                } else {
                    return false;
                }
            }catch (Exception e){
                System.out.println("plantTree exception!");
            }
        }else{
            return false;
        }
        seed.replace(WorldChunk.CONTENT.SEED);

        return true;
    }
}
