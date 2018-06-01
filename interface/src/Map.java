import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JPanel;

import javax.swing.*;

public class Map extends JPanel {

    public Map(){
    }

    public Collection initWorldTerritories(){
        Collection<Territory> territories = new ArrayList<>(42);
        territories.add(new Territory("Eastern Australia",1088,629));
        territories.add(new Territory("Western Australia",986,642));
        territories.add(new Territory("New Guinea",1038,505));
        territories.add(new Territory("Indonesia",938,525));
        territories.add(new Territory("Japan",1043,255));
        territories.add(new Territory("Siam",929,412));
        territories.add(new Territory("Kamtchatka",1019,92));
        territories.add(new Territory("India",827,382));
        territories.add(new Territory("China",898,318));
        territories.add(new Territory("Mongolia",932,247));
        territories.add(new Territory("Urkutsk",919,178));
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
        territories.add(new Territory("Westhern United States",155, 253));
        territories.add(new Territory("North West Territory",173, 112));
        territories.add(new Territory("Alberta",159, 168));
        territories.add(new Territory("Alaska",58, 112));
        return territories;
    }
}
