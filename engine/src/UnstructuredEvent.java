import org.jetbrains.annotations.NotNull;

public class UnstructuredEvent implements Event {
    @NotNull
    private final String type;
    @NotNull
    private final String message;

    @NotNull
    public String getType() {
        return type;
    }

    @NotNull
    public String getMessage() {
        return message;
    }

    public UnstructuredEvent(@NotNull String type, @NotNull String message) {
        assert (type != null);
        assert (message != null);

        this.type = type;
        this.message = message;
    }
}
