import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnitRegistry implements Collection<Unit> {
    private Collection<Unit> unitList = new ArrayList<>();

    public void register(Unit u) {
        this.unitList.add(u);
    }
    public Stream<Unit> intercept(Stream<Unit> su) {return su.peek(this::register);}

    public static Predicate<Unit> isFromTerritoryPredicate(Territory t) {
        return u -> t.equals(u.getCurrentTerritory());
    }

    public static Predicate<Unit> isFromRegionPredicate(Region r) {
        return u -> r.equals(u.getCurrentTerritory().getRegion());
    }

    public static Predicate<Unit> isOwnedByPredicate(Player o) {
        return u -> o.equals(u.getOwner());
    }

    public Collection<Unit> findByTerritory(Territory t) {
        return this.unitList.stream().filter(
                isFromTerritoryPredicate(t)
        ).collect(Collectors.toList());
    }

    public Collection<Unit> findByRegion(Region r) {
        return this.unitList.stream().filter(
                isFromRegionPredicate(r)
        ).collect(Collectors.toList());
    }

    public Collection<Unit> findByOwner(Player o) {
        return this.unitList.stream().filter(
                isOwnedByPredicate(o)
        ).collect(Collectors.toList());
    }

    @Override
    public int size() {
        return this.unitList.size();
    }

    @Override
    public boolean isEmpty() {
        return this.unitList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.unitList.contains(o);
    }

    @Override
    public Iterator<Unit> iterator() {
        return this.unitList.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.unitList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return this.unitList.toArray(ts);
    }

    @Override
    public boolean add(Unit unit) {
        return this.unitList.add(unit);
    }

    @Override
    public boolean remove(Object o) {
        return this.unitList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.unitList.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends Unit> collection) {
        return this.unitList.addAll(collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return this.unitList.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return this.unitList.retainAll(collection);
    }

    @Override
    public void clear() {
        this.unitList.clear();
    }
}
