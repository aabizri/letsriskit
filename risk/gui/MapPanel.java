import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import javax.swing.JPanel;

import javax.swing.*;

public class MapPanel extends Panel {

    private List<Player> players;
    private Font font = new Font("Times Roman", Font.BOLD, 15);
    private Font font2 = new Font("Times Roman", Font.BOLD, 30);
    final Collection<Territory> territories = new ArrayList<>(42);
    private final String tabulation = "&nbsp;&nbsp;&nbsp;&nbsp;";
    int i = 0;

    public MapPanel(){

    }

    private Collection<Territory> getTerritories(){
        territories.add(new Territory("Eastern Australia",1088,629));
        territories.add(new Territory("Western Australia",986,642));
        territories.add(new Territory("New Guinea",1038,505));
        territories.add(new Territory("Indonesia",938,525));
        territories.add(new Territory("Japan",1043,255));
        territories.add(new Territory("Siam",929,412));
        territories.add(new Territory("Kamchatka",1019,92));
        territories.add(new Territory("India",827,382));
        territories.add(new Territory("China",898,318));
        territories.add(new Territory("Mongolia",932,247));
        territories.add(new Territory("Irkutsk",919,178));
        territories.add(new Territory("Yakutsk",927,85));
        territories.add(new Territory("Siberia",848,121));
        territories.add(new Territory("Ural",777,170));
        territories.add(new Territory("Afghanistan",757,273));
        territories.add(new Territory("Middle East",683, 381));
        territories.add(new Territory("Ukraine",657, 194));
        territories.add(new Territory("Scandinavia",564, 130));
        territories.add(new Territory("Northern Europe",554, 244));
        territories.add(new Territory("Southern Europe",568, 308));
        territories.add(new Territory("Western Europe",475, 325));
        territories.add(new Territory("Iceland",471, 142));
        territories.add(new Territory("Great Britain",450, 224));
        territories.add(new Territory("Eastern Africa",667, 509));
        territories.add(new Territory("Madagascar",716, 645));
        territories.add(new Territory("South Africa",608, 633));
        territories.add(new Territory("Congo",601, 549));
        territories.add(new Territory("Egypt",606, 423));
        territories.add(new Territory("Northern Africa",516, 456));
        territories.add(new Territory("Argentina",277, 603));
        territories.add(new Territory("Brazil",334, 478));
        territories.add(new Territory("Peru",231, 494));
        territories.add(new Territory("Venezuela",251, 408));
        territories.add(new Territory("Greenland",384, 64));
        territories.add(new Territory("Quebec",306, 184));
        territories.add(new Territory("Ontario",231, 176));
        territories.add(new Territory("Eastern United States",240, 275));
        territories.add(new Territory("Central America",174, 350));
        territories.add(new Territory("Western United States",155, 253));
        territories.add(new Territory("North West Territory",173, 112));
        territories.add(new Territory("Alberta",159, 168));
        territories.add(new Territory("Alaska",58, 112));
        return territories;
    }

    public void showRound(){

        JPanel roundInformations = new JPanel();

        JButton endOfRound = new JButton("TERMINER");
        endOfRound.setFont(font);
        endOfRound.setBounds(1100,460,160,80);
        this.add(endOfRound);

        JLabel roundLabel = new JLabel();
        roundLabel.setFont(font2);
        roundLabel.setForeground(Color.WHITE);
        roundInformations.setVisible(false);
        roundInformations.setBackground(new Color(0,0,0,0));
        roundInformations.setBounds(980,0,300,100);
        roundInformations.add(roundLabel);
        this.add(roundInformations);


        //Action : Fin du tour
        //TODO : ajouter la fin du tour
        endOfRound.addActionListener(e -> {

            this.i++;
            roundLabel.setText("TOUR NUMERO " + String.valueOf(this.i));
            roundInformations.add(roundLabel);
            roundInformations.setVisible(true);
        });

    }

