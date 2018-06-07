import org.jetbrains.annotations.NotNull;

/**
 * Should be thread-safe
 */
public interface SubMission {
    @NotNull
    String getObjective();

    int getTarget();

    int getStatus(@NotNull Player player, @NotNull Board board);

    default boolean isAchieved(@NotNull Player player, @NotNull Board board) {
        assert(player != null); assert(board != null);

        return this.getStatus(player,board) == this.getTarget();
    }
}
