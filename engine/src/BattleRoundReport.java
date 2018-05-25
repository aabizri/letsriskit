import java.util.Map;
import java.util.stream.Stream;

/**
 * Thread-safe because its immutable
 */
public class BattleRoundReport {
    private final BattleRound origin;
    private final Rolls rolls;
    private final Map<Unit, Boolean> attackers;
    private final Map<Unit, Boolean> defenders;
    private boolean attackerVictory;

    BattleRoundReport(BattleRound br, Rolls r, Map<Unit, Boolean> attackers, Map<Unit, Boolean> defenders) {
        this.origin = br;
        this.rolls = r;
        this.attackers = attackers;
        this.defenders = defenders;
    }

    /**
     * Commit the BattleRound to history, applying the deaths on the defeated units
     *
     * NOTE: no need for synchronized as we call an atomic method (BattleRound.commit)
     *
     * @return true if commit success, false it another report has already been committed
     */
    public boolean commit() {
        boolean ok = this.origin.commit(this);
        if (!ok) {
            return false;
        }

        Stream.concat(attackers.entrySet().stream(),defenders.entrySet().stream()).filter(entry -> !entry.getValue()).forEach(entry -> entry.getKey().kill());
        return true;
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
