import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class ConfigPanel extends Panel {
    RiskFrame parentFrame;

    private static final String backgroundFilename = "interface/src/ressources/risk.jpg";

    //Boutons JOUER & règles
    private JButton rulesButton = new JButton("Les règles du jeu");
    private JButton playButton = new JButton("JOUER");
    private JButton[] buttonsPlay = {rulesButton, playButton};

    public ConfigPanel(RiskFrame parent) {
        this.parentFrame = parent;
        initBoardClear();
        initMenu();
    }

    private void initMenu(){

        Font font = new Font("Times Roman", Font.BOLD | Font.ITALIC, 50);

        for(int i = 0 ; i < 2 ; i++){
            add(buttonsPlay[i]);
            buttonsPlay[i].setBackground(new Color(0,0,0,0));
            buttonsPlay[i].setContentAreaFilled(false);
            buttonsPlay[i].setBorderPainted(false);
            buttonsPlay[i].setFont(font);
        }

        rulesButton.setForeground(Color.WHITE);

        //Faire clignoter le bouton JOUER
        int delay = 1000;
        Color gris = new Color(158,158,158);
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (playButton.getForeground() == Color.WHITE){
                    playButton.setForeground(gris);
                }
                else {
                    playButton.setForeground(Color.WHITE);
                }
                repaint();
            }
        };
        Timer t1 = new Timer(delay, taskPerformer);
        t1.start();

        setLayout(new GridLayout(2,0));
        setVisible(true);

        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();;
                parentFrame.setCurrentPanel(new RulesPanel(parentFrame));
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                parentFrame.setCurrentPanel(new PlayerQuantityChoicePanel(parentFrame));
            }
        });

    }

    public void initBoardClear(){
        this.setBackgroundFile(backgroundFilename);
    }
}