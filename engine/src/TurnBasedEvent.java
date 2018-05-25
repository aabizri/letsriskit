public class TurnBasedEvent extends Event {
    protected int turnNumber;

    public TurnBasedEvent(String eventType, String message, int turnNumber) {
        super(eventType,message);
        this.turnNumber = turnNumber;
    }
}
