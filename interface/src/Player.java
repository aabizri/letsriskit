import java.awt.*;
import java.util.List;

public class Player {

   // private engine.Game game;
   // private engine.Player enginePlayer;
    private List<Territory> territories;
    private Color color;
    private String name;
    private Boolean IA;

    public Player(String name, Boolean IA){
        this.name = name;
        this.IA = IA;
    }

    // public List<Territory> getTerritories() { return game.getBoard().getUnitRegistry().getTerritoriesForPlayer(enginePlayer); }

    public Color getColor() { return color; }

    public String getName() {
        return name;
    }

    public Boolean getIA() { return IA; }
/**
    public void play(Turn turn){

    }
**/
}
