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
//����� �����, ������ � ���� WChunk'�
public class Map {
    private Chunk[][] chunks;
    private int width, height;
    //���� ��� ���������� ����� �� ������ 50 �� 50.
    Map(){
        height = width = 50;
        chunks = new WChunk[height][width];
        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                chunks[i][j] = new WChunk(j*10,i*10, CONTENT.AIR);
            }
        }
        for(int j = 0; j<width; j++){
            chunks[16][j] = new WChunk(j*10, 160, CONTENT.GRASS);
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
                chunks[i][j] = new WChunk(j*10,i*10, c);
            }
        }
    }
    //���������� ������ �� WChunk �� ����������� X, Y.
    public WChunk getChunk(int x, int y){
        if(width*10 < y || height*10 < x)
            return null;
        int i = Math.round((y - y%10)/10.0f);
        int j = Math.round((x - x%10)/10.0f);
        return (WChunk)chunks[i][j];
    }
    //���������� ������ �� ������� WChunk'��
    //[7][0][1]
    //[6][X][2]
    //[5][4][3]
    //X - ��� WChunk
    public WChunk[] getChunkNeighbors(int x, int y){
        WChunk[] neighbors = {null, null, null, null, null, null, null, null};
        if(width*10 < y || height*10 < x)
            return null;
        int i = Math.round((y - y%10)/10.0f);
        int j = Math.round((x - x%10)/10.0f);
        try{
        if((i-1)>=0){
            neighbors[0] = (WChunk)chunks[i-1][j];
        }
        if(((i-1)>=0)&&(j+1)<=(width-1)){
            neighbors[1] = (WChunk)chunks[i-1][j+1];
        }
        if((j+1)<=(width-1)){
            neighbors[2] = (WChunk)chunks[i][j+1];
        }
        if(((i+1)<=(height-1))&&(j+1)<=(width-1)){
            neighbors[3] = (WChunk)chunks[i+1][j+1];
        }
        if((i+1)<=(height-1)){
            neighbors[4] = (WChunk)chunks[i+1][j];
        }
        if(((i+1)<=(height-1))&&((j-1)>=0)){
            neighbors[5] = (WChunk)chunks[i+1][j-1];
        }
        if((j-1)>=0){
            neighbors[6] = (WChunk)chunks[i][j-1];
        }
        if(((j-1)>=0)&&((i-1)>=0)){
            neighbors[7] = (WChunk)chunks[i-1][j-1];
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
