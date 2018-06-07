package engine;

import java.util.Optional;
import java.util.Random;

/**
 * Should be thread-safe
 */
public interface BattleRound {
    int MAX_ATTACKING_PARTY_SIZE = 3;
    int MAX_DEFENDING_PARTY_SIZE = 2;

    UnitSelection getAttackingParty();

    Territory getDefendingTerritory();

    /**
     * Pick the defending party from the defending territory using the given rules.
     *
     * Should be idempotent, but can change if units move in/out of the territory between two calls
     *
     * @return UnitSelection the defending party
     */
    UnitSelection pickDefendingParty() throws NoUnitOnTerritoryException, CantSelectException;

    /**
     * Engage executes the battle round, returning the report
     *
     * WARNING : the results are NOT committed automatically, one needs to manually execute BattleRoundReport.commit()
     * Or use the other form blitz;
     *
     * @return BattleRoundReport a report of the battle round casualties & rolls
     */
    BattleRoundReport engage(Rolls r);

    default BattleRoundReport engage() throws NoUnitOnTerritoryException, CantSelectException {
        Rolls r = new Rolls(new Random(), this.getAttackingParty(), this.pickDefendingParty());
        return this.engage(r);
    }

    /**
     * Engages using engage() and commits using BattleRoundReport.commit()
     */
    default BattleRoundReport blitz() throws NoUnitOnTerritoryException, CantSelectException {
        BattleRoundReport report = this.engage();
        report.commit();
        return report;
    }

    /**
     * @return true if it wasn't committed before and has been successfully marked as committed
     */
    boolean commit(BattleRoundReport report);

    /**
     * @return true if the BattleRound has been committed
     */
    boolean hasBeenCommitted();

    /**
     * @return report if it has been committed
     */
    Optional<BattleRoundReport> getReport();
}
