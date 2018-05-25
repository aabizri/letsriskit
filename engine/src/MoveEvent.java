public class MoveEvent extends TurnBasedEvent {
    private static final String eventType = "MoveEvent";

    private Player player;
    private Territory from;
    private Territory to;
    private UnitSelection units;

    public MoveEvent(int turnNumber, Player player, Territory from, Territory to, UnitSelection units) {
        super(eventType,"Player moved troops between two territories", turnNumber);
        this.player = player;
        this.from = from;
        this.to = to;
        this.units = units;
    }
}
