import org.jetbrains.annotations.NotNull;

/**
 * Immutable, so thread-safe
 */
public class Event {
    @NotNull private final String type;
    @NotNull private final String message;

    @NotNull
    public String getType() {
        return type;
    }

    @NotNull
    public String getMessage() {
        return message;
    }

    public Event(@NotNull String type, @NotNull String message) {
        assert(type != null); assert(message != null);

        this.type = type;
        this.message = message;
    }
}
