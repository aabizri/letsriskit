import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class Board extends JPanel {

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

    public Board() {
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

        this.setLayout(new GridLayout(2,0));
        this.setVisible(true);

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

    private void numberPlayers(){

        Font font = new Font("Times Roman", Font.BOLD | Font.ITALIC, 35);
        int players;

        for(int i = 0 ; i < 6 ; i++){
            add(buttonsNumberPlayers[i]);
            buttonsNumberPlayers[i].setBackground(new Color(0,0,0,0));
            buttonsNumberPlayers[i].setContentAreaFilled(false);
            buttonsNumberPlayers[i].setBorderPainted(false);
            buttonsNumberPlayers[i].setForeground(Color.WHITE);
            buttonsNumberPlayers[i].setFont(font);
            buttonsNumberPlayers[i].addActionListener(e -> {

                removeAll();

                if (e.getActionCommand().equals("2 joueurs")){
                    Player player = new Player();
                    add(player.addPlayerName(2));
                    player.addPlayerName(2).setVisible(true);
                }

            });
        }

        this.setLayout(new GridLayout(6,0));
        this.setVisible(true);

    }

    private void rules(){

        Font font = new Font("Times Roman", Font.BOLD | Font.ITALIC, 30);

        for (int i = 0; i < 7; i++){
            add(allRules[i]);
            allRules[i].setForeground(Color.WHITE);
            allRules[i].setFont(font);
        }
        titleRules.setFont(new Font("Times Roman", Font.BOLD | Font.ITALIC, 50));

        this.setLayout(new GridLayout(10,0));

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

    private void initBoardClear(){

        loadImageClear();

        int w = background.getWidth(this);
        int h =  background.getHeight(this);
        setPreferredSize(new Dimension(w, h));
    }

    private void loadImageClear() {

        ImageIcon bg = new ImageIcon("interface/src/ressources/risk.jpg");
        background = bg.getImage();

    }

    private void initBoardOOF(){

        loadImageOOF();

        int w = background.getWidth(this);
        int h =  background.getHeight(this);
        setPreferredSize(new Dimension(w, h));
    }

    private void loadImageOOF() {

        ImageIcon bg = new ImageIcon("interface/src/ressources/risk_flou.jpeg");
        background = bg.getImage();

    }

    private void initWorld(){
        loadWorld();

        int w = background.getWidth(this);
        int h = background.getHeight(this);
        setPreferredSize(new Dimension(w, h));
    }

    private void loadWorld(){

        ImageIcon w = new ImageIcon(new ImageIcon("interface/src/ressources/world.jpg").getImage().getScaledInstance(1280,715,Image.SCALE_DEFAULT));
        background = w.getImage();
    }

    public void paintComponent(Graphics g) {

        g.drawImage(background, 0, 0, null);

    }
}