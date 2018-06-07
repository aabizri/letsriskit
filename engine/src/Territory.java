import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class Territory {
    private String name;

    public String getName() {
        return name;
    }

    private Region region;

    public Region getRegion() {
        return region;
    }

    private Player owner = null;

    public Optional<Player> getOwner() {
        return Optional.ofNullable(owner);
    }

    public void setOwner(Player owner){
        this.owner = owner;
    }

    private Collection<Territory> neighbours = new ArrayList<>();

    public Collection<Territory> getNeighbours() {
        return neighbours;
    }

    public synchronized void addNeighbour(Territory neighbour) {
        this.neighbours.add(neighbour);
    }

    public Territory(Region r, String name) {
        this.region = r;
        this.name = name;
    }

    public boolean calculateIsNeighbour(Territory other) {
        return neighbours.contains(other);
    }

}
