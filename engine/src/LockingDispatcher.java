import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/**
 * Thread-safe (because of ReadWrite lock implementation)
 */
public class LockingDispatcher implements Dispatcher {
    @NotNull
    private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
    @NotNull
    private final Lock rlock = rwlock.readLock();
    @NotNull
    private final Lock wlock = rwlock.writeLock();

    @NotNull
    private final Map<
            @NotNull String,
            @NotNull Collection<@NotNull Consumer<@NotNull Event>>>
            table = new HashMap<>();

    public void listen(@NotNull String eventType, @NotNull Consumer<@NotNull Event> listener) {
        assert (eventType != null);
        assert (listener != null);

        wlock.lock();
        @Nullable Collection<@NotNull Consumer<@NotNull Event>> listeners = this.table.get(eventType);

        if (listeners == null) {
            listeners = new ArrayList<>();
            table.put(eventType, listeners);
        }

        listeners.add(listener);
        wlock.unlock();
    }

    public void dispatch(@NotNull Event e) {
        assert (e != null);

        final @NotNull Optional<@NotNull Collection<@NotNull Consumer<@NotNull Event>>> listeners = this.getListeners(e);
        if (listeners.isPresent() && !listeners.get().isEmpty()) {
            listeners.get().forEach(l -> l.accept(e));
        }
    }

    private @NotNull Optional<@NotNull Collection<@NotNull Consumer<@NotNull Event>>> getListeners(@NotNull Event e) {
        assert (e != null);

        return this.getListeners(e.getType());
    }

    private @NotNull Optional<@NotNull Collection<@NotNull Consumer<@NotNull Event>>> getListeners(@NotNull String eventType) {
        assert (eventType != null);

        rlock.lock();
        @Nullable final Collection<Consumer<Event>> res = this.table.get(eventType);
        rlock.unlock();

        return Optional.ofNullable(res);
    }
}
