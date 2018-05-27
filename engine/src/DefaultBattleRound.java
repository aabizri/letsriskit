import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class DefaultBattleRound implements BattleRound {
    private Game game;

    private UnitSelection attackingParty;
    private Territory defendingTerritory;

    private AtomicBoolean committed = new AtomicBoolean(false);
    private BattleRoundReport report = null;

    public DefaultBattleRound(Game game, UnitSelection attackingParty, Territory defendingTerritory) throws CantAttackException {
        if (this.attackingParty.size() > BattleRound.MAX_ATTACKING_PARTY_SIZE) {
            throw new CantAttackException(attackingParty, defendingTerritory, "Attacking Party Size exceeds maximum !");
        }
        if (game.getBoard().getUnits().findByTerritory(this.attackingParty.getCurrentTerritory()).size() == 0) {
            throw new CantAttackException(attackingParty, defendingTerritory, "When attacking, at least 1 unit should stay in the territory from which originates the attack");
        }
        if (game.getBoard().getUnits().findByTerritory(this.defendingTerritory).size() == 0) {
            throw new CantAttackException(attackingParty, defendingTerritory, "Each territory should have at least 1 unit on it, the defending one doesn't");
        }

        this.attackingParty = attackingParty;
        this.defendingTerritory = defendingTerritory;
        this.game = game;
    }

    public UnitSelection getAttackingParty() {
        return this.attackingParty;
    }

    public Territory getDefendingTerritory() {
        return this.defendingTerritory;
    }

    public UnitSelection pickDefendingParty() throws NoUnitOnTerritoryException, CantSelectException {
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

        if (selectedUnits.size() == 0) {
            throw new NoUnitOnTerritoryException(this.defendingTerritory);
        }

        return new UnitSelection(this.game, selectedUnits);
    }

    @NotNull
    public BattleRoundReport engage(Rolls r) {
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

        return new BattleRoundReport(this, r, attackersCasualties, defendersCasualties);
    }

    public boolean hasBeenCommitted() {
        return this.committed.get();
    }

    public boolean commit(BattleRoundReport report) {
        boolean ok = this.committed.compareAndSet(false, true);
        if (!ok) return false;

        this.report = report;
        return true;
    }

    public Optional<BattleRoundReport> getReport() {
        return Optional.ofNullable(this.report);
    }
}
