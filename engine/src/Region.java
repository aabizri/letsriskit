import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Region {
    private String name;
    private List<Territory> territories = new ArrayList<>();

    public String getName() {
        return this.name;
    }

    public Region(@NotNull String name) {
        this.name = name;
    }

    /**
     * @return the territories in the region
     */
    public List<Territory> getTerritories() {
        return this.territories;
    }

    /**
     * @return the owner of the whole region if there is one, or an empty Optional if there isn't
     */
    public Optional<Player> getOwner() {
        Optional<Player> ret = Optional.empty();
        Territory sampleTerritory = this.territories.get(0);
        if (this.territories.stream().anyMatch(u -> !u.getOwner().equals(sampleTerritory.getOwner()))) {
            return ret;
        }
        return sampleTerritory.getOwner();
    }

    /**
     * creates a new territory correctly associated with this region
     *
     * @param name the name of the territory
     * @return the new territory
     */
    public Territory newTerritory(String name) {
        Territory t = new Territory(this,name);
        this.territories.add(t);
        return t;
    }
}
