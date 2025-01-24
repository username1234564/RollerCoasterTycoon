import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Game implements KeyListener, MouseListener{
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
        
        frame.addMouseListener(this);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        for(int i = grid.size-1; i>=0; i--){
            for(int j = 0; j<grid.size; j++){
                //grid.build(new Building("src/Cube.png"), new Vector(i, j, 0)); 
            }
        }
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
        for(int i = grid.size-1; i>=0; i--){
            for(int j = 0; j<grid.size; j++){
                if(grid.getTile(new Vector(i, j, 0)).building != null){
                    Vector v = gridToWorld(new Vector(i, j, 0));
                    g.drawImage(grid.getTile(new Vector(i, j, 0)).building.image, v.x, v.y - Tile.HEIGHT, null);
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

    public Vector worldToGrid(Vector v) {
        v.x = v.x - v.x % Tile.WIDTH;
        v.y = v.y - v.y % Tile.HEIGHT;
        int y = ((2 * (v.y + cameraPos.y)) / Tile.HEIGHT + (v.x + cameraPos.x) / (Tile.WIDTH / 2)) / 2;
        int x = ((v.x + cameraPos.x) / (Tile.WIDTH / 2)) - y;
        
        return new Vector(x, y, 0);
    }
    
  
  
    public Vector gridToWorld(Vector v){
        int x = v.x * Tile.WIDTH/2 + v.y * Tile.WIDTH / 2 - cameraPos.x;
        int y = - v.x * Tile.HEIGHT/2 + v.y * Tile.HEIGHT / 2- cameraPos.y;
        return new Vector(x, y, 0);
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(worldToGrid(new Vector(x, y, 0)));
    }

    public void mousePressed(MouseEvent e) {
       
    }

    public void mouseReleased(MouseEvent e) {
        
    }


    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {

    }
}
