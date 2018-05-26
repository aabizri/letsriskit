import java.util.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Should be thread-safe
 */
public class BattleRound {
    private Game game;

    public static int MAX_ATTACKING_PARTY_SIZE = 3;
    public static int MAX_DEFENDING_PARTY_SIZE = 2;

    private UnitSelection attackingParty;
    private Territory defendingTerritory;

    private AtomicBoolean committed = new AtomicBoolean(false);
    private BattleRoundReport report = null;

    public BattleRound(Game game, UnitSelection attackingParty, Territory defendingTerritory) throws Exception {
        if (this.attackingParty.size() > BattleRound.MAX_ATTACKING_PARTY_SIZE) {
            throw new Exception("Attacking Party Size exceeds maximum !");
        }
        if (game.getBoard().getUnits().findByTerritory(this.attackingParty.getCurrentTerritory()).size() == 0) {
            throw new Exception("When attacking, at least 1 unit should stay in the territory from which originates the attack");
        }
        this.attackingParty = attackingParty;
        this.defendingTerritory = defendingTerritory;
        this.game = game;
    }

    public UnitSelection getAttackingParty() {
        return this.attackingParty;
    }

    /**
     * Pick the defending party from the defending territory using the given rules
     *
     * @return UnitSelection the defending party
     *
     * @throws Exception if problem in generating the UnitSelection
     */
    public UnitSelection pickDefendingParty() throws Exception {
        List<Unit> selectedUnits = game.
                getBoard().
                getUnits().
                findByTerritory(defendingTerritory).
                stream().
                sorted(
                    Comparator.comparing(u -> u.getType().getDefensePriority())
                ).
                limit(BattleRound.MAX_DEFENDING_PARTY_SIZE).
                collect(Collectors.toList());
        return new UnitSelection(this.game,selectedUnits);
    }

    private Rolls roll() throws Exception {
        return new Rolls(new Random(), attackingParty, this.pickDefendingParty());
    }

    public BattleRoundReport engage(Rolls r) throws Exception {
        Map<Unit, Integer> attackerRolls = r.getAttackerRolls();
        Map<Unit, Integer> defenderRolls = r.getDefenderRolls();

        Comparator<Map.Entry<Unit, Integer>> rollComparator
                = Comparator.comparingInt(Map.Entry::getValue);

        Comparator<Map.Entry<Unit, Integer>> defensePriorityComparator
                = Comparator.<Map.Entry<Unit, Integer>>comparingInt(entry -> entry.getKey().getType().getDefensePriority()).reversed();
        Comparator<Map.Entry<Unit, Integer>> attackPriorityComparator
                = Comparator.<Map.Entry<Unit, Integer>>comparingInt(entry -> entry.getKey().getType().getAttackPriority()).reversed();

        Comparator<Map.Entry<Unit, Integer>> defenseComparator = rollComparator.thenComparing(defensePriorityComparator);
        Comparator<Map.Entry<Unit, Integer>> attackComparator = rollComparator.thenComparing(attackPriorityComparator);

        Map<Unit, Boolean> attackersCasualties = new HashMap<>(attackerRolls.size());
        Map<Unit, Boolean> defendersCasualties = new HashMap<>(defenderRolls.size());

        for (int i = 0; i < Integer.min(r.getAttackerRolls().size(), r.getDefenderRolls().size()); i++) {
            Map.Entry<Unit, Integer> attackerEntryWithMaxRoll = attackerRolls.entrySet().stream().max(attackComparator).get();
            Map.Entry<Unit, Integer> defenderEntryWithMaxRoll = defenderRolls.entrySet().stream().max(defenseComparator).get();

            boolean attackerWins = attackerEntryWithMaxRoll.getValue() > defenderEntryWithMaxRoll.getValue();

            // Remove from map so as not to iterate on them again
            defenderRolls.remove(defenderEntryWithMaxRoll.getKey());
            attackerRolls.remove(attackerEntryWithMaxRoll.getKey());

            // Mark casualties
            attackersCasualties.put(attackerEntryWithMaxRoll.getKey(), attackerWins);
            defendersCasualties.put(defenderEntryWithMaxRoll.getKey(), !attackerWins);
        }

        return new BattleRoundReport(this,r,attackersCasualties,defendersCasualties);
    }

    /**
     * Engage executes the battle round, returning the report
     *
     * WARNING : the results are NOT committed automatically, one needs to manually execute BattleRoundReport.commit()
     * Or use the other form blitz;
     *
     * @return BattleRoundReport a report of the battle round casualties & rolls
     */
    public BattleRoundReport engage() throws Exception {
        Rolls r = this.roll();
        return this.engage(r);
    }

    /**
     * Engages using engage() and commits using BattleRoundReport.commit()
     *
     * @return
     * @throws Exception
     */
    public BattleRoundReport blitz() throws Exception {
        BattleRoundReport report = this.engage();
        report.commit();
        return report;
    }

    /**
     * @return true if it wasn't committed before and has been successfully marked as committed
     */
    public boolean commit(BattleRoundReport report) {
        boolean ok = this.committed.compareAndSet(false,true);
        if (!ok) return false;

        this.report = report;
        return true;
    }

    /**
     * Returns report if it has been committed
     *
     * @return
     */
    public Optional<BattleRoundReport> getReport() {
        return Optional.ofNullable(this.report);
    }

    /**
     * @return true if the BattleRound has been committed
     */
    public boolean hasBeenCommitted() {
        return this.committed.get();
    }
}
