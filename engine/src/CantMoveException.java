import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CantMoveException extends Exception {
    private Movable subject;
    private Territory destination;
    private String info;

    CantMoveException(@NotNull Movable subject, @NotNull Territory destination, @Nullable String info) {
        assert (subject != null && destination != null);

        this.subject = subject;
        this.destination = destination;
        this.info = info;
    }

    public Movable getSubject() {
        return subject;
    }

    public Territory getDestination() {
        return destination;
    }

    public String getMessage() {
        return "Movable in territory \"" + subject.getCurrentTerritory().getName() + "\" can't move to territory \"" + destination.getName() + "\". Info: " + info;
    }
}
