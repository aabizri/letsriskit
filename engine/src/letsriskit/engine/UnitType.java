package letsriskit.engine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

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

    public ByteArrayOutputStream marshalJSON() {

    }

    public void unmarshalJSON(ByteArrayInputStream) {

    }

    /**
     * spawns a letsriskit.engine.Unit of this type that hasn't been placed on a territory yet
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
        ArrayList<Unit> ul = new ArrayList<>(quantity);
        IntStream.range(0,quantity).mapToObj(i -> ul.set(i,this.spawn(owner))).close();
        return ul;
    }
}