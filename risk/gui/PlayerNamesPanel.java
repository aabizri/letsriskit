package gui;

import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerNamesPanel extends Panel {
    private RiskFrame parentFrame;

    private Color finalColor;
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
    String white = "Blanc";
    String noir = "Noir";
    String[] couleurs = {bleu, jaune, rouge, vert, white, noir};
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
                }
                else {
                    IA = false;
                }

                if (names[i].getText().isEmpty()){
                    JOptionPane.showMessageDialog(this,"Chaque joueur doit avoir un nom","Erreur de nom",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for (int j = 0; j < numberOfPlayers-1; j++){
                    for (int k = j+1; k < numberOfPlayers-1; k++) {
                        if (allCombos[j].getSelectedItem() == allCombos[k].getSelectedItem()) {
                            JOptionPane.showMessageDialog(this,"Chaque joueur doit avoir une couleur différente","Erreur de couleur",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (names[j].equals(names[k])){
                            JOptionPane.showMessageDialog(this,"Le même nom ne peut pas être utilisé plusieurs fois","Erreur de nom",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
                name = names[i].getText();
                if (allCombos[i].getSelectedItem().toString().equals("Bleu")){
                    finalColor = Color.BLUE;
                }
                if (allCombos[i].getSelectedItem().toString().equals("Jaune")){
                    finalColor = Color.YELLOW;
                }
                if (allCombos[i].getSelectedItem().toString().equals("Rouge")){
                    finalColor = Color.RED;
                }
                if (allCombos[i].getSelectedItem().toString().equals("Vert")){
                    finalColor = Color.GREEN;
                }
                if (allCombos[i].getSelectedItem().toString().equals("Blanc")){
                    finalColor = Color.WHITE;
                }
                if (allCombos[i].getSelectedItem().toString().equals("Noir")){
                    finalColor = Color.black;
                }

                allPlayers.add(new Player(name, IA, finalColor ));
            }
            parentFrame.setCurrentPanel(new MapPanel(parentFrame,allPlayers));
        });

        this.setLayout(new GridLayout(0, 4));
    }

    public ArrayList getAllPlayers(){
        return allPlayers;
    }
}
