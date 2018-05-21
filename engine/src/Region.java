import java.util.List;
import java.util.Optional;

public class Region {
    private String name;
    private List<Territory> territories;

    public String getName() {
        return this.name;
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
}
