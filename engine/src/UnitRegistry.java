import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Thread-safe (should be)
 */
public class UnitRegistry implements Collection<Unit> {
    private final @NotNull Collection<@NotNull Unit> unitList = new ArrayList<>();

    public Stream<Unit> intercept(@NotNull Stream<@NotNull Unit> su) {
        assert(su != null);
        return su.peek(this::add);
    }

    @Contract(pure = true)
    public static @NotNull Predicate<@NotNull Unit> isFromTerritoryPredicate(@NotNull Territory t) {
        assert(t != null);
        return u -> t.equals(u.getCurrentTerritory());
    }

    @Contract(pure = true)
    public static @NotNull Predicate<@NotNull Unit> isFromRegionPredicate(@NotNull Region r) {
        assert(r != null);
        return u -> r.equals(u.getCurrentTerritory().getRegion());
    }

    @Contract(pure = true)
    public static @NotNull Predicate<@NotNull Unit> isOwnedByPredicate(@NotNull Player o) {
        assert(o != null);
        return u -> o.equals(u.getOwner());
    }

    public synchronized @NotNull Collection<@NotNull Unit> findByTerritory(@NotNull Territory t) {
        assert(t != null);

        return this.unitList.stream().filter(
                isFromTerritoryPredicate(t)
        ).collect(Collectors.toList());
    }

    public synchronized @NotNull Collection<Unit> findByRegion(@NotNull Region r) {
        assert(r != null);

        return this.stream().filter(
                isFromRegionPredicate(r)
        ).collect(Collectors.toList());
    }

    public synchronized @NotNull Collection<Unit> findByOwner(@NotNull Player o) {
        assert(o != null);

        return this.stream().filter(
                isOwnedByPredicate(o)
        ).collect(Collectors.toList());
    }

    @Override
    public synchronized int size() {
        return this.unitList.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return this.unitList.isEmpty();
    }

    @Override
    public synchronized boolean contains(Object o) {
        return this.unitList.contains(o);
    }

    @Override @NotNull
    public synchronized Iterator<Unit> iterator() {
        return this.unitList.iterator();
    }

    @Override @NotNull
    public synchronized Object[] toArray() {
        return this.unitList.toArray();
    }

    @Override @NotNull
    public synchronized <T> T[] toArray(@NotNull T[] ts) {
        return this.unitList.toArray(ts);
    }

    @Override
    public synchronized boolean add(@NotNull Unit unit) {
        assert(unit != null);
        return this.unitList.add(unit);
    }

    @Override
    public synchronized boolean remove(Object o) {
        return this.unitList.remove(o);
    }

    @Override
    public synchronized boolean containsAll(Collection<?> collection) {
        return this.unitList.containsAll(collection);
    }

    @Override
    public synchronized boolean addAll(Collection<? extends Unit> collection) {
        return this.unitList.addAll(collection);
    }

    @Override
    public synchronized boolean removeAll(Collection<?> collection) {
        return this.unitList.removeAll(collection);
    }

    @Override
    public synchronized boolean retainAll(Collection<?> collection) {
        return this.unitList.retainAll(collection);
    }

    @Override
    public synchronized void clear() {
        this.unitList.clear();
    }
}
