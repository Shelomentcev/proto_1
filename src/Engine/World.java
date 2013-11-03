package Engine;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Toolkit;

import Engine.Chunk.CONTENT;

public class World extends JPanel implements Runnable{
    public World(){
        map = new Map();
        tree = new Tree(map);
        selChunk = null;
        setBackground(Color.white);
        setDoubleBuffered(true);
    }
    private Thread animator;
    private final int DELAY = 50;
    private int interval = 0;
    //Клетка которая сейчас выделена
    private WChunk selChunk;
    Map map;
    Tree tree;
    //Заменить материал в выделенном на другой
    public void replaceSelected(CONTENT c){
        if((selChunk.contains() == CONTENT.LEAF) && (selChunk.getObject() == tree) && (c == CONTENT.TREE)){
            tree.growBranch(selChunk);
        } else {
            WChunk[] chunks = map.getChunkNeighbors(selChunk.getX(), selChunk.getY());
            //Просмотреть все 8 смежных клеток
            for(int i = 0; i<8; i++){
                WChunk chunk = chunks[i];
                if(chunk.getObject() == tree){
                    //Если рядом Chunk TREE и мы хотим вырастить листок(C == LEAF), то
                    if((chunk.contains() == CONTENT.TREE) && (c == CONTENT.LEAF) && (selChunk.contains() == CONTENT.AIR)){
                        //Растим листок
                        tree.growLeaf(selChunk);
                        //Выходим из цикла
                        break;
                    }
                    //По аналогии
                    if((chunk.contains() == CONTENT.TREE) && (c == CONTENT.TREE) && (selChunk.contains() == CONTENT.AIR)){
                        tree.growBranch(selChunk);
                        break;
                    }
                    if((chunk.contains() == CONTENT.ROOT) && (c == CONTENT.ROOT)&& (selChunk.contains() == CONTENT.DIRT)){
                        tree.growRoot(selChunk);
                        break;
                    }
                    if((chunk.contains() == CONTENT.ROOT) && (c == CONTENT.TREE) && (selChunk.contains() == CONTENT.AIR)){
                        tree.growBranch(selChunk);
                        break;
                    }
                }
            }
        }
    }
    public boolean plantSeed(int x, int y){
        return tree.plantTree(x, y);
    }
    public void addNotify(){
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        map.Draw(g2d);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    public void run(){
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while(true){
            repaint();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;
            if (sleep < 0)
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e){
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
    }
    //Выдиляет элемент по координатам x, y
    public void select(int x, int y){
        if(selChunk!=null){
            selChunk.selectOff();
        }
        selChunk = map.getChunk(x, y);
        if(selChunk!=null){
            selChunk.selectOn();
        }
    }
    public void nextTurn(){
        tree.start();
    }
}
