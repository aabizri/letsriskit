import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

/**
 * Immutable, so thread-safe as well
 */
public class SubMission {
    @NotNull private final String objective;
    private final int target;
    @NotNull private final BiFunction<Player,Board,Integer> checker;

    public SubMission(@NotNull String objective, int target, @NotNull BiFunction<Player,Board,Integer> checker) {
        assert(objective != null); assert(checker != null);

        this.objective = objective;
        this.target = target;
        this.checker = checker;
    }

    @NotNull
    public String getObjective() {
        return objective;
    }

    public int getTarget() {
        return this.target;
    }

    public int getStatus(@NotNull Player player, @NotNull Board board) {
        assert(player != null); assert(board != null);

        return this.checker.apply(player,board);
    }

    public boolean isAchieved(@NotNull Player player, @NotNull Board board) {
        assert(player != null); assert(board != null);

        return this.getStatus(player,board) == this.getTarget();
    }

}
