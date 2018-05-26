import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Battle class is a model of a battle in multiple rounds (see BattleRound).
 *
 * Should be thread-safe, as long as the Attacking Units aren't altered and the defending territory not changed
 */
public class Battle {
    @NotNull private final Game game;

    @NotNull private final List<@NotNull BattleRound> rounds = new ArrayList<>(1);

    @NotNull private final UnitSelection attackingParty;
    @NotNull private final Territory defendingTerritory;

    public Battle(
            @NotNull final Game game,
            @NotNull final UnitSelection attackingParty,
            @NotNull final Territory defendingTerritory)
            throws Exception
    {
        assert(game != null); assert(attackingParty != null); assert(defendingTerritory != null);

        if (game.getBoard().getUnits().findByTerritory(attackingParty.getCurrentTerritory()).size() == 0) {
            throw new Exception("When attacking, at least 1 unit should stay in the territory from which originates the attack");
        }
        this.attackingParty = attackingParty;
        this.defendingTerritory = defendingTerritory;
        this.game = game;
    }

    @NotNull
    public List<@NotNull BattleRound> getRounds() {
        return new ArrayList<>(this.rounds);
    }

    public boolean hasNextRound() {
        return (this.rounds.size() == 0
                || (this.rounds.get(this.rounds.size()-1).hasBeenCommitted() && !this.rounds.get(this.rounds.size()-1).getReport().get().isAttackerVictorious()))
                && defendingTerritory.getOwner().isPresent()
                && !defendingTerritory.getOwner().get().equals(attackingParty.getOwner())
                && !attackingParty.getCurrentTerritory().equals(defendingTerritory)
                && attackingParty.getCurrentTerritory().calculateIsNeighbour(defendingTerritory)
                && attackingParty.size() > 0;
    }

    /**
     * Generates and returns the next BattleRound to be played
     *
     * Note: synchronized so as to have only one BattleRound be played at a time (duh)
     *
     * @return
     * @throws Exception
     */
    @NotNull
    public synchronized BattleRound nextRound() throws Exception {
        if (!hasNextRound()) {
            throw new Exception("No next round possible");
        }

        BattleRound nextRound = new BattleRound(game, attackingParty, defendingTerritory);
        this.rounds.add(nextRound);
        return nextRound;
    }

    /**
     * speeds-play the next round and commit the results
     *
     * @return the report of the round
     */
    @NotNull
    public BattleRoundReport blitzNextRound() throws Exception {
        return this.nextRound().blitz();
    }

    @NotNull
    public Collection<@NotNull BattleRoundReport> blitz() throws Exception {
        Collection<@NotNull BattleRoundReport> reports = new ArrayList<>(1);
        while (hasNextRound()) {
            reports.add(blitzNextRound());
        }
        return reports;
    }

    public boolean isAttackerVictorious() {
        return this.rounds.size() != 0
                && this.rounds.get(this.rounds.size()-1).getReport().isPresent()
                && this.rounds.get(this.rounds.size()-1).getReport().get().isAttackerVictorious();
    }
}
