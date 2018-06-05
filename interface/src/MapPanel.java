import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import javax.swing.JPanel;

import javax.swing.*;

public class MapPanel extends Panel {

    private List<Player> players;

    private static Collection<Territory> getTerritories(){
        final Collection<Territory> territories = new ArrayList<>(42);
        territories.add(new Territory("Eastern Australia",1088,645));
        territories.add(new Territory("Western Australia",986,652));
        territories.add(new Territory("New Guinea",1038,513));
        territories.add(new Territory("Indonesia",938,505));
        territories.add(new Territory("Japan",1041,260));
        territories.add(new Territory("Siam",929,412));
        territories.add(new Territory("Kamchatka",1019,92));
        territories.add(new Territory("India",827,382));
        territories.add(new Territory("China",898,322));
        territories.add(new Territory("Mongolia",932,251));
        territories.add(new Territory("Irkutsk",919,178));
        territories.add(new Territory("Yakutsk",927,85));
        territories.add(new Territory("Siberia",848,121));
        territories.add(new Territory("Ural",777,175));
        territories.add(new Territory("Afghanistan",757,273));
        territories.add(new Territory("Middle East",683, 381));
        territories.add(new Territory("Ukraine",657, 194));
        territories.add(new Territory("Scandinavia",560, 135));
        territories.add(new Territory("Northern Europe",554, 249));
        territories.add(new Territory("Southern Europe",568, 311));
        territories.add(new Territory("Western Europe",478, 325));
        territories.add(new Territory("Iceland",471, 144));
        territories.add(new Territory("Great Britain",450, 227));
        territories.add(new Territory("Eastern Africa",667, 517));
        territories.add(new Territory("Madagascar",705, 620));
        territories.add(new Territory("South Africa",608, 655));
        territories.add(new Territory("Congo",601, 551));
        territories.add(new Territory("Egypt",606, 427));
        territories.add(new Territory("Northern Africa",516, 468));
        territories.add(new Territory("Argentina",277, 603));
        territories.add(new Territory("Brazil",334, 478));
        territories.add(new Territory("Peru",235, 494));
        territories.add(new Territory("Venezuela",251, 412));
        territories.add(new Territory("Greenland",384, 70));
        territories.add(new Territory("Quebec",306, 186));
        territories.add(new Territory("Ontario",231, 148));
        territories.add(new Territory("Eastern United States",240, 240));
        territories.add(new Territory("Central America",192, 350));
        territories.add(new Territory("Western United States",155, 253));
        territories.add(new Territory("North West Territory",173, 112));
        territories.add(new Territory("Alberta",159, 170));
        territories.add(new Territory("Alaska",58, 114));
        return territories;
    }

    private void initWorldTerritories() {
        getTerritories().forEach(t -> t.addToPanel(this) );
    }



    public MapPanel(RiskFrame currentFrame, List<Player> players) {
        this.players = players;

        this.initBackgroundImage();

        this.initWorldTerritories();

        this.setLayout(null);
    }

    public Image getBackgroundImage() {
        return (new ImageIcon("interface/src/ressources/world.jpg")).getImage().getScaledInstance(1280, 715, Image.SCALE_DEFAULT);
    }

    private void initBackgroundImage() {
        this.setBackgroundImage(this.getBackgroundImage());
    }
}
