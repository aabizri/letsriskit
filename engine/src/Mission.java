import java.util.Collection;

public class Mission {
    private String objective;
    private Collection<SubMission> subMissions;

    public Mission(String objective, Collection<SubMission> subMissions) {
        this.objective = objective;
        this.subMissions = subMissions;
    }

    public Collection<SubMission> getSubMissions() {
        return subMissions;
    }

    public boolean isAchieved(Player player, Board board) {
        return subMissions.stream().allMatch(sm -> sm.isAchieved(player,board));
    }
}
