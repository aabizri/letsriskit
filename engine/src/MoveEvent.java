import org.jetbrains.annotations.NotNull;

/**
 * Immutable
 */
public class MoveEvent extends TurnBasedEvent {
    private static final String eventType = "MoveEvent";
    private static final String message = "Player moved troops between two territories";

    private final @NotNull Player player;
    private final @NotNull Territory from;
    private final @NotNull Territory to;
    private final @NotNull UnitSelection units;

    public MoveEvent(int turnNumber, @NotNull Player player, @NotNull Territory from, @NotNull Territory to, @NotNull UnitSelection units) {
        super(eventType,message, turnNumber);

        assert(player != null && from != null && to != null && units != null);

        this.player = player;
        this.from = from;
        this.to = to;
        this.units = units;
    }

    @NotNull
    public Player getPlayer() {
        return this.player;
    }

    @NotNull
    public Territory getFrom() {
        return this.from;
    }

    @NotNull
    public Territory getTo() {
        return this.to;
    }

    @NotNull
    public UnitSelection getUnits() {
        return this.units;
    }
}
