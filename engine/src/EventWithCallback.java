import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Immutable
 *
 * @param <T>
 * @param <R>
 */
public class EventWithCallback<T,R> extends Event{
    public static final String type = "EventWithCallback";
    private final @NotNull Function<T,R> callback;

    public EventWithCallback(@NotNull String message, @NotNull Function<T,R> callback) {
        super(type, message);

        assert(callback != null);

        this.callback = callback;
    }

    public R call(T param) {
        return callback.apply(param);
    }
}
