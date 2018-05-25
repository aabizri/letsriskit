public class TerritoryCaptureEvent extends TurnBasedEvent {
    private static final String eventType = "TerritoryCaptureEvent";

    private Player conqueror;
    private Territory captured;

    public TerritoryCaptureEvent(int turnNumber, Player conqueror, Territory captured) {
        super(eventType, "Territory Captured", turnNumber);
        this.conqueror = conqueror;
        this.captured = captured;
    }

    public Player getConqueror() {
        return conqueror;
    }

    public Territory getCaptured() {
        return captured;
    }
}
