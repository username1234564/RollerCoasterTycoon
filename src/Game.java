import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Game implements KeyListener, MouseListener, MouseMotionListener{
    JFrame frame;
    JPanel panel;
    Vector cameraPos;
    Grid grid = new Grid(30);
    Vector mousePosition = new Vector(0, 0, 0);
    
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
        frame.addMouseMotionListener(this);
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
        System.out.println(worldToGrid(mousePosition));
    }


    public void redraw(Graphics g){

        int w = Tile.WIDTH / 2;
        int h = Tile.HEIGHT / 2;

        for(int i = 0; i<=grid.size; i++){

            
            /* 
            g.drawLine(i * Tile.WIDTH / 2 - cameraPos.x, i * Tile.HEIGHT / 2 - cameraPos.y, i * Tile.WIDTH / 2 + grid.size / 2 * Tile.WIDTH - cameraPos.x, i * Tile.HEIGHT / 2 - grid.size / 2 * Tile.HEIGHT - cameraPos.y);
            g.drawLine(i * Tile.WIDTH / 2 - cameraPos.x, - i * Tile.HEIGHT / 2 - cameraPos.y, i * Tile.WIDTH / 2 + grid.size / 2 * Tile.WIDTH - cameraPos.x, -i * Tile.HEIGHT / 2 + grid.size / 2 * Tile.HEIGHT - cameraPos.y);
            */
            
            Vector origin = new Vector(w * i, h * i, 0).subtract(cameraPos);
            Vector end = origin.add(new Vector(w * grid.size, -h * grid.size, 0));
            g.drawLine(origin.x, origin.y, end.x, end.y);

            origin = new Vector(w * i, -h * i, 0).subtract(cameraPos);
            end = origin.add(new Vector(w * grid.size, h * grid.size, 0));
            g.drawLine(origin.x, origin.y, end.x, end.y);
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
        //v.x = v.x - v.x % (Tile.WIDTH * 2);
        //v.y = v.y - v.y % (Tile.HEIGHT * 2);
        //int y = ((2 * (v.y + cameraPos.y)) / (Tile.HEIGHT / 2) + (v.x + cameraPos.x) / (Tile.WIDTH / 2)) / 2;
        //int x = ((v.x + cameraPos.x) / (Tile.WIDTH / 2)) - y;
        
        v = v.add(cameraPos);

        int w = Tile.WIDTH / 2;
        int h = Tile.HEIGHT / 2;
        
        double x = 0.5 * (v.x * 1.0 / w - v.y * 1.0 / h);
        double y = 0.5 * (v.x * 1.0 / w + v.y * 1.0 / h);
        /* 
        Vector ve = snapToGrid(v);

        int x = (ve.x / (Tile.WIDTH / 2) + ve.y / (Tile.HEIGHT / 2)) / 2;
        int y = (ve.y / (Tile.HEIGHT / 2 ) - ve.x / (Tile.WIDTH / 2)) / 2;
        return new Vector(x, y, 0);
        
        double slopeL1 = (double) Tile.HEIGHT / Tile.WIDTH;
        double slopeL2 = (double) -Tile.HEIGHT / Tile.WIDTH;
 
         double b1 = v.y - v.x * slopeL1;
         double b2 = v.y - v.x * slopeL2;
 
         double x2 = b1 / (slopeL2 - slopeL1);
         double x = x2 / (Tile.WIDTH /2);
         
         double x1 = b2 / (slopeL1 - slopeL2);
         double y = x1 / (Tile.HEIGHT /2);
        */

         return new Vector((int)x, (int)y, 0);
         //double y2 = slopeL2 * x2;
         
        // double length2 = Math.sqrt(x2 * x2 + y2 * y2);
    }
    
    //public Vector snapToGrid(Vector v){
 

    //}
    
    public Vector gridToWorld(Vector v){
        int w = Tile.WIDTH / 2;
        int h = Tile.HEIGHT / 2;
        
        //int x = (v.x - v.y) * Tile.WIDTH / 2 + cameraPos.x;
        //int y = (v.x + v.y) * Tile.HEIGHT / 2 + cameraPos.y;

        //int x = v.x * Tile.WIDTH/2 + v.y * Tile.WIDTH / 2 - cameraPos.x;
        //int y = - v.x * Tile.HEIGHT/2 + v.y * Tile.HEIGHT / 2- cameraPos.y;

        int x = w * (v.y + v.x) + cameraPos.x;
        int y = h * (v.y - v.x) + cameraPos.y;

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

    public void mouseDragged(MouseEvent e) {
       
    }

    public void mouseMoved(MouseEvent e) {
        mousePosition = new Vector(e.getX(), e.getY(), 0).subtract(new Vector(7, 30, 0));
        //System.out.println(mousePosition);
    }
}
