package engine;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {
    // State
    private final static int STATE_NOT_STARTED = 0;
    private final static int STATE_PREPARING = 1;
    private final static int STATE_READY = 2;
    private final static int STATE_STARTED = 3;
    private final static int STATE_PLAYING = 4;
    private AtomicInteger state = new AtomicInteger(STATE_NOT_STARTED);

    // Turns
    private AtomicInteger turnCounter = new AtomicInteger(0);
    private List<Turn> turns = new ArrayList<>(1);

    // The board
    private Board board;

    // Players playing the game
    private List<Player> players;

    // Missions
    private Map<Player, Mission> assignedMissions;

    // Unit Types in use
    private Collection<UnitType> unitTypes;

    // Loaded assignedMissions: assignedMissions that can be distributed once ascertained that they are available
    private Collection<Mission> loadedMissions;

    public Game(Collection<Region> regions, List<Player> players, Collection<Mission> missions, Collection<UnitType> unitTypes) {
        this.board = new Board(regions);
        this.players = players;
        this.loadedMissions = missions;
        this.assignedMissions = new HashMap<>(players.size());
        players.forEach(p -> this.assignedMissions.put(p, null));
        this.unitTypes = unitTypes;
    }

    public Board getBoard() {
        return board;
    }

    public Map<Player, Mission> getMissions() {
        return assignedMissions;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public Collection<UnitType> getUnitTypes() {
        return unitTypes;
    }

    public List<Turn> getTurns() {
        return new ArrayList<>(this.turns);
    }


    /**
     * Run the game, this is a blocking operation.
     * <p>
     * Sequence 0 (Standby)        Sequence 1 (Preparation)           Sequence 2 (Play)
     * / ALLOCATING_MISSIONS ----------------------\
     * NOT_STARTED  -                    PREPARING                - READY - PLAYING
     * \ ALLOCATING_TERRITORIES - ALLOCATING_UNITS /
     * <p>
     * Sequence 0      Sequence 1.0               Sequence 1.1         Sequence 2
     */
    public void run() throws GameStateException {
        this.prepare();
        this.play();
    }

    /**
     * Play the game if preparation has been done.
     *
     * @throws GameStateException
     */
    public void play() throws GameStateException {
        // Atomic check
        boolean ok = this.state.compareAndSet(STATE_READY, STATE_STARTED);
        if (!ok) {
            throw new GameStateException();
        }

        // Loop on getNextTurn()
        Optional<Turn> optionalTurn;
        Turn turn;
        while ((optionalTurn = getNextTurn()).isPresent()) {
            optionalTurn.get().execute();
        }
    }

    /**
     * Prepare the game (Allocate assignedMissions, territories & units)
     *
     * @throws GameStateException
     */
    public void prepare() throws GameStateException {
        // Atomic check
        boolean ok = this.state.compareAndSet(STATE_NOT_STARTED, STATE_PREPARING);
        if (!ok) {
            throw new GameStateException();
        }

        // Branch 1: Allocating Missions
        new Thread(() -> {
            try {
                allocateMissions(new Random());
            } catch (GameStateException ignore) {
            }
        }).start();

        // Branch 2: Allocate Territories & Units
        new Thread(() -> {
            try {
                this.allocateTerritories();
                this.allocateUnits();
            } catch (Exception ignore) {
            }
        }).start();

        // Atomic update
        ok = this.state.compareAndSet(STATE_PREPARING, STATE_PREPARING);
        if (!ok) {
            throw new GameStateException();
        }
    }

    private boolean hasNextTurn() {
        // TODO: Check for victory conditions / time exceeded / etc.
        return false;
    }

    private @NotNull Optional<Turn> getNextTurn() {
        boolean ok = this.state.compareAndSet(STATE_STARTED, STATE_PLAYING);
        if (!ok) {
            return Optional.empty();
        }

        if (!hasNextTurn()) {
            this.state.set(STATE_STARTED);
            return Optional.empty();
        }

        int newTurnNumber = this.turnCounter.incrementAndGet();
        Turn newTurn = new Turn(this,newTurnNumber,this.getPlayerForTurnNumber(newTurnNumber));
        this.turns.add(newTurn);

        return Optional.of(newTurn);
    }

    private void allocateMissions(Random rand) throws GameStateException {
        if (this.state.get() != STATE_PREPARING) throw new GameStateException();

        Collection<Mission> availableMissionsSet = this.getAvailableMissions();
        List<Mission> availableMissions = availableMissionsSet instanceof List ? (List) availableMissionsSet : new ArrayList<>(availableMissionsSet);

        this.assignedMissions.entrySet().forEach(e -> {
            Mission m = availableMissions.get(rand.nextInt(availableMissions.size()));
            e.setValue(m);
        });
    }

    private void allocateTerritories() throws GameStateException {
        if (this.state.get() != STATE_PREPARING) throw new GameStateException();

        final BiConsumer<Player, Collection<Territory>> allocateTerritoriesForPlayer = ((player, territories) ->
                territories.forEach(territory -> territory.setOwner(player))
        );

        final Stream<Map.Entry<Player, Collection<Territory>>> territoryDistributionStream = this.generateTerritoryDistributionStream(new Random());

        territoryDistributionStream.parallel()
                .forEach(e -> allocateTerritoriesForPlayer.accept(e.getKey(), e.getValue()));
    }

    private void allocateUnits() throws GameStateException {
        if (this.state.get() != STATE_PREPARING) throw new GameStateException();

        final int unitsPerPlayer = 50 - this.assignedMissions.size() * 5;

        final Consumer<Player> allocateUnitsForPlayer = (player -> {
            ReinforcementsInteraction interaction = new ReinforcementsInteraction(this, player, this.unitTypes, unitsPerPlayer);
            player.prepare(interaction);
        });

        this.assignedMissions.keySet().parallelStream().forEach(allocateUnitsForPlayer);
    }

    /**
     * Distributes the territories among players
     */
    private Stream<Map.Entry<Player, Collection<Territory>>> generateTerritoryDistributionStream(Random rand) {
        Collection<Territory> territoriesCollection = this.getBoard().getTerritories();
        List<Territory> territories = territoriesCollection instanceof List ? (List) territoriesCollection : new ArrayList<>(territoriesCollection);
        Collections.shuffle(territories, rand);

        int territoriesPerPlayer = territories.size() / this.assignedMissions.size();
        int remainingTerritories = territories.size() % this.assignedMissions.size();

        AtomicInteger i = new AtomicInteger(0);
        return this.assignedMissions.keySet().stream().map(p -> {
            int quantityOfTerritoriesForPlayer = territoriesPerPlayer + i.get() < remainingTerritories ? 1 : 0;
            i.getAndIncrement();

            Collection<Territory> territoriesForPlayer = territories.stream().limit(quantityOfTerritoriesForPlayer).collect(Collectors.toCollection(ArrayList::new));
            return new AbstractMap.SimpleEntry<>(p, territoriesForPlayer);
        });
    }

    /**
     * Gets the player corresponding to the given turn
     *
     * @param turnNumber the turn number
     * @return Player the player
     */
    private Player getPlayerForTurnNumber(int turnNumber) {
        int playerIndex = turnNumber % (this.players.size());
        return this.players.get(playerIndex);
    }

    private Collection<Mission> getAvailableMissions() {
        return this.loadedMissions.stream().filter(m -> m.isAvailable(this)).collect(Collectors.toList());
    }

    @TestOnly
    public static void main(String[] args) {

        World world = World.realWorld;
        List<Player> players = new ArrayList<>();
        Collection<Mission> missions = new ArrayList<>();
        Collection<UnitType> unitTypes = UnitType.basicSet;
        Game game = new Game(world.getRegions(), players, missions, unitTypes);
    }
}

