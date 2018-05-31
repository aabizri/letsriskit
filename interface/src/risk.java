import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

public class risk extends JFrame {


    //Le menu
    private JFrame f = new JFrame();
    private JPanel menu = new JPanel();
    private JPanel map = new JPanel();
    public risk(){


        initUI();
    }




    private void initUI() {

        f.getContentPane().add(menu);
        menu.add(new Board());
        menu.setVisible(true);

        f.getContentPane().add(map);
        add(menu);
        pack();


        setTitle("RISK");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(1280, 750);
        setResizable(false);
        Map m = new Map();
        menu.add(m);
        Collection<Territory>territories = m.initWorldTerritories();
        territories.forEach(t -> t.addToPanel(menu));
        menu.setLayout(null);
       }





    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            risk game = new risk();
            game.setVisible(true);
        }
        );


    }


}
