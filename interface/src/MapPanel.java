import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import javax.swing.JPanel;

import javax.swing.*;

public class MapPanel extends Panel {

    private List<Player> players;
    private Font font = new Font("Times Roman", Font.BOLD, 15);
    final Collection<Territory> territories = new ArrayList<>(42);

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

    private void playingButtons(){

        //Playing Buttons
        JButton attack = new JButton("ATTAQUER");
        JButton move = new JButton("DEPLACER");
        JPanel playingPanel = new JPanel();
        JLabel empty = new JLabel();
        attack.setVisible(true);
        attack.setFont(font);
        move.setVisible(true);
        move.setFont(font);
        playingPanel.setVisible(true);
        playingPanel.add(attack);
        playingPanel.add(empty);
        playingPanel.add(move);
        playingPanel.setLayout(new GridLayout(3,1));
        playingPanel.setBackground(new Color(0,0,0,0));
        playingPanel.setBounds(1100,250,160,200);

        JPanel attackPanel = new JPanel();
        for(int i = 0; i < 42; i++){

        }

        String[] allCountriesAttacking = {"Pays attaquant","Eastern Australia","Western Australia","New Guinea","Indonesia","Japan","Siam","Kamchatka",
                "India","China","Mongolia","Irkutsk","Yakutsk","Siberia","Ural","Afghanistan","Middle East","Ukraine","Scandinavia",
                "Northern Europe", "Southern Europe","Western Europe","Iceland","Great Britain","Eastern Africa","Madagascar",
                "South Africa","Congo","Egypt","Northern Africa","Argentina","Brazil","Peru","Venezuela","Greenland","Quebec",
                "Ontario","Eastern United States","Central America","Western United States","North West Territory","Alberta","Alaska"
        };
        String[] allCountriesAttacked = {"Pays attaqué","Eastern Australia","Western Australia","New Guinea","Indonesia","Japan","Siam","Kamchatka",
                "India","China","Mongolia","Irkutsk","Yakutsk","Siberia","Ural","Afghanistan","Middle East","Ukraine","Scandinavia",
                "Northern Europe", "Southern Europe","Western Europe","Iceland","Great Britain","Eastern Africa","Madagascar",
                "South Africa","Congo","Egypt","Northern Africa","Argentina","Brazil","Peru","Venezuela","Greenland","Quebec",
                "Ontario","Eastern United States","Central America","Western United States","North West Territory","Alberta","Alaska"
        };
        JComboBox attackingTerritory = new JComboBox(allCountriesAttacking);
        JComboBox attackedTerritory = new JComboBox(allCountriesAttacked);
        JButton validateAttack = new JButton("ATTAQUER");
        JButton cancelAttack = new JButton("ANNULER");
        String[] soldats = {"Soldats","1","2","3"};
        String[] canons = {"Canons","1","2","3"};
        String[] cavaliers = {"Cavaliers","1","2","3"};
        JComboBox soldatAttack = new JComboBox(soldats);
        JComboBox canonAttack = new JComboBox(canons);
        JComboBox cavalierAttack = new JComboBox(cavaliers);
        attackPanel.setBounds(1100,250,160,200);
        attackPanel.setBackground(Color.WHITE);
        attackPanel.setVisible(false);
        attackPanel.add(attackingTerritory);
        attackPanel.add(attackedTerritory);
        attackPanel.add(soldatAttack);
        attackPanel.add(canonAttack);
        attackPanel.add(cavalierAttack);
        attackPanel.add(validateAttack);
        attackPanel.add(cancelAttack);
        attackPanel.setLayout(new GridLayout(7,1));

        cancelAttack.addActionListener(e -> {
            attackPanel.setVisible(false);
            playingPanel.setVisible(true);
        });

        this.add(playingPanel);
        this.add(attackPanel);

        attack.addActionListener(e -> {
            attackPanel.setVisible(true);
            playingPanel.setVisible(false);
        });
    }

    private void initWorldTerritories() {
        getTerritories().forEach(t -> t.addToPanel(this));
    }

    public MapPanel(RiskFrame currentFrame, List<Player> players) {
        this.players = players;

        this.initBackgroundImage();

        this.initWorldTerritories();

        this.playingButtons();

        this.setLayout(null);
    }

    public Image getBackgroundImage() {
        return (new ImageIcon("interface/src/ressources/world.jpg")).getImage().getScaledInstance(1280, 715, Image.SCALE_DEFAULT);
    }

    private void initBackgroundImage() {
        this.setBackgroundImage(this.getBackgroundImage());
    }
}
