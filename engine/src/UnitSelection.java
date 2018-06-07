import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Thread-safe IF listed units aren't manipulated directly
 *
 * TODO: Make it able to select 0 units, and add Ownable interface
 */
public class UnitSelection implements Movable, Collection<Unit> {
    @NotNull private final Game game;

    @NotNull private final List<Unit> selection;

    public UnitSelection(@NotNull Game game, @NotNull List<@NotNull Unit> selection) throws CantSelectException {
        assert(game != null);
        assert(selection != null);

        // Check that the selection is of at least one Unit
        if (selection.size() <= 0) {
            throw new CantSelectException("Empty selection");
        }

        // Check that they are all on the same territory !
        if (selection.stream().anyMatch(u -> u != selection.get(0))) {
            throw new CantSelectException("Not all units are on the same territory");
        }

        this.selection = selection;
        this.game = game;
    }

    @NotNull
    public Territory getCurrentTerritory() {
        return selection.get(0).getCurrentTerritory();

    }

    @NotNull
    public Player getOwner() {
        assert (this.getCurrentTerritory().getOwner().isPresent());
        return this.getCurrentTerritory().getOwner().get();
    }

    @Override
    public synchronized void setCurrentTerritory(@NotNull Territory dst) {
        this.selection.forEach(u -> u.setCurrentTerritory(dst));
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
    public boolean canMove(Territory dst) {
        return this.selection.stream().allMatch(u -> u.canMove(dst));
    }

    private boolean canPeacefullyMove(@NotNull Territory dst) {
        assert(dst != null);

        return this.canMove(dst) && (!dst.getOwner().isPresent() || dst.getOwner().get().equals(this.getOwner()));
    }


    /**
     * Move a unit selection to a new territory, attacking it if necessary for the move
     *
     * The Battle created in case of hostile territory is played through, which means that it may fail and thus the move may not be applied
     */
    @Override
    @NotNull
    public Optional<Battle> move(@NotNull Territory dst) throws CantMoveException {
        assert(dst != null);

        if (!canMove(dst)) {
            throw new CantMoveException(this, dst, null);
        }

        Optional<Battle> ob = Optional.empty();
        if (dst.getOwner() != this.getCurrentTerritory().getOwner()) {
            Battle b = this.attack(dst);
            b.blitz();
            ob = Optional.of(b);

            if (b.isAttackerVictorious()) this.setCurrentTerritory(dst);
        } else {
            this.peacefulMove(dst);
        }

        return ob;
    }

    @NotNull
    private Battle attack(@NotNull Territory dst) throws CantAttackException {
        assert(dst != null);

        if (!canMove(dst)
                && dst.getOwner().isPresent()
                && !dst.getOwner().get().equals(this.getOwner())) {
            throw new CantAttackException(this, dst, null);
        }

        return new DefaultBattle(this.game, this, dst);
    }

    /**
     * @return true if peaceful move successful
     */
    private boolean peacefulMove(@NotNull Territory dst) {
        assert(dst != null);

        if (!canPeacefullyMove(dst)) {
            return false;
        }

        this.setCurrentTerritory(dst);
        return true;
    }

    /**
     * Remove all dead units from selection
     */
    public synchronized void prune() {
        this.selection.stream()
        .filter(Unit::isKilled)
        .forEach(this.selection::remove);
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
