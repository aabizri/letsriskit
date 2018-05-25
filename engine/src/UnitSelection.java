import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class UnitSelection implements Movable, Collection<Unit> {

    private Game game;

    private List<Unit> selection;

    private Territory currentTerritory;

    public Territory getCurrentTerritory() {
        return currentTerritory;
    }

    public Player getOwner() { assert(currentTerritory.getOwner().isPresent()); return currentTerritory.getOwner().get();}

    private void setUnitsCurrentTerritory(Territory dst) {
        this.selection.forEach(u -> u.setCurrentTerritory(dst));
    }

    public UnitSelection(Game game, List<Unit> selection) throws Exception {
        if (selection.size() == 0) return ;

        // Check that they are all on the same territory !
        if (selection.stream().anyMatch(u -> u != selection.get(0))) {
            throw new Exception("Not all units are on the same territory");
        }

        currentTerritory = selection.get(0).getCurrentTerritory();
        this.selection = selection;
    }

    public int getMovesLeft() {
        assert(selection.size() >= 1); // To get as int
        return selection.stream().mapToInt(Unit::getMovesLeft).min().getAsInt();
    }

    public void decrementMovesLeft() {
        this.selection.forEach(Unit::decrementMovesLeft);
    }

    public Optional<BattleRound> move(Territory dst) throws Exception {
        if (!canMove(dst)) {
            throw new Exception("Can't move !");
        }

        Optional<BattleRound> ob = Optional.empty();
        if (dst.getOwner() != currentTerritory.getOwner()) {
            BattleRound b = this.attack(dst);
            ob = Optional.of(b);

            // If the attacker loses, then we don't move the units
            if (!b.engage().isAttackerVictorious()) {
                return ob;
            }
        }

        this.setUnitsCurrentTerritory(dst);
        return ob;
    }

    public BattleRound attack(Territory dst) throws Exception {
        return new BattleRound(this.game, this, dst);
    }

    @Override
    public int size() {
        return this.selection.size();
    }

    @Override
    public boolean isEmpty() {
        return this.selection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.selection.contains(o);
    }

    @Override
    public Iterator<Unit> iterator() {
        return this.selection.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.selection.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return this.selection.toArray(ts);
    }

    @Override
    public boolean add(Unit unit) {
        return this.selection.add(unit);
    }

    @Override
    public boolean remove(Object o) {
        return this.selection.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.selection.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends Unit> collection) {
        return this.selection.addAll(collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return this.selection.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return this.selection.retainAll(collection);
    }

    @Override
    public void clear() {
        this.selection.clear();
    }
}
