package engine;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class Turn {
    private int turnNumber;
    private Game game;
    private Turn previousTurn;
    private Player player;
    private int capturedTerritories = 0;
    private AtomicBoolean executing = new AtomicBoolean(false);

    public Turn(@NotNull Game game, int turnNumber, @NotNull Player player) {
        assert (game != null && player != null);
        this.game = game;
        this.turnNumber = turnNumber;
        this.player = player;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Turn)) {
            return false;
        }

        Turn other = (Turn) obj;
        return this.turnNumber == other.turnNumber;
    }

    public @NotNull ReinforcementsInteraction getReinforcements() {
        return new ReinforcementsInteraction(game, player, game.getUnitTypes(), ReinforcementsInteraction.calculateUnitQuantity(player, game.getBoard(), previousTurn));
    }

    public int getCapturedTerritories() {
        return this.capturedTerritories;
    }

    public boolean isExecuting() {
        return executing.get();
    }

    public boolean execute() {
        boolean ok = this.executing.compareAndSet(false, true);
        if (!ok) return false;

        player.play(this);
        return false;
    }
}
