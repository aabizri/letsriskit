import java.util.Collection;
import java.util.List;

public class Game {
    // The regions used in the game
    private Collection<Region> regions;

    // Players
    private List<Player> players;

    // Unit Types
    private Collection<UnitType> unitTypes;

    // Unit registry
    public UnitRegistry unitRegistry = new UnitRegistry();

    public Game(Collection<Region> regions, List<Player> players, Collection<UnitType> unitTypes) {
        this.regions = regions;
        this.players = players;
        this.unitTypes = unitTypes;
    }
}

