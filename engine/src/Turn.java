public class Turn {
    private int turnNumber;
    private Turn previousTurn;
    private Player player;
    private Board board;

    public ReinforcementsInteraction getReinforcements() {
        return new ReinforcementsInteraction(game, player, game.unitTypes, ReinforcementsInteraction.calculateUnitQuantity(player,board,previousTurn));
    }

    public int capturedTerritories() {
        return 0; //TODO: Return how much territories were captured in that turn
    }
}
