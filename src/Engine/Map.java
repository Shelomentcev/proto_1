package Engine;

import Engine.Chunk.CONTENT;
import java.util.Random;
import java.awt.Graphics2D;
/**
 * Created with IntelliJ IDEA.
 * User: SED
 * Date: 30.10.13
 * Time: 9:03
 * To change this template use File | Settings | File Templates.
 */
//Класс карты, хранит в себе WorldChunk'и
public class Map {
    private Chunk[][] chunks;
    private int width, height;
    //Пока что генерирует сетку из чанков 50 на 50.
    Map(){
        height = width = 50;
        chunks = new WorldChunk[height][width];
        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                chunks[i][j] = new WorldChunk(j*10,i*10, CONTENT.AIR);
            }
        }
        for(int j = 0; j<width; j++){
            chunks[16][j] = new WorldChunk(j*10, 160, CONTENT.GRASS);
        }
        for(int i = 17; i<height; i++){
            for(int j = 0; j<width; j++){
                CONTENT c;
                Random r = new Random();
                if(r.nextBoolean()){
                    c = CONTENT.STONE;
                } else {
                    c = CONTENT.DIRT;
                }
                chunks[i][j] = new WorldChunk(j*10,i*10, c);
            }
        }
    }
    //Возвращает ссылку на WorldChunk по координатам X, Y.
    public WorldChunk getChunk(int x, int y){
        if(width*10 < y || height*10 < x)
            return null;
        int i = Math.round((y - y%10)/10.0f);
        int j = Math.round((x - x%10)/10.0f);
        return (WorldChunk)chunks[i][j];
    }
    //Возвращает массив из смежных WorldChunk'ов
    //[7][0][1]
    //[6][X][2]
    //[5][4][3]
    //X - наш WorldChunk
    public WorldChunk[] getChunkNeighbors(int x, int y){
        WorldChunk[] neighbors = {null, null, null, null, null, null, null, null};
        if(width*10 < y || height*10 < x)
            return null;
        int i = Math.round((y - y%10)/10.0f);
        int j = Math.round((x - x%10)/10.0f);
        try{
        if((i-1)>=0){
            neighbors[0] = (WorldChunk)chunks[i-1][j];
        }
        if(((i-1)>=0)&&(j+1)<=(width-1)){
            neighbors[1] = (WorldChunk)chunks[i-1][j+1];
        }
        if((j+1)<=(width-1)){
            neighbors[2] = (WorldChunk)chunks[i][j+1];
        }
        if(((i+1)<=(height-1))&&(j+1)<=(width-1)){
            neighbors[3] = (WorldChunk)chunks[i+1][j+1];
        }
        if((i+1)<=(height-1)){
            neighbors[4] = (WorldChunk)chunks[i+1][j];
        }
        if(((i+1)<=(height-1))&&((j-1)>=0)){
            neighbors[5] = (WorldChunk)chunks[i+1][j-1];
        }
        if((j-1)>=0){
            neighbors[6] = (WorldChunk)chunks[i][j-1];
        }
        if(((j-1)>=0)&&((i-1)>=0)){
            neighbors[7] = (WorldChunk)chunks[i-1][j-1];
        }
        } catch (Exception e){
            System.out.println("getChunkNeighbors exception!");
        }
        return neighbors;
    }
    public void Draw(Graphics2D g2d){
        for(int i = 0; i<50; i++){
            for(int j = 0; j<50; j++){
                chunks[i][j].draw(g2d);
            }
        }
    }
}
