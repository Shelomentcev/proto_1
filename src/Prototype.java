import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Engine.World;
import Engine.Chunk.CONTENT;
/**
 * Created with IntelliJ IDEA.
 * User: SED
 * Date: 29.10.13
 * Time: 20:24
 * To change this template use File | Settings | File Templates.
 */
public class Prototype extends JFrame{
    private JLabel labelX, labelY;
    private JPanel tools;
    private World world;
    private CONTENT content;
    private boolean gameStarted = false;
    public Prototype(){
        content = CONTENT.DIRT;
        showGUI();
    }
    //Здесь создаётся интерфейс
    public void showGUI(){
        //Мышка
        PlayerInput adapter = new PlayerInput();
        //Мир
        world = new World();
        add(world);
        //Инструменты
        world.addMouseListener(adapter);
        world.addMouseMotionListener(adapter);
        labelX = new JLabel("X: 0");
        labelY = new JLabel("Y: 0");
        BtnActionListener listener = new BtnActionListener();
        JButton btnLeaf = new JButton("Leaf");
        btnLeaf.setName("btnLeaf");
        btnLeaf.addActionListener(listener);
        JButton btnTree = new JButton("Tree");
        btnTree.setName("btnTree");
        btnTree.addActionListener(listener);
        JButton btnRoot = new JButton("Root");
        btnRoot.setName("btnRoot");
        btnRoot.addActionListener(listener);
        JButton btnStart = new JButton("NEXT");
        btnStart.setName("btnStart");
        btnStart.addActionListener(listener);
        tools = new JPanel();
        Border toolsBorder = BorderFactory.createTitledBorder("TOOLS");
        tools.setBorder(toolsBorder);
        tools.add(labelX);
        tools.add(labelY);
        tools.add(btnRoot);
        tools.add(btnTree);
        tools.add(btnLeaf);
        tools.add(btnStart);
        tools.setPreferredSize(new Dimension(100, 500));
        add(tools, BorderLayout.LINE_END);
        //Остальное
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setTitle("Prototype");
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args){
        new Prototype();
    }

    private class BtnActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Component c = (Component)e.getSource();
            if(c.getName() == "btnTree"){
                content = CONTENT.TREE;
            } else if(c.getName() == "btnRoot") {
                content = CONTENT.ROOT;
            } else if(c.getName() == "btnLeaf") {
                content = CONTENT.LEAF;
            } else if(c.getName() == "btnStart") {
                world.nextTurn();
            }
        }
    }

    private class PlayerInput extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent event){
            if(gameStarted){
                world.replaceSelected(content);
            } else {
                if(world.plantSeed(event.getX(),event.getY())){
                    gameStarted = true;
                } else {
                    System.out.println("Can't plant tree here...");
                }
            }
        }
        @Override
        public void mouseMoved(MouseEvent event){
            labelX.setText("X: " + event.getX());
            labelY.setText("Y: " + event.getY());
            world.select(event.getX(),event.getY());
        }
    }
}
