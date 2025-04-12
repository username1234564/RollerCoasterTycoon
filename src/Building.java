import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Building {
    BufferedImage image;
    Vector offset;
    Vector position;
     
    public Building(String filePath, Vector offset, Vector position){
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.offset = offset;
        this.position = position;
    }
}
