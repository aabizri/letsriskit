import java.util.Collection;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.*;

public class UnitType {
    public static UnitType soldier = new UnitType("Soldier", 1, 1, 6, 2, 1, 2);
    public static UnitType rider = new UnitType("Rider", 3, 2, 7, 1, 3, 3);
    public static UnitType canon = new UnitType("Canon", 7, 4, 9, 3, 2,1);

    private String name;
    private int cost;
    private int minPower;
    private int maxPower;
    private int attackPriority;
    private int defensePriority;
    private int moves;

    public String getName() {
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

    public UnitType(String name, int cost, int minPower, int maxPower, int attackPriority, int defensePriority, int moves) {
        this.name = name;
        this.cost = cost;
        this.minPower = minPower;
        this.maxPower = maxPower;
        this.attackPriority = attackPriority;
        this.defensePriority = defensePriority;
        this.moves = moves;
    }

    /**
     * spawns a Unit of this type that hasn't been placed on a territory yet
     *
     * @param owner
     * @return
     */
    public Unit spawn(Player owner) {
        Unit u = new Unit(this, owner);
        return u;
    }

    /**
     * spawns a given number of Units of this type but doesn't place them on a territory
     *
     * @param owner
     * @param quantity
     * @return
     */
    public Collection<Unit> spawn(Player owner, int quantity) {
        return unitStream(owner).limit((long) quantity).collect(Collectors.toList());
    }

    /**
     * generates a spawning stream for units, given a specific Player
     *
     * @param owner
     * @return
     */
    public Stream<Unit> unitStream(Player owner) {
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
            public Spliterator<Unit> trySplit() {
                return new unitSpliterator();
            }
        }
        return StreamSupport.stream(new unitSpliterator(), false);
    }
}