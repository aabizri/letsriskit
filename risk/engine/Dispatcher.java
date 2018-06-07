package engine;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Should be thread-safe
 */
public interface Dispatcher {
    void listen(@NotNull String eventType, @NotNull Consumer<@NotNull Event> listener);

    void dispatch(@NotNull Event e);
}
