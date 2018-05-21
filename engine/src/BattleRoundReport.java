import java.util.Map;
import java.util.stream.Stream;

public class BattleRoundReport {
    private Rolls rolls;
    private Map<Unit, Boolean> attackers;
    private Map<Unit, Boolean> defenders;
    private boolean attackerVictory;

    BattleRoundReport(Rolls r, Map<Unit, Boolean> attackers, Map<Unit, Boolean> defenders) {
        this.rolls = r;
        this.attackers = attackers;
        this.defenders = defenders;
    }

    /**
     * Commit the BattleRound to history, applying the deaths on the defeated units
     */
    public void commit() {
        Stream.concat(attackers.entrySet().stream(),defenders.entrySet().stream()).filter(entry -> !entry.getValue()).forEach(entry -> entry.getKey().kill());
    }

    public Rolls getRolls() {
        return rolls;
    }

    public Map<Unit, Boolean> getAttackers() {
        return attackers;
    }

    public Map<Unit, Boolean> getDefenders() {
        return defenders;
    }

    public boolean isAttackerVictorious() {
        return attackerVictory;
    }
}
