package engine;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DefaultBattle is a model of a battle in multiple rounds (see BattleRound).
 * <p>
 * Should be thread-safe, as long as the Attacking Units aren't altered and the defending territory not changed
 */
public class DefaultBattle implements Battle {
    @NotNull
    private final Game game;
    @NotNull
    private final List<@NotNull BattleRound> rounds = new ArrayList<>(1);
    @NotNull
    private final UnitSelection attackingParty;
    @NotNull
    private final Territory defendingTerritory;

    public DefaultBattle(
            @NotNull final Game game,
            @NotNull final UnitSelection attackingParty,
            @NotNull final Territory defendingTerritory)
            throws CantAttackException {
        assert (game != null);
        assert (attackingParty != null);
        assert (defendingTerritory != null);

        if (game.getBoard().getUnits().findByTerritory(attackingParty.getCurrentTerritory()).size() == 0) {
            throw new CantAttackException(attackingParty, defendingTerritory, "When attacking, at least 1 unit should stay in the territory from which originates the attack");
        }
        this.attackingParty = attackingParty;
        this.defendingTerritory = defendingTerritory;
        this.game = game;
    }

    @NotNull
    public UnitSelection getAttackingParty() {
        return this.attackingParty;
    }

    @NotNull
    public Territory getDefendingTerritory() {
        return this.defendingTerritory;
    }

    @NotNull
    public List<@NotNull BattleRound> getRounds() {
        return new ArrayList<>(this.rounds);
    }

    public boolean hasNextRound() {
        return (this.rounds.size() == 0
                || (this.rounds.get(this.rounds.size() - 1).hasBeenCommitted() && !this.rounds.get(this.rounds.size() - 1).getReport().get().isAttackerVictorious()))
                && defendingTerritory.getOwner().isPresent()
                && !defendingTerritory.getOwner().get().equals(attackingParty.getOwner())
                && !attackingParty.getCurrentTerritory().equals(defendingTerritory)
                && attackingParty.getCurrentTerritory().calculateIsNeighbour(defendingTerritory)
                && attackingParty.size() > 0;
    }

    /**
     * Generates and returns the next BattleRound to be played
     * <p>
     * Note: synchronized so as to have only one BattleRound be played at a time (duh)
     */
    @NotNull
    public synchronized Optional<BattleRound> nextRound() {
        if (!hasNextRound()) {
            return Optional.empty();
        }

        BattleRound nextRound;
        try {
            nextRound = new DefaultBattleRound(game, attackingParty, defendingTerritory);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();

        }

        this.rounds.add(nextRound);
        return Optional.of(nextRound);
    }
}
