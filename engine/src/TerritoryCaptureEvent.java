import org.jetbrains.annotations.NotNull;

/**
 * Immutable
 */
public class TerritoryCaptureEvent extends TurnBasedEvent {
    private static final String eventType = "TerritoryCaptureEvent";
    private static final String message = "Territory Captured";

    private final @NotNull Player conqueror;
    private final @NotNull Territory captured;

    public TerritoryCaptureEvent(int turnNumber, @NotNull Player conqueror, @NotNull Territory captured) {
        super(eventType, message, turnNumber);

        assert(conqueror != null && captured != null);

        this.conqueror = conqueror;
        this.captured = captured;
    }

    @NotNull
    public Player getConqueror() {
        return conqueror;
    }

    @NotNull
    public Territory getCaptured() {
        return captured;
    }
}
