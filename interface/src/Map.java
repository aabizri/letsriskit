import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

import javax.swing.*;

public class Map extends JPanel {

    private Image world;

    public Map(){
    initImageWorld();
    }

    private void initImageWorld() {

        loadImageWorld();

        int w = world.getWidth(this);
        int h =  world.getHeight(this);
        setPreferredSize(new Dimension(w, h));
    }

    private void loadImageWorld() {

        ImageIcon w = new ImageIcon(new ImageIcon("interface/src/ressources/monde.jpg").getImage().getScaledInstance(1280,750,Image.SCALE_DEFAULT));
        world = w.getImage();

    }

    public void paintComponent(Graphics g) {

        g.drawImage(world, 0, 0, null);

    }
}
