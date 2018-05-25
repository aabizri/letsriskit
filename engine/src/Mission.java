import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Immutable, so thread-safe as well
 */
public class Mission {
    @NotNull private final String objective;
    @NotNull private final Collection<@NotNull SubMission> subMissions;

    public Mission(@NotNull String objective, @NotNull Collection<@NotNull SubMission> subMissions) {
        assert(objective != null); assert(subMissions != null);
        assert(subMissions.size() > 0);
        assert(subMissions.stream().allMatch(sm -> sm != null));

        this.objective = objective;
        this.subMissions = subMissions;
    }

    @NotNull
    public String getObjective() {
        return this.objective;
    }

    @NotNull
    public Collection<SubMission> getSubMissions() {
        return new ArrayList<>(subMissions);
    }

    public boolean isAchieved(@NotNull Player player, @NotNull Board board) {
        assert(player != null); assert(board != null);

        return subMissions.stream().allMatch(sm -> sm.isAchieved(player,board));
    }
}
