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
    private int minerals = 0, energy = 25, water = 0;
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
        //seed.replace(WChunk.CONTENT.SEED);
        //parts.add(seed);
        addPart(seed, WChunk.CONTENT.SEED);
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
                    //arr[4].replace(Chunk.CONTENT.ROOT);
                    //parts.add(arr[4]);
                    addPart(arr[4], Chunk.CONTENT.ROOT);
                } else {
                    System.out.println("Can't grove up!");
                    return;
                }
                if(arr[0].contains() == Chunk.CONTENT.AIR){
                    //arr[0].replace(Chunk.CONTENT.LEAF);
                    //parts.add(arr[0]);
                    addPart(arr[0], Chunk.CONTENT.LEAF);
                } else {
                    System.out.println("Can't grove up!");
                    return;
                }
            } else if(c.contains() == Chunk.CONTENT.ROOT){
                minerals+=5;
                water+=10;
                energy-=1;
            } else if(c.contains() == Chunk.CONTENT.TREE) {
                minerals-=2;
                water-=2;
            } else  if(c.contains() == Chunk.CONTENT.LEAF) {
                water-=8;
                minerals-=3;
                energy+=2;
            }
        }
       if(water<0){
            energy+=water;
            water = 0;
        }
        if (minerals<0){
            energy+=minerals;
            minerals = 0;
        }
        if (energy<0) {
            this.partDie();
            System.out.println("Tree is dying!");
        }
    }
    public WChunk[] getParts(){
        return parts.toArray(new WChunk[parts.size()]);
    }
    //Добавляет часть к дереву.
    //НЕ ДОБОВЛЯТЬ части через parts.add !!!
    //Только через этот метод.
    private void addPart(WChunk chunk, Chunk.CONTENT cont){
        if(cont == Chunk.CONTENT.TREE){
            if(energy<5){
                System.out.println("Need more energy!");
                return;
            } else {
                energy-=5;
            }
        } else if (cont == Chunk.CONTENT.ROOT){
            if(energy<10){
                System.out.println("Need more energy!");
                return;
            } else {
                energy-=10;
            }
        } else if (cont == Chunk.CONTENT.LEAF){
            if(energy<10){
                System.out.println("Need more energy!");
                return;
            } else {
                energy-=10;
            }
        }
        chunk.replace(cont);
        chunk.setObject(this);
        parts.add(chunk);
    }
    //Отрастить ещё древесную часть(TREE).
    public void growBranch(WChunk chunk){
        //this.addPart(chunk, Chunk.CONTENT.TREE);
        if(chunk.contains() == Chunk.CONTENT.LEAF){
            this.addPart(chunk, Chunk.CONTENT.TREE);
            WChunk[] n = map.getChunkNeighbors(chunk.getX(), chunk.getY());
            if((n[4]!=null) && (n[0]!=null) && (n[0].contains() == Chunk.CONTENT.AIR) && (n[4].contains() == Chunk.CONTENT.TREE)){
                growLeaf(n[0]);
            }else if((n[2]!=null) && (n[6]!=null) && (n[6].contains() == Chunk.CONTENT.AIR) && (n[2].contains() == Chunk.CONTENT.TREE)){
                growLeaf(n[6]);
            }else if((n[6]!=null) && (n[2]!=null) && (n[2].contains() == Chunk.CONTENT.AIR)&& (n[6].contains() == Chunk.CONTENT.TREE)){
                growLeaf(n[2]);
            }
        } else {
            this.addPart(chunk, Chunk.CONTENT.TREE);
        }
    }
    //Отрастить ещё корней
    public void growRoot(WChunk chunk){
        this.addPart(chunk, Chunk.CONTENT.ROOT);
    }
    //Отрастить ещё листвы
    public void growLeaf(WChunk chunk){
        this.addPart(chunk, Chunk.CONTENT.LEAF);
    }
    //Возвращает кол-во минералов
    public int getMinerals(){
        return minerals;
    }
    //Возвращает кол-во энергии
    public int getEnergy(){
        return energy;
    }
    //Возвращает кол-во воды
    public int getWater(){
        return water;
    }
    //
    public void partDie(){
       int size = parts.size();
       for(int i = 0; i<size; i++){
           WChunk c = parts.get(i);
           if(c.contains() == Chunk.CONTENT.LEAF){
               c.setObject(null);
               c.replace(Chunk.CONTENT.AIR);
               parts.remove(i);
               break;
           }
       }
    }
}
