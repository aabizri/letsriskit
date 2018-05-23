import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class risk extends JFrame {


    public risk(){

        initUI();
    }

    private void initUI() {

        add(new Board());

        pack();

        setTitle("RISK");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(1280, 750);
        setResizable(false);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            risk game = new risk();
            game.setVisible(true);
        }
        );
    }

    private int x;
    private int y;

    public void init() {
        addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e){
        x = e.getX();
        y = e.getY();
        System.out.println("("+ x + ";" + y + ")");
    }
}
