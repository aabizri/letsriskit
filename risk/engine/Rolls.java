package engine;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Random;
import java.util.Map;

/**
 * Immutable, and as such thread-safe
 */
public class Rolls {
    @NotNull private final Map<@NotNull Unit, @NotNull Integer> attackers;
    @NotNull private final Map<@NotNull Unit, @NotNull Integer> defenders;

    public Rolls(@NotNull Random rand, @NotNull UnitSelection attackingParty, @NotNull UnitSelection defendingParty) {
        assert(rand != null); assert(attackingParty != null); assert(defendingParty != null);

        this.attackers = new HashMap<>(attackingParty.size());
        this.defenders = new HashMap<>(defendingParty.size());

        attackingParty.forEach(u -> attackers.put(u, Rolls.rollDice(rand,u.getType())));
        defendingParty.forEach(u -> defenders.put(u, Rolls.rollDice(rand,u.getType())));
    }

    @NotNull
    public Map<@NotNull Unit, @NotNull Integer> getAttackerRolls() {
        return new HashMap<>(attackers);
    }

    @NotNull
    public Map<@NotNull Unit, @NotNull Integer> getDefenderRolls() {
        return new HashMap<>(defenders);
    }

    private static int rollDice(@NotNull Random rand, @NotNull UnitType ut) {
        assert(rand != null); assert(ut != null);
        return ut.getMinPower() + rand.nextInt(ut.getMaxPower() + 1);
    }
}