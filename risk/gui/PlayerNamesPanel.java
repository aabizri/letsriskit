package gui;

import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerNamesPanel extends Panel {
    private RiskFrame parentFrame;

    private JButton back = new JButton("<");
    private JButton next = new JButton(">");
    private Font font = new Font("Times Roman", Font.BOLD, 30);
    private String tabulation = "&nbsp;&nbsp;&nbsp;&nbsp;";
    private ArrayList<Player> allPlayers = new ArrayList<>();

    //Tous les JTextArea
    private JLabel empty = new JLabel();
    private JLabel empty2 = new JLabel();
    private JTextField name1 = new JTextField();
    private JTextField name2 = new JTextField();
    private JTextField name3 = new JTextField();
    private JTextField name4 = new JTextField();
    private JTextField name5 = new JTextField();
    private JTextField name6 = new JTextField();
    private JTextField[] names = {name1, name2, name3, name4, name5, name6};
    private String name;

    //Checkboxes
    private JCheckBox IACheckbox1 = new JCheckBox("  Ordinateur");
    private JCheckBox IACheckbox2 = new JCheckBox("  Ordinateur");
    private JCheckBox IACheckbox3 = new JCheckBox("  Ordinateur");
    private JCheckBox IACheckbox4 = new JCheckBox("  Ordinateur");
    private JCheckBox IACheckbox5 = new JCheckBox("  Ordinateur");
    private JCheckBox IACheckbox6 = new JCheckBox("  Ordinateur");
    private JCheckBox[] IACheckbox = {IACheckbox1, IACheckbox2, IACheckbox3, IACheckbox4, IACheckbox5, IACheckbox6};
    private Boolean IA;

    //couleurs
    String bleu = "Bleu";
    String jaune = "Jaune";
    String rouge = "Rouge";
    String vert = "Vert";
    String rose = "Rose";
    String noir = "Noir";
    String[] couleurs = {bleu, jaune, rouge, vert, rose, noir};
    JComboBox combo1 = new JComboBox(couleurs);
    JComboBox combo2 = new JComboBox(couleurs);
    JComboBox combo3 = new JComboBox(couleurs);
    JComboBox combo4 = new JComboBox(couleurs);
    JComboBox combo5 = new JComboBox(couleurs);
    JComboBox combo6 = new JComboBox(couleurs);
    JComboBox[] allCombos = {combo1, combo2, combo3, combo4, combo5, combo6};


    public PlayerNamesPanel(RiskFrame parentFrame, int numberOfPlayers) {


        for (int i = 0; i < numberOfPlayers; i++) {

            JLabel playerNameTitle = new JLabel("Nom du joueur " + (i + 1));
            playerNameTitle.setHorizontalAlignment(SwingConstants.CENTER);

            playerNameTitle.setVisible(true);
            playerNameTitle.setBounds(20, -10, 400, 100);
            playerNameTitle.setFont(font);
            playerNameTitle.setForeground(Color.BLACK);

            IACheckbox[i].setHorizontalAlignment(SwingConstants.CENTER);
            IACheckbox[i].setFont(font);
            IACheckbox[i].setPreferredSize(new Dimension(100,100));
            IACheckbox[i].setForeground(Color.BLACK);

            allCombos[i].setFont(font);

            this.add(playerNameTitle);
            this.add(IACheckbox[i]);
            this.add(allCombos[i]);

            names[i].setFont(font);
            names[i].setHorizontalAlignment(SwingConstants.CENTER);
            this.add(names[i]);

            this.setBackground(Color.WHITE);
        }

        back.setVisible(true);
        back.setFont(font);
        this.add(back);

        this.add(empty);
        this.add(empty2);

        next.setVisible(true);
        next.setFont(font);
        this.add(next);

        back.addActionListener(e -> {
            parentFrame.setCurrentPanel(new PlayerQuantityChoicePanel(parentFrame));
        });

        next.addActionListener(e -> {
            for (int i = 0; i < numberOfPlayers; i++) {
                if (IACheckbox[i].isSelected()){
                    IA = true;
                } IA = false;

                if (names[i].getText().isEmpty()){
                    name = names[i].toString();
                } name = names[i].getText();


                //TODO Si 2 joueurs ont la même couleur alors afficher un message d'erreur
                /**for (int j = 0; j < couleurs.length; j++){
                    for (int k = 0; k < couleurs.length; k++) {
                        if (allCombos[j].getSelectedItem() == allCombos[k].getSelectedItem()) {
                            JFrame error = new JFrame();
                            JLabel errorSentence = new JLabel("Deux joueurs ont la même couleur");
                            JButton ok = new JButton("Compris");
                            error.setTitle("Erreur");
                            error.setLocationRelativeTo(null);
                            error.add(errorSentence);
                            error.add(ok);
                            error.setLayout(new GridLayout(2,1));
                            error.setVisible(true);
                            ok.addActionListener(e1 ->{
                                error.setVisible(false);
                            });
                        }
                    }
                }**/

                allPlayers.add(new Player(name, IA, allCombos[i].getSelectedItem().toString()));
            }
            parentFrame.setCurrentPanel(new MapPanel(parentFrame,allPlayers));
        });

        this.setLayout(new GridLayout(0, 4));
    }

    public ArrayList getAllPlayers(){
        return allPlayers;
    }
}
