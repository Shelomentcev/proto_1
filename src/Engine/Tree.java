package Engine;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: SED
 * Date: 30.10.13
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public class Tree implements Object{
    private Map map = null;
    private ArrayList<WChunk> parts = new ArrayList<WChunk>();
    Tree(){
        map = null;
    }
    Tree(Map m){
        map = m;
    }
    public boolean plantTree(int x, int y){
        WChunk seed = map.getChunk(x, y);
        if(seed!=null){
             /*
            [7][0][1]
            [6][X][2]
            [5][4][3]
            */
            WChunk[] neighbors = map.getChunkNeighbors(x, y);
            try{
                if(neighbors != null){
                    if(neighbors[0] == null || neighbors[0].contains()!= WChunk.CONTENT.AIR)
                        return false;
                    if(neighbors[2] == null || neighbors[2].contains() == WChunk.CONTENT.AIR)
                        return false;
                    if(neighbors[4] == null || neighbors[4].contains() == WChunk.CONTENT.AIR || neighbors[4].contains() == WChunk.CONTENT.STONE)
                        return false;
                    if(neighbors[6] == null || neighbors[6].contains() == WChunk.CONTENT.AIR)
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
        seed.replace(WChunk.CONTENT.SEED);
        parts.add(seed);
        return true;
    }
    public void start(){
        int size = parts.size();
        for(int i = 0; i<size; i++){
            WChunk c = parts.get(i);
            if(c.contains() == Chunk.CONTENT.SEED){
                c.replace(Chunk.CONTENT.TREE);
                WChunk[] arr = map.getChunkNeighbors(c.getX(), c.getY());
                if(arr[4].contains() == Chunk.CONTENT.DIRT){
                    arr[4].replace(Chunk.CONTENT.ROOT);
                    parts.add(arr[4]);
                } else {
                    System.out.println("Can't grove up!");
                    return;
                }
                if(arr[0].contains() == Chunk.CONTENT.AIR){
                    arr[0].replace(Chunk.CONTENT.LEAFS);
                    parts.add(arr[0]);
                } else {
                    System.out.println("Can't grove up!");
                    return;
                }
            }
        }
    }
    public WChunk[] getParts(){
        return parts.toArray(new WChunk[parts.size()]);
    }
}
