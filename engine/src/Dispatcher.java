import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

public class Dispatcher {
    private ReadWriteLock rwlock = new ReentrantReadWriteLock();
    private Lock rlock = rwlock.readLock();
    private Lock wlock = rwlock.writeLock();

    private Map<String,Collection<Consumer<Event>>> table = new HashMap<>();

    public void listen(String eventType, Consumer<Event> listener) {
        Collection<Consumer<Event>> listeners = getListeners(eventType);

        wlock.lock();
        if (listeners.size() == 0) {
            listeners = new ArrayList<>();
            table.put(eventType,listeners);
        }

        listeners.add(listener);
        wlock.unlock();
    }

    public void dispatch(Event e) {
        Collection<Consumer<Event>> listeners = this.getListeners(e);
        if (listeners.size() != 0) {
            listeners.forEach(l -> l.accept(e));
        }
    }

    private Collection<Consumer<Event>> getListeners(Event e) {return this.getListeners(e.getType());}
    private Collection<Consumer<Event>> getListeners(String eventType) {
        rlock.lock();
        Collection<Consumer<Event>> res = this.table.get(eventType);
        rlock.unlock();
        return res;
    }
}