    private void playingButtons(){

        //Playing Buttons
        JButton attack = new JButton("ATTAQUER");
        JButton move = new JButton("DEPLACER");
        JLabel empty = new JLabel();
        attack.setFont(font);
        move.setFont(font);

        //Buttons Panel
        JPanel playingPanel = new JPanel();
        playingPanel.setVisible(true);
        playingPanel.add(attack);
        playingPanel.add(empty);
        playingPanel.add(move);
        playingPanel.setLayout(new GridLayout(5,1));
        playingPanel.setBackground(new Color(0,0,0,0));
        playingPanel.setBounds(1100,140,160,400);

        //Attacking Panel
        JPanel attackPanel = new JPanel();

        String[] allCountriesAttacking = {
                "Pays attaquant","Afghanistan","Alaska","Alberta","Argentina","Brazil","Central America","China",
                "Congo","Eastern Africa","Eastern Australia","Eastern United States","Egypt","Great Britain","Greenland","Iceland","Indonesia",
                "Irkutsk","Japan","Kamchatka", "India","Madagascar", "South Africa","Middle East","Mongolia","New Guinea","North West Territory",
                "Northern Africa","Peru","Quebec", "Ontario","Scandinavia", "Northern Europe", "Southern Europe","Siam","Siberia","Ukraine","Ural",
                "Venezuela","Western Australia","Western Europe","Western United States","Yakutsk"
        };
        String[] allCountriesAttacked = {
                "Pays attaqué","Afghanistan","Alaska","Alberta","Argentina","Brazil","Central America","China",
                "Congo","Eastern Africa","Eastern Australia","Eastern United States","Egypt","Great Britain","Greenland","Iceland","Indonesia",
                "Irkutsk","Japan","Kamchatka", "India","Madagascar", "South Africa","Middle East","Mongolia","New Guinea","North West Territory",
                "Northern Africa","Peru","Quebec", "Ontario","Scandinavia", "Northern Europe", "Southern Europe","Siam","Siberia","Ukraine","Ural",
                "Venezuela","Western Australia","Western Europe","Western United States","Yakutsk"
        };
        JComboBox attackingTerritory = new JComboBox(allCountriesAttacking);
        JComboBox attackedTerritory = new JComboBox(allCountriesAttacked);
        JButton validateAttack = new JButton("ATTAQUER");
        JButton cancelAttack = new JButton("ANNULER");
        Integer[] soldats = {0,1,2,3};
        Integer[] canons = {0,1,2,3};
        Integer[] cavaliers = {0,1,2,3};
        JLabel soldatsTitle = new JLabel("Soldats");
        JLabel canonsTitle = new JLabel("Canons");
        JLabel cavaliersTitle = new JLabel("Cavaliers");
        soldatsTitle.setHorizontalAlignment(SwingConstants.CENTER);
        soldatsTitle.setFont(font);
        canonsTitle.setHorizontalAlignment(SwingConstants.CENTER);
        canonsTitle.setFont(font);
        cavaliersTitle.setHorizontalAlignment(SwingConstants.CENTER);
        cavaliersTitle.setFont(font);
        JComboBox soldatAttack = new JComboBox(soldats);
        JComboBox canonAttack = new JComboBox(canons);
        JComboBox cavalierAttack = new JComboBox(cavaliers);
        attackPanel.setBounds(1100,100,160,350);
        attackPanel.setBackground(Color.WHITE);
        attackPanel.setVisible(false);
        attackPanel.add(attackingTerritory);
        attackPanel.add(attackedTerritory);
        attackPanel.add(soldatsTitle);
        attackPanel.add(soldatAttack);
        attackPanel.add(canonsTitle);
        attackPanel.add(canonAttack);
        attackPanel.add(cavaliersTitle);
        attackPanel.add(cavalierAttack);
        attackPanel.add(validateAttack);
        attackPanel.add(cancelAttack);
        attackPanel.setLayout(new GridLayout(10,1));

        cancelAttack.addActionListener(e -> {
            attackPanel.setVisible(false);
            playingPanel.setVisible(true);
        });

        validateAttack.addActionListener(e -> {
            int quantitySoldats = (int)soldatAttack.getSelectedItem();
            int quantityCanons = (int)canonAttack.getSelectedItem();
            int quantityCavaliers = (int)cavalierAttack.getSelectedItem();
            System.out.println(quantityCanons);
            System.out.println(quantitySoldats);
            System.out.println(quantityCavaliers);
            if (quantitySoldats + quantityCanons + quantityCavaliers > 3){
                JOptionPane.showMessageDialog(attackPanel, "Vous pouvez attaquer avec au maximum 3 unités","Erreur pendant l'attaque",JOptionPane.ERROR_MESSAGE);
            }
            if (quantityCanons + quantityCavaliers + quantitySoldats == 0){
                JOptionPane.showMessageDialog(attackPanel, "Impossible d'attaquer avec 0 unités","Erreur pendant l'attaque",JOptionPane.ERROR_MESSAGE);
            }
        });

        this.add(playingPanel);
        this.add(attackPanel);

        //Moving Panel
        JPanel movePanel = new JPanel();

        String[] firstCountry = {
                "Territoire de départ","Afghanistan","Alaska","Alberta","Argentina","Brazil","Central America","China",
                "Congo","Eastern Africa","Eastern Australia","Eastern United States","Egypt","Great Britain","Greenland","Iceland","Indonesia",
                "Irkutsk","Japan","Kamchatka", "India","Madagascar", "South Africa","Middle East","Mongolia","New Guinea","North West Territory",
                "Northern Africa","Peru","Quebec", "Ontario","Scandinavia", "Northern Europe", "Southern Europe","Siam","Siberia","Ukraine","Ural",
                "Venezuela","Western Australia","Western Europe","Western United States","Yakutsk"
        };
        String[] secondCountry = {
                "Territoire d'arrivé","Afghanistan","Alaska","Alberta","Argentina","Brazil","Central America","China",
                "Congo","Eastern Africa","Eastern Australia","Eastern United States","Egypt","Great Britain","Greenland","Iceland","Indonesia",
                "Irkutsk","Japan","Kamchatka", "India","Madagascar", "South Africa","Middle East","Mongolia","New Guinea","North West Territory",
                "Northern Africa","Peru","Quebec", "Ontario","Scandinavia", "Northern Europe", "Southern Europe","Siam","Siberia","Ukraine","Ural",
                "Venezuela","Western Australia","Western Europe","Western United States","Yakutsk"
        };
        JComboBox firstTerritory = new JComboBox(firstCountry);
        JComboBox secondTerritory = new JComboBox(secondCountry);
        JButton validateMove = new JButton("DEPLACER");
        JButton cancelMove = new JButton("ANNULER");
        JLabel soldatsMovement = new JLabel("Soldats");
        JLabel canonsMovement = new JLabel("Canons");
        JLabel cavaliersMovement = new JLabel("Cavaliers");
        soldatsMovement.setFont(font);
        soldatsMovement.setHorizontalAlignment(SwingConstants.CENTER);
        canonsMovement.setFont(font);
        canonsMovement.setHorizontalAlignment(SwingConstants.CENTER);
        cavaliersMovement.setFont(font);
        cavaliersMovement.setHorizontalAlignment(SwingConstants.CENTER);
        JComboBox soldatQuantity = new JComboBox();
        JComboBox canonQuantity = new JComboBox();
        JComboBox cavalierQuantity = new JComboBox();
        movePanel.setBackground(Color.WHITE);
        movePanel.setBounds(1100,100,160,350);
        movePanel.setVisible(false);
        movePanel.add(firstTerritory);
        movePanel.add(secondTerritory);
        movePanel.add(soldatsMovement);
        movePanel.add(soldatQuantity);
        movePanel.add(canonsMovement);
        movePanel.add(canonQuantity);
        movePanel.add(cavaliersMovement);
        movePanel.add(cavalierQuantity);
        movePanel.add(validateMove);
        movePanel.add(cancelMove);
        movePanel.setLayout(new GridLayout(10,1));

        cancelMove.addActionListener(e -> {
            movePanel.setVisible(false);
            playingPanel.setVisible(true);
        });

        //Ajouter les panels à la frame
        this.add(playingPanel);
        this.add(attackPanel);
        this.add(movePanel);

        //Action : quand le bouton ATTAQUER est cliqué
        attack.addActionListener(e -> {
            attackPanel.setVisible(true);
            playingPanel.setVisible(false);
        });

        //Action : quand le bouton DEPLACER est cliqué
        move.addActionListener(e -> {
            movePanel.setVisible(true);
            playingPanel.setVisible(false);
        });
    }

