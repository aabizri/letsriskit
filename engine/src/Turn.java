public class Turn {
    private int turn;
    private Board board;

    public ReinforcementsDialogue getReinforcements() {
        int unitsFromTerritories = floor(controlledTerritories.count()/3); // floor(T/3)
        int unitsFromRegions = 0;
        for (Region region : controlledRegions) {
            unitsFromRegions += floor(region.territories.count()/2); // floor(N/2)
        }
        int unitsFromCapture = round(rand(1))*; // 50% that you get one from a captured territory
    }
}
