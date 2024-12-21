import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Game implements KeyListener{
    JFrame frame;
    JPanel panel;
    Vector cameraPos;
    
    public Game(){
        frame = new JFrame("name");
        panel = new JPanel(){
            public void paintComponent(Graphics g){
                redraw(g);
            }
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(400, 400);
        frame.add(panel);
        cameraPos = new Vector(0, 60);
        

        frame.addKeyListener(this);
        frame.setFocusable(true);


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
        for(int i = 0; i<30; i++){
            g.drawLine(0 - cameraPos.x, 32 * i - cameraPos.y, 320 - cameraPos.x,  32 * i + 100 - cameraPos.y);
            g.drawLine( 0 - cameraPos.x, 32 * i + 100 - cameraPos.y, 320 - cameraPos.x, 32 * i - cameraPos.y);
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
    
}
