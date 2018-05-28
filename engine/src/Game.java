import java.util.*;

public class Game {
    // The board
    private Board board;

    // Players playing the game
    private Map<Player, Mission> missions;

    // Unit Types in use
    private Collection<UnitType> unitTypes;

    public Game(Board board, List<Player> players, Collection<UnitType> unitTypes) {
        this.board = board;
        this.missions = new HashMap<>(players.size());
        players.forEach(p -> this.missions.put(p, null));
        this.unitTypes = unitTypes;
    }

    public Board getBoard() {
        return board;
    }

    public Map<Player, Mission> getMissions() {
        return missions;
    }

    public Set<Player> getPlayers() {
        return this.missions.keySet();
    }

    public Collection<UnitType> getUnitTypes() {
        return unitTypes;
    }
}