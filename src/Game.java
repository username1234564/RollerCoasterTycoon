import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Game implements KeyListener, MouseListener, MouseMotionListener{
    JFrame frame;
    JPanel panel;
    Vector cameraPos;
    Vector cameraVelocity;
    Grid grid = new Grid(30);
    Vector mousePosition = new Vector(0, 0, 0);
    Building ghost = new ArcadeMachine(new Vector(0, 0, 0));

    
    public Game(){
        frame = new JFrame(" ult");
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
        cameraVelocity = new Vector(0, 0, 0);
        
        panel.addMouseListener(this);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.addMouseMotionListener(this);


        grid.build(new ArcadeMachine(new Vector(1, 1, 2)));
                 
        grid.build(new Floor(new Vector(3, 4, 3)));

        grid.build(new ArcadeMachine(new Vector(6, 2, 1)));
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
        cameraPos = cameraPos.add(cameraVelocity);
        mousePosition = new Vector(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, 0);  //mouse position is based on screen
        mousePosition = mousePosition.subtract(new Vector(panel.getLocationOnScreen().x, panel.getLocationOnScreen().y, 0));
        System.out.println(mousePosition);
    }


    public void redraw(Graphics g){

        int w = Tile.WIDTH / 2;
        int h = Tile.HEIGHT / 2;

        for(int i = 0; i<=grid.size; i++){
            Vector origin = new Vector(w * i, h * i, 0).subtract(cameraPos);
            Vector end = origin.add(new Vector(w * grid.size, -h * grid.size, 0));
            g.drawLine(origin.x, origin.y, end.x, end.y);

            origin = new Vector(w * i, -h * i, 0).subtract(cameraPos);
            end = origin.add(new Vector(w * grid.size, h * grid.size, 0));
            g.drawLine(origin.x, origin.y, end.x, end.y);
        }
        for(int i = grid.size-1; i>=0; i--){
            for(int j = 0; j<grid.size; j++){
                Building building = grid.getTile(new Vector(i, j, 0)).building;
                if(building != null){
                    Vector v = gridToWorld(new Vector(i, j, 0));
                    
                    g.drawImage(building.image, v.x + building.offset.x, v.y + 32 - Tile.HEIGHT + building.offset.y - (building.position.z * 50), null); //the 30 is a counter offset
                }
            }          
        }

        if(ghost == null)
            return;
        float alpha = (float) 0.5;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
        ((Graphics2D) g).setComposite(ac);
        Vector v = gridToWorld(worldToGrid(mousePosition)); //snap to grid
        g.drawImage(ghost.image, v.x + ghost.offset.x, v.y + 32 - Tile.HEIGHT + ghost.offset.y, null);
        
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_W)
            cameraVelocity.y = -5;
        if(keyCode == KeyEvent.VK_S)
            cameraVelocity.y = 5;
        if(keyCode == KeyEvent.VK_A)
            cameraVelocity.x = -5;
        if(keyCode == KeyEvent.VK_D)
            cameraVelocity.x = 5;
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_W && cameraVelocity.y == -5)
            cameraVelocity.y = 0;
        if(keyCode == KeyEvent.VK_S && cameraVelocity.y == 5)
            cameraVelocity.y = 0;
        if(keyCode == KeyEvent.VK_A && cameraVelocity.x == -5)
            cameraVelocity.x = 0;
        if(keyCode == KeyEvent.VK_D && cameraVelocity.x == 5)
            cameraVelocity.x = 0;
    }

    public Vector worldToGrid(Vector v) {
        v.add(new Vector(0, -16, 0));
        v = v.add(cameraPos);

        int w = Tile.WIDTH / 2;
        int h = Tile.HEIGHT / 2;
        
        double x = 0.5 * (v.x * 1.0 / w - v.y * (1.0 / h));
        double y = 0.5 * (v.x * 1.0 / w + v.y * (1.0 / h));

         return new Vector((int)x, (int)y, v.z);
       
    }

    public Vector gridToWorld(Vector v){
        int w = Tile.WIDTH / 2;
        int h = Tile.HEIGHT / 2;
        
        int x = w * (v.y + v.x) - cameraPos.x;
        int y = h * (v.y - v.x) - cameraPos.y;

        return new Vector(x, y, v.z);
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(worldToGrid(new Vector(x, y, 0)));
        Vector v = worldToGrid(new Vector(x, y, 0));
        grid.build(new ArcadeMachine(v));
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

    }
}
