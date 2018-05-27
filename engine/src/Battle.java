import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Battle is a model of a battle in multiple rounds (see BattleRound).
 *
 * Should be thread-safe, as long as the Attacking Units aren't altered and the defending territory not changed
 */
public interface Battle {

    @NotNull
    List<@NotNull BattleRound> getRounds();

    boolean hasNextRound();

    /**
     * Generates and returns the next BattleRound to be played
     */
    @NotNull
    Optional<BattleRound> nextRound();

    /**
     * speeds-play the next round and commit the results
     *
     * @return the report of the round
     */
    @NotNull
    default Optional<BattleRoundReport> blitzNextRound() {
        Optional<BattleRound> nextRound = this.nextRound();
        if (!nextRound.isPresent()) {
            return Optional.empty();
        }

        return nextRound.get().getReport();
    }

    @NotNull
    default List<@NotNull BattleRoundReport> blitz() {
        List<@NotNull BattleRoundReport> reports = new ArrayList<>(1);
        while (hasNextRound()) {
            Optional<BattleRoundReport> report = this.blitzNextRound();
            if (!report.isPresent()) {
                break; // This means we're in a race
            }
        }
        return reports;
    }

    default boolean isAttackerVictorious() {
        List<BattleRound> rounds = this.getRounds();
        return rounds.size() != 0
                && rounds.get(rounds.size() - 1).getReport().isPresent()
                && rounds.get(rounds.size() - 1).getReport().get().isAttackerVictorious();
    }
}
