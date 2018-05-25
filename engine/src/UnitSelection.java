import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.*;

public class UnitSelection implements Movable, Collection<Unit> {
    @NotNull private Game game;

    @NotNull private List<Unit> selection;

    @Nullable private Territory currentTerritory;

    public Territory getCurrentTerritory() {
        return currentTerritory;
    }

    public Player getOwner() {
        assert(currentTerritory.getOwner().isPresent());
        return currentTerritory.getOwner().get();
    }

    private void setUnitsCurrentTerritory(Territory dst) {
        this.selection.forEach(u -> u.setCurrentTerritory(dst));
    }

    public UnitSelection(@NotNull Game game, @NotNull List<Unit> selection) throws Exception {
        assert(game != null);
        assert(selection != null);

        // Check that the selection is of at least one Unit
        if (selection.size() <= 0) {
            throw new Exception("Empty selection");
        }

        // Check that they are all on the same territory !
        if (selection.stream().anyMatch(u -> u != selection.get(0))) {
            throw new Exception("Not all units are on the same territory");
        }

        currentTerritory = selection.get(0).getCurrentTerritory();
        this.selection = selection;
        this.game = game;
    }

    /**
     * @return 0 if UnitSelection is empty
     */
    @Override
    public int getMovesLeft() {
        if (this.selection.size() <= 0) {
            return 0;
        }

        return selection.stream().mapToInt(Unit::getMovesLeft).min().getAsInt();
    }

    @Override
    public void decrementMovesLeft() {
        if (this.getMovesLeft() <= 0) {
            return;
        }

        this.selection.forEach(Unit::decrementMovesLeft);
    }

    public boolean canPeacefullyMove(@NotNull Territory dst) {
        assert(dst != null);

        return this.canMove(dst) && (!dst.getOwner().isPresent() || dst.getOwner().get().equals(this.getOwner()));
    }

    /**
     * Move a unit selection to a new territory, attacking it if necessary for the forceMove
     *
     * The Battle created in case of hostile territory is played through, which means that it may fail and thus the forceMove may not be applied
     */
    public Optional<Battle> forceMove(@NotNull Territory dst) throws Exception {
        assert(dst != null);

        if (!canMove(dst)) {
            throw new Exception("Can't forceMove !");
        }

        Optional<Battle> ob = Optional.empty();
        if (dst.getOwner() != currentTerritory.getOwner()) {
            Battle b = this.attack(dst);
            b.blitz();
            ob = Optional.of(b);

            if (b.isAttackerVictorious()) this.setUnitsCurrentTerritory(dst);
        } else {
            this.peacefulMove(dst);
        }

        return ob;
    }

    public Battle attack(@NotNull Territory dst) throws Exception {
        assert(dst != null);

        if (!canMove(dst)
                && dst.getOwner().isPresent()
                && !dst.getOwner().get().equals(this.getOwner())) {
            throw new Exception("Can't forceMove there");
        }

        return new Battle(this.game, this, dst);
    }

    public void peacefulMove(@NotNull Territory dst) throws Exception {
        assert(dst != null);

        if (!canPeacefullyMove(dst)) {
            throw new Exception("Can't peacefully forceMove there !");
        }

        this.setUnitsCurrentTerritory(dst);
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