    private void victory(){

        JPanel victory = new JPanel();
        JButton backToMenu = new JButton("RETOUR AU MENU");
        backToMenu.setFont(font);
        JLabel winner = new JLabel("<html>"+tabulation+tabulation+tabulation+"Félicitations PLAYER'S NAME, tu as gagné !</html>");
        winner.setFont(font);
        victory.setBackground(Color.WHITE);
        victory.setBounds(450,250,400,200);
        victory.add(winner);
        victory.add(backToMenu);
        victory.setVisible(false);
        victory.setLayout(new GridLayout(2,1));
        this.add(victory);

        backToMenu.addActionListener(e -> {
            //TODO retour au menu
        });

        //
        /**if (victory){
         removeAll();
         victory.setVisible(true);
        }**/
    }

    private void initWorldTerritories() {
        getTerritories().forEach(t -> t.addToPanel(this));
    }

    public MapPanel(RiskFrame currentFrame, List<Player> players) {
        this.players = players;

        this.initBackgroundImage();

        this.initWorldTerritories();

        this.playingButtons();

        this.showRound();

        this.setLayout(null);

        this.victory();
    }

    public Image getBackgroundImage() {
        return (new ImageIcon("interface/src/ressources/world.jpg")).getImage().getScaledInstance(1280, 715, Image.SCALE_DEFAULT);
    }

    private void initBackgroundImage() {
        this.setBackgroundImage(this.getBackgroundImage());
    }
}
