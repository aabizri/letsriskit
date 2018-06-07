package engine;

import java.util.Collection;
import java.util.stream.Collectors;

public class Board {
    private Collection<Region> regions;
    private UnitRegistry units;

    public void register(Unit u) {
        units.add(u);
    }

    public Collection<Region> getRegions() {
        return regions;
    }

    public Collection<Territory> getTerritories() {
        return regions.stream().flatMap(r -> r.getTerritories().stream()).collect(Collectors.toList());
    }

    public UnitRegistry getUnits() {
        return units;
    }

    public Board(Collection<Region> regions) {
        this.regions = regions;
    }
}
