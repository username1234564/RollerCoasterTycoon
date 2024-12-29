import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Game implements KeyListener{
    JFrame frame;
    JPanel panel;
    Vector cameraPos;
    Grid grid = new Grid(30);
    
    public Game(){
        frame = new JFrame("name");
        panel = new JPanel(){
            public void paintComponent(Graphics g){
                redraw(g);
            }
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(900, 900);
        frame.add(panel);
        cameraPos = new Vector(0, 60, 0);
        

        frame.addKeyListener(this);
        frame.setFocusable(true);

        grid.build(new Building("src/Transparent Tile Cube.png"), new Vector(1, 3, 0)); 
    }   

    public void start(){
        while(true){
            update(1000.0/60);
            frame.repaint();
            try {
                Thread.sleep(1000 / 60);
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }

    public void update(double delta){
        
    }


    public void redraw(Graphics g){
        for(int i = 0; i<=grid.size; i++){
            g.drawLine(i * Tile.WIDTH / 2 - cameraPos.x, i * Tile.HEIGHT / 2 - cameraPos.y, i * Tile.WIDTH / 2 + grid.size / 2 * Tile.WIDTH - cameraPos.x, i * Tile.HEIGHT / 2 - grid.size / 2 * Tile.HEIGHT - cameraPos.y);
            g.drawLine(i * Tile.WIDTH / 2 - cameraPos.x, - i * Tile.HEIGHT / 2 - cameraPos.y, i * Tile.WIDTH / 2 + grid.size / 2 * Tile.WIDTH - cameraPos.x, -i * Tile.HEIGHT / 2 + grid.size / 2 * Tile.HEIGHT - cameraPos.y);
        }
        for(int i = 0; i<grid.size; i++){
            for(int j = 0; j<grid.size; j++){
                if(grid.getTile(new Vector(i, j, 0)).building != null){
                    g.drawImage(grid.getTile(new Vector(i, j, 0)).building.image, i, j, 0);
                }
            }
        }
        
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_W)
            cameraPos.y -= 5;
        else if(keyCode == KeyEvent.VK_S)
            cameraPos.y += 5;
        else if(keyCode == KeyEvent.VK_A)
            cameraPos.x -= 5;
        else if(keyCode == KeyEvent.VK_D)
            cameraPos.x += 5;
    }

    public void keyReleased(KeyEvent e) {

    }
/* 
    public Vector worldToGrid(Vector worldPosition){
        
    }
  */  
}
