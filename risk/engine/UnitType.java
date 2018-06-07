package engine;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Immutable, and as such thread-safe
 */
public class UnitType {
    public final static UnitType soldier = new UnitType("Soldier", 1, 1, 6, 2, 1, 2);
    public final static UnitType rider = new UnitType("Rider", 3, 2, 7, 1, 3, 3);
    public final static UnitType canon = new UnitType("Canon", 7, 4, 9, 3, 2,1);
    public final static Collection<UnitType> basicSet = new ArrayList<>(Arrays.asList(soldier, rider, canon));

    private @NotNull String name;
    private int cost;
    private int minPower;
    private int maxPower;
    private int attackPriority;
    private int defensePriority;
    private int moves;

    public @NotNull String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getMinPower() {
        return minPower;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public int getAttackPriority() {
        return attackPriority;
    }

    public int getDefensePriority() {
        return defensePriority;
    }

    public int getMoves() { return moves; }

    public UnitType(@NotNull String name, int cost, int minPower, int maxPower, int attackPriority, int defensePriority, int moves) {
        assert(name != null);
        this.name = name;

        assert(cost > 0);
        this.cost = cost;

        assert(minPower >= 0 && minPower < maxPower);
        this.minPower = minPower;
        this.maxPower = maxPower;

        assert(attackPriority >= 0);
        this.attackPriority = attackPriority;

        assert(defensePriority >= 0);
        this.defensePriority = defensePriority;

        assert(moves >= 0);
        this.moves = moves;
    }

    /**
     * JSON format:
     *
     * {
     *     "name": ?;
     *     "cost": ?;
     *     "min-power": ?;
     *     "max-power": ?;
     *     "attack-priority": ?:
     *     "defense-priority": ?:
     *     "moves": ?:
     * }
     *
     * @return
     */
    public ByteArrayOutputStream marshalJSON() {
        // TODO
        return null;
    }

    public void unmarshalJSON(ByteArrayInputStream input) {

    }

    /**
     * spawns a Unit of this type that hasn't been placed on a territory yet
     *
     * @param owner
     * @return
     */
    @NotNull
    public Unit spawn(@NotNull Player owner) {
        assert(owner != null);
        return new Unit(this, owner);
    }

    /**
     * spawns a given number of Units of this type but doesn't place them on a territory
     *
     * @param owner
     * @param quantity
     * @return
     */
    public @NotNull Collection<@NotNull Unit> spawn(@NotNull Player owner, int quantity) {
        assert(owner != null);
        return unitStream(owner).limit((long) quantity).collect(Collectors.toList());
    }

    /**
     * generates a spawning stream for units, given a specific Player
     *
     * @param owner
     * @return
     */
    public @NotNull Stream<@NotNull Unit> unitStream(@NotNull Player owner) {
        assert(owner != null);

        UnitType ut = this;
        class unitSpliterator implements Spliterator<Unit> {
            @Override
            public int characteristics() {
                return CONCURRENT | DISTINCT | NONNULL;
            }

            @Override
            public long estimateSize() {
                return Long.MAX_VALUE;
            }

            @Override
            public boolean tryAdvance(Consumer<? super Unit> consumer) {
                Unit u = ut.spawn(owner);
                consumer.accept(u);
                return true;
            }

            @Override
            public @NotNull Spliterator<@NotNull Unit> trySplit() {
                return new unitSpliterator();
            }
        }
        return StreamSupport.stream(new unitSpliterator(), false);
    }
}