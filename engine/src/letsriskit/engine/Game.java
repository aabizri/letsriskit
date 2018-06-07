import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    // The board
    private Board board;

    // Players playing the game
    private Map<Player, Mission> players;

    // Unit Types in use
    private Collection<UnitType> unitTypes;

    public Game(Board board, List<Player> players, Collection<UnitType> unitTypes) {
        this.board = board;
        this.players = new HashMap<>(players.size());
        players.forEach(p -> this.players.put(p, null));
        this.unitTypes = unitTypes;
    }

    public Board getBoard() {
        return board;
    }

    public Map<Player, Mission> getPlayers() {
        return players;
    }

    public Collection<UnitType> getUnitTypes() {
        return unitTypes;
    }
}

