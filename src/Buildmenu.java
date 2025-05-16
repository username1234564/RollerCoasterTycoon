import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
 
public class Buildmenu extends JPanel implements ActionListener{
    public Buildmenu(){
        JButton buildbutton = new JButton("build");
        JButton cancelButton = new JButton("Canel");

        buildbutton.addActionListener(this);
        buildbutton.setActionCommand("build");

        add(buildbutton);


    }

    public void actionPerformed(ActionEvent e) {
       if("build".equals(e.getActionCommand())){
            System.out.println("build works");
       }
    }
}