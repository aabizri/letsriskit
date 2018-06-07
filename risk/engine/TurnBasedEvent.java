package engine;

import org.jetbrains.annotations.NotNull;

public class TurnBasedEvent extends UnstructuredEvent {
    private final int turnNumber;

    public TurnBasedEvent(@NotNull String eventType, @NotNull String message, int turnNumber) {
        super(eventType,message);
        this.turnNumber = turnNumber;
    }

    public final int getTurnNumber() {
        return turnNumber;
    }
}
