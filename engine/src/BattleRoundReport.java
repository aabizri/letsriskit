import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BattleRoundReport {
    private Rolls rolls;
    private Map<Unit, Boolean> attackers; // FALSE: DEAD, TRUE: ALIVE
    private Map<Unit, Boolean> defenders;
    private boolean attackerVictorious;

    BattleRoundReport(Rolls r, Map<Unit, Boolean> attackers, Map<Unit, Boolean> defenders) {
        this.rolls = r;

        this.attackers = attackers;
        this.defenders = defenders;

        this.attackerVictorious = evaluateAttackerVictory(defenders);
    }

    /**
     * Checks if the attacker was victorious.
     */
    private static boolean evaluateAttackerVictory(Map<Unit, Boolean> defenders) {
        return defenders.values().stream().noneMatch(b -> b);
    }

    /**
     * Commit the BattleRound to history, applying the deaths on the defeated units.
     */
    public void commit() {
        Stream.concat(
                attackers.entrySet().stream(),
                defenders.entrySet().stream())

                .filter(entry -> !entry.getValue()).forEach(entry -> entry.getKey().kill());
    }

    public Rolls getRolls() {
        return rolls;
    }

    public Map<Unit, Boolean> getAttackers() {
        return new HashMap<>(attackers);
    }

    public Map<Unit, Boolean> getDefenders() {
        return new HashMap<>(defenders);
    }

    public boolean isAttackerVictorious() {
        return attackerVictorious;
    }
}
