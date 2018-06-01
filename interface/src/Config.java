import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Config extends JPanel {

    //Image de fond
    private Image background;

    //Boutons JOUER et nombre de joueurs
    private JButton emptyButton = new JButton();
    private JButton rules = new JButton("Les règles du jeu");
    private JButton play = new JButton("JOUER");
    private JButton[] buttonsPlay = {rules,play};
    private JButton solo = new JButton("1 joueur (contre l'IA)");
    private JButton duo = new JButton("2 joueurs");
    private JButton trio = new JButton("3 joueurs");
    private JButton quator = new JButton("4 joueurs");
    private JButton quintet = new JButton("5 joueurs");
    private JButton sextuor = new JButton("6 joueurs");
    private JButton[] buttonsNumberPlayers = {solo, duo, trio, quator, quintet, sextuor};
    private JButton ok = new JButton("J'ai compris");

    //Les règles
    private String tabulation = "&nbsp;&nbsp;&nbsp;&nbsp;";
    private JLabel emptyLabel = new JLabel("<html></html>");
    private JLabel titleRules = new JLabel("<html>" + tabulation + "Les règles du jeu</html>");
    private JLabel objectif = new JLabel("<html>" + tabulation + "L'objectif du jeu est d'accomplir la mission secrète qui est attribuée au joueur ou de "+tabulation+"conquérir la planète.</html>");
    private JLabel territory = new JLabel("<html>" + tabulation + "Les territoires sont distribués aléatoirement entre les joueurs.</html>");
    private JLabel army = new JLabel("<html>" + tabulation + "Chaque joueur reçoit ses armées.</html>");
    private JLabel position = new JLabel( "<html>" + tabulation + "Les joueurs placent chacun leur tour leurs armées sur leurs territoires. Un minimum " + tabulation + "d'une armée doit être placée sur chaque territoire.<br></html>");
    private JLabel lap = new JLabel("<html>" + tabulation + "Chaque tour, les joueurs reçoivent des renforts. Ils doivent ensuite choisir de se " + tabulation + "déplacer et d'attaquer.");
    private JLabel allRules[] = {titleRules, emptyLabel, objectif, territory, army, position, lap, emptyLabel};


    public Config() {

        initBoardClear();
        menu();

    }

    private void menu(){

        Font font = new Font("Times Roman", Font.BOLD | Font.ITALIC, 50);

        for(int i = 0 ; i < 2 ; i++){
            add(buttonsPlay[i]);
            buttonsPlay[i].setBackground(new Color(0,0,0,0));
            buttonsPlay[i].setContentAreaFilled(false);
            buttonsPlay[i].setBorderPainted(false);
            buttonsPlay[i].setFont(font);
        }

        rules.setForeground(Color.WHITE);

        //Faire clignoter le bouton JOUER
        int delay = 1000;
        Color gris = new Color(158,158,158);
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (play.getForeground() == Color.WHITE){
                    play.setForeground(gris);
                }
                else {
                    play.setForeground(Color.WHITE);
                }
                repaint();
            }
        };
        Timer t1 = new Timer(delay, taskPerformer);
        t1.start();

        setLayout(new GridLayout(2,0));
        setVisible(true);

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(rules);
                remove(play);
                repaint();

                initBoardOOF();
                rules();
            }
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(rules);
                remove(play);
                repaint();

                initBoardOOF();
                numberPlayers();
            }
        });

    }

    public void numberPlayers(){

        Font font = new Font("Times Roman", Font.BOLD | Font.ITALIC, 35);
        JPanel selectNbPlayers = new JPanel();

        for(int i = 0 ; i < 6 ; i++){
            add(selectNbPlayers.add(buttonsNumberPlayers[i]));
            buttonsNumberPlayers[i].setBackground(new Color(0,0,0,0));
            buttonsNumberPlayers[i].setContentAreaFilled(false);
            buttonsNumberPlayers[i].setBorderPainted(false);
            buttonsNumberPlayers[i].setForeground(Color.WHITE);
            buttonsNumberPlayers[i].setFont(font);
        }
        this.setVisible(true);

        addTagName();

    }

    private void addTagName(){

        for(int i = 0 ; i < 6 ; i++){
            buttonsNumberPlayers[i].addActionListener(e -> {
                if (e.getActionCommand().equals("1 joueur (contre l'IA)")){
                    PlayerNames player = new PlayerNames(1);
                    player.getPlayerTagArea();
                }
                if (e.getActionCommand().equals("2 joueurs")){
                    PlayerNames player = new PlayerNames(2);
                    player.getPlayerTagArea();
                    Missions mission = new Missions();
                    mission.showMissions(player.getAllPlayers());
                }
                if (e.getActionCommand().equals("3 joueurs")){
                    PlayerNames player = new PlayerNames(3);
                    player.getPlayerTagArea();
                }
                if (e.getActionCommand().equals("4 joueurs")){
                    PlayerNames player = new PlayerNames(4);
                    player.getPlayerTagArea();
                }
                if (e.getActionCommand().equals("5 joueurs")){
                    PlayerNames player = new PlayerNames(5);
                    player.getPlayerTagArea();
                }
                if (e.getActionCommand().equals("6 joueurs")){
                    PlayerNames player = new PlayerNames(6);
                    player.getPlayerTagArea();
                }
            });
        }
    }

    private void rules(){

        Font font = new Font("Times Roman", Font.BOLD | Font.ITALIC, 30);

        for (int i = 0; i < 7; i++){
            add(allRules[i]);
            allRules[i].setForeground(Color.WHITE);
            allRules[i].setFont(font);
        }
        titleRules.setFont(new Font("Times Roman", Font.BOLD | Font.ITALIC, 50));

        setLayout(null);
        setLayout(new GridLayout(10,1));

        add(emptyButton);
        emptyButton.setBackground(new Color(0,0,0,0));
        emptyButton.setContentAreaFilled(false);
        emptyButton.setBorderPainted(false);
        add(ok);
        ok.setBackground(new Color(0,0,0,0));
        ok.setContentAreaFilled(false);
        ok.setBorderPainted(false);
        ok.setFont(font);
        int delay = 1000;
        Color gris = new Color(158,158,158);
        ActionListener taskPerformer = new ActionListener() {
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
        Timer t1 = new Timer(delay, taskPerformer);
        t1.start();

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(ok);
                remove(emptyButton);
                remove(titleRules);
                for (int i = 0; i < 7; i++){
                    remove(allRules[i]);
                }
                repaint();

                initBoardClear();
                menu();
            }
        });

    }

    public void initBoardClear(){

        ImageIcon bg = new ImageIcon("interface/src/ressources/risk.jpg");
        background = bg.getImage();
        int w = background.getWidth(this);
        int h =  background.getHeight(this);
        setPreferredSize(new Dimension(w, h));
    }

    public void initBoardOOF(){

        ImageIcon bg = new ImageIcon("interface/src/ressources/risk_flou.jpeg");
        background = bg.getImage();
        int w = background.getWidth(this);
        int h =  background.getHeight(this);
        setPreferredSize(new Dimension(w, h));
    }

    public void initWorld(){

        ImageIcon world = new ImageIcon(new ImageIcon("interface/src/ressources/world.jpg").getImage().getScaledInstance(1280,715,Image.SCALE_DEFAULT));
        background = world.getImage();
        int w = background.getWidth(this);
        int h = background.getHeight(this);
        setPreferredSize(new Dimension(w, h));
    }

    public void paintComponent(Graphics g) {

        g.drawImage(background, 0, 0, null);

    }
}