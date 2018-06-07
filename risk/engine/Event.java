package engine;

import org.jetbrains.annotations.NotNull;

/**
 * Should be thread safe
 */
public interface Event {
    @NotNull
    String getType();

    @NotNull
    String getMessage();
}
