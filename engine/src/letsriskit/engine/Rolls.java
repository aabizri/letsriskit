package letsriskit.engine;

import java.util.HashMap;
import java.util.Random;
import java.util.Map;

public class Rolls {
    private Map<Unit, Integer> attackers;
    private Map<Unit, Integer> defenders;

    public Rolls(Random rand, UnitSelection attackingParty, UnitSelection defendingParty) {
        this.attackers = new HashMap<>(attackingParty.size());
        this.defenders = new HashMap<>(defendingParty.size());

        attackingParty.forEach(u -> attackers.put(u,
                u.getType().getMinPower() + rand.nextInt(u.getType().getMaxPower() + 1)
        ));
        defendingParty.forEach(u -> defenders.put(u,
                u.getType().getMinPower() + rand.nextInt(u.getType().getMaxPower() + 1)
        ));
    }

    public Map<Unit, Integer> getAttackerRolls() {
        return new HashMap<>(attackers);
    }

    public Map<Unit, Integer> getDefenderRolls() {
        return new HashMap<>(defenders);
    }
}