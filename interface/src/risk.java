import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

public class risk extends JFrame {

    private JPanel menu = new JPanel();

    public risk(){

        initUI();
    }

    private void initUI() {

        menu.add(new Config());
        menu.setVisible(true);
        add(menu);
        pack();

        setTitle("RISK");
        setLocationRelativeTo(null);
        setSize(1280, 750);
        setResizable(false);

    }

       }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            risk game = new risk();
            game.setVisible(true);
        });
    }
}
