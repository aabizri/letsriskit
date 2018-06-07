import java.util.Optional;

public interface Ownable {
    Optional<Player> getOwner();

    void setOwner(Player owner);
}
