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
    Vector lastCoasterTract = new Vector(0,1,0);


    
    public Game(){
        frame = new JFrame("RollerCoasterTycoon");
        panel = new JPanel(){
            public void paintComponent(Graphics g){
                redraw(g);
            }
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(900, 900);
        //JLayeredPane lpane = new JLayeredPane();
        //frame.add(lpane);
        //lpane.add(panel);
        //lpane.add(new Buildmenu());
        frame.add(panel);
        //panel.add(new Buildmenu());
        cameraPos = new Vector(0, 60, 0);
        cameraVelocity = new Vector(0, 0, 0);
        
        panel.addMouseListener(this);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.addMouseMotionListener(this);

        grid.build(new RailTrack(new Vector(0, 1, 0), new Vector(0, 0, 0), new Vector(0, 2, 0)));
        //grid.build(new RailTrack(new Vector(0, 3, 0), null, null));
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
                if(building != null && building.position.equals(new Vector(i, j, 0))){
                    Vector v = gridToWorld(new Vector(i, j, 0));
                    
                    g.drawImage(building.image, v.x + building.offset.x, v.y + 32 - Tile.HEIGHT + building.offset.y - (building.position.z * 50), null); //the 30 is a counter offset
                }
            }          
        }

        g.setColor(Color.RED);
        g.fillRect(50, 50, 100, 100); // Positioned at (50,50), size 100x100

        // If ghost is active, adjust color
        if (ghost != null) {
            g.setColor(Color.GREEN);
            g.fillRect(50, 50, 100, 100); // Make green if ghost is activated
        }

        if(ghost == null)
            return;
        float alpha = (float) 0.5;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha); //taken fron stack overflow
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

        // Check if click was inside the build menu square
        if (x >= 50 && x <= 150 && y >= 50 && y <= 150) {
            if (ghost == null) {
                ghost = new ArcadeMachine(new Vector(0, 0, 0)); // Enable preview
                System.out.println("Build enabled!");
            } else {
                ghost = null; // Disable ghost
                System.out.println("Build disabled!");
            }
            frame.repaint(); // Refresh screen to update color
            return;
        }

        // Convert mouse click to grid position
        Vector v = worldToGrid(new Vector(x, y, 0));

        // If ghost mode is enabled, place a new arcade machine
        if (v.x >= 0 && v.y >= 0 && ghost != null) {
            grid.build(new ArcadeMachine(v));
        } else {
            // Check if the clicked tile contains an Arcade Machine
            Building clickedBuilding = grid.getTile(v).building;
            if (clickedBuilding instanceof ArcadeMachine) {
                openBrickBreaker(); // Launch the mini-game
            }
        }
    }

    public void openBrickBreaker() {
        JFrame brickBreakerFrame = new JFrame("Brick Breaker Arcade");
        brickBreakerFrame.setSize(400, 500);
        brickBreakerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        BrickBreakerGame gamePanel = new BrickBreakerGame(); // The mini-game panel
        brickBreakerFrame.add(gamePanel);
        
        brickBreakerFrame.setVisible(true);
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
