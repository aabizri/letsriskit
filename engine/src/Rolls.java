import java.util.HashMap;
import java.util.Random;
import java.util.Map;

public class Rolls {
    private Map<Unit, Integer> attackers;
    private Map<Unit, Integer> defenders;

    public Rolls(Random rand, UnitSelection attackingParty, UnitSelection defendingParty) {
        this.attackers = new HashMap<>(attackingParty.size());
        this.defenders = new HashMap<>(defendingParty.size());

        attackingParty.forEach(u -> attackers.put(u, Rolls.rollDice(rand,u.getType())));
        defendingParty.forEach(u -> defenders.put(u, Rolls.rollDice(rand,u.getType())));
    }

    public Map<Unit, Integer> getAttackerRolls() {
        return new HashMap<>(attackers);
    }

    public Map<Unit, Integer> getDefenderRolls() {
        return new HashMap<>(defenders);
    }

    private static int rollDice(Random rand, UnitType ut) {
        return ut.getMinPower() + rand.nextInt(ut.getMaxPower() + 1);
    }
}