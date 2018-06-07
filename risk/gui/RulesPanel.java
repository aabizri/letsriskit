package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RulesPanel extends Panel {
    // Boutons
    private final JButton emptyButton = new JButton();
    private final JButton ok = new JButton("J'ai compris");

    //Les règles
    private final String tabulation = "&nbsp;&nbsp;&nbsp;&nbsp;";
    private final JLabel emptyLabel = new JLabel("<html></html>");
    private final JLabel titleRules = new JLabel("<html>" + tabulation + "Les règles du jeu</html>");
    private final JLabel objectif = new JLabel("<html>" + tabulation + "L'objectif du jeu est d'accomplir la mission secrète qui est attribuée au joueur ou de "+tabulation+"conquérir la planète.</html>");
    private final JLabel territory = new JLabel("<html>" + tabulation + "Les territoires sont distribués aléatoirement entre les joueurs.</html>");
    private final JLabel army = new JLabel("<html>" + tabulation + "Chaque joueur reçoit ses armées.</html>");
    private final JLabel position = new JLabel( "<html>" + tabulation + "Les joueurs placent chacun leur tour leurs armées sur leurs territoires. Un minimum " + tabulation + "d'une armée doit être placée sur chaque territoire.<br></html>");
    private final JLabel lap = new JLabel("<html>" + tabulation + "Chaque tour, les joueurs reçoivent des renforts. Ils doivent ensuite choisir de se " + tabulation + "déplacer et d'attaquer.");
    private final JLabel allRules[] = {titleRules, emptyLabel, objectif, territory, army, position, lap, emptyLabel};

    public RulesPanel(RiskFrame parentFrame){

        this.initBoardOOF();

        final Font font = new Font("Times Roman", Font.BOLD | Font.ITALIC, 30);

        for (int i = 0; i < 7; i++){
            this.add(allRules[i]);
            allRules[i].setForeground(Color.WHITE);
            allRules[i].setFont(font);
        }

        titleRules.setFont(new Font("Times Roman", Font.BOLD | Font.ITALIC, 50));

        this.setLayout(null);
        this.setLayout(new GridLayout(10,1));

        this.add(emptyButton);
        this.emptyButton.setBackground(new Color(0,0,0,0));
        this.emptyButton.setContentAreaFilled(false);
        this.emptyButton.setBorderPainted(false);
        this.add(ok);
        this.ok.setBackground(new Color(0,0,0,0));
        this.ok.setContentAreaFilled(false);
        this.ok.setBorderPainted(false);
        this.ok.setFont(font);

        final int delay = 1000;
        final Color gris = new Color(158,158,158);
        final ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ok.getForeground() == Color.WHITE){
                    ok.setForeground(gris);
                }
                else {
                    ok.setForeground(Color.WHITE);
                }
                repaint();
            }
        };
        final Timer timer = new Timer(delay, taskPerformer);
        timer.start();

        this.ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.setCurrentPanel(new ConfigPanel(parentFrame));
            }
        });

    }

    public void initBoardOOF(){
        this.setBackgroundFile("interface/src/ressources/risk_flou.jpeg");
    }
}
