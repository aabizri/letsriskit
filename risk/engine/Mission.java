package engine;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Should be thread-safe
 */
public interface Mission {
    @NotNull
    String getObjective();

    @NotNull
    Collection<SubMission> getSubMissions();

    default boolean isAchieved(@NotNull Player player, @NotNull Board board) {
        assert(player != null); assert(board != null);

        return this.getSubMissions().stream().allMatch(sm -> sm.isAchieved(player, board));
    }

    default boolean isAvailable(@NotNull Game game) {
        assert(game != null);

        return true;
    }
}
