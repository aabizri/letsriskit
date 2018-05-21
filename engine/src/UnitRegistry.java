import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UnitRegistry {
    private List<Unit> unitList = new ArrayList<>();

    public void register(Unit u) {
        this.unitList.add(u);
    }

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
}
