package Engine;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Created with IntelliJ IDEA.
 * User: SED
 * Date: 30.10.13
 * Time: 8:29
 * To change this template use File | Settings | File Templates.
 */

public class WChunk implements Chunk {
    private int x, y;
    private boolean selected;
    private CONTENT content;
    //Объект к которому принадлежжит WChunk
    private Object  obj = null;
    WChunk(){
        this.x = 0;
        this.y = 0;
        this.content = CONTENT.EMPTY;
        this.selected = false;
    }
    WChunk(int x, int y){
        this.x = x;
        this.y = y;
        this.content = CONTENT.EMPTY;
        this.selected = false;
    }
    WChunk(int x, int y, CONTENT cont){
        this.x = x;
        this.y = y;
        this.content = cont;
        this.selected = false;
    }
    public CONTENT contains(){
        return content;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int nx){
        this.x = nx;
    }
    public void setY(int ny){
        this.y = ny;
    }
    //Задаёт новое содержимое чанка
    public void replace(CONTENT c){
        content = c;
    }
    //Включает выделение
    public void selectOn(){
        selected = true;
    }
    //Выключает выделение
    public  void selectOff(){
        selected = false;
    }
    //Присвоить WChunk объекту
    public void setObject(Object nobject){
        this.obj = nobject;
    }
    //Получить хозяина WChunk'a
    public Object getObject(){
        return this.obj;
    }
    //Возвращает цвет в зависимости от материала
    private Color color(){
        Color c = new Color(255, 20, 147);
        switch(content){
            case EMPTY: c = new Color(255, 255, 255);   break;
            case AIR:   c = new Color(174,238,238);     break;
            case STONE: c = new Color(190, 190, 190);   break;
            case DIRT:  c = new Color(139, 69, 19);     break;
            case GRASS: c = new Color(188, 238, 104);   break;
            case TREE:  c = new Color(165, 42, 42);     break;
            case ROOT:  c = new Color(125, 42, 42);     break;
            case LEAF: c = new Color(0, 238, 0);       break;
            case SEED:  c = new Color(255, 255, 20);    break;
            default:    c = new Color(255, 20, 147);    break;
        }
        return c;
    }
    @Override
    public void draw(Graphics2D g2d){
        g2d.setColor(this.color());
        g2d.fillRect(this.x, this.y, 10, 10);
        if(this.selected){
            g2d.setColor(new Color(255, 255, 255));
            g2d.drawRect(this.x, this.y, 10, 10);
        }
    }
}
