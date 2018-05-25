import java.util.function.BiFunction;

public class SubMission {
    private String objective;
    private int target;
    private BiFunction<Player,Board,Integer> checker;

    public SubMission(String objective, int target, BiFunction<Player,Board,Integer> checker) {
        this.objective = objective;
        this.target = target;
        this.checker = checker;
    }

    public String getObjective() {
        return objective;
    }

    public int getTarget() {
        return this.target;
    }

    public int getStatus(Player player, Board board) {
        return this.checker.apply(player,board);
    }

    public boolean isAchieved(Player player, Board board) {
        return this.getStatus(player,board) == this.getTarget();
    }

}
