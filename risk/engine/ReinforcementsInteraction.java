package engine;

import java.util.*;

public class ReinforcementsInteraction {
    private Game game;
    private Player player;

    // distribution stores the UnitType->Quantity distribution for reinforcements
    private Map<UnitType, Integer> distribution;

    /**
     * Calculates the quantiy of units that should be given out, given three parameters.
     * This is probabilistic because of the units from captured territories, which means that two calls to this method MAY NOT return the same value
     *
     * @param player the player whose turn it is
     * @param board the board on which we play
     * @param previousTurn the previous turn which is used for calculation of previously captured territories
     *
     * @return the "total budget" for units reinforcements
     */
    public static int calculateUnitQuantity(Player player, Board board, Turn previousTurn) {
        int unitsFromTerritories = (int) Math.floor(
                board.getTerritories().stream().filter(t -> t.getOwner().isPresent() && t.getOwner().get().equals(player)).count()
                        /3); // floor(T/3)
        int unitsFromRegions = (int) Math.floor(
                board.getRegions().stream().filter(r -> r.getOwner().isPresent() && r.getOwner().get().equals(player)).mapToLong(r -> r.getTerritories().size()).sum()
                        /2); // floor(N/2)
        int unitsFromCapture = (int) Math.round(new Random().nextDouble()) * previousTurn.getCapturedTerritories(); // 50% that you get one from a captured territory
        return unitsFromTerritories+unitsFromRegions+unitsFromCapture;
    }

    public ReinforcementsInteraction(Game game, Player owner, Collection<UnitType> unitTypeList, int unitQuantity) {
        this.player = owner;

        this.distribution = new HashMap<>(unitTypeList.size());

        // Prepare keys
        unitTypeList.forEach(ut -> this.distribution.put(ut, 0));

        // Set the quantity of soldiers
        assert (distribution.containsKey(UnitType.soldier));
        distribution.put(UnitType.soldier, unitQuantity);
    }

    /**
     * @return total cost/value of all units (for each unit type: unit type cost * unit quantity of that type)
     */
    public int getTotalValue() {
        return distribution.entrySet().stream().mapToInt(entry-> entry.getKey().getCost() * entry.getValue()).sum();
    }

    /**
     * @return the number of units as currently configured
     */
    public int getQuantity() {
        return distribution.values().stream().mapToInt(i -> i).sum();
    }

    /**
     * @return the distribution
     */
    public Map<UnitType, Integer> getDistribution() {
        return new HashMap<>(this.distribution);
    }

    /**
     * Upgrade procedure: convert some soldiers for some other units
     *
     * TODO: Specify the Exception
     */
    public void upgrade(UnitType recruitType, int quantity) throws Exception {
        int transactionCost = recruitType.getCost() * quantity;
        int soldierQuantity = this.distribution.get(UnitType.soldier);

        // Sanity check
        if (soldierQuantity < transactionCost) {
            throw new Exception("Not enough soldiers to upgrade riders and/or guns");
        }

        // Remove soldiers
        this.distribution.put(UnitType.soldier, soldierQuantity - transactionCost);

        // Add the new units
        int preExistingUnitsOfThatType = this.distribution.get(recruitType);
        this.distribution.put(recruitType, preExistingUnitsOfThatType + quantity);
    }

    /**
     * Downgrade procedure: convert some unit type to soldier
     *
     * TODO: Specify the Exception
     */
    public void downgrade(UnitType recruitType, int quantity) throws Exception {
        int unitsOfThatType = this.distribution.get(recruitType);

        // Sanity check
        if (unitsOfThatType < quantity) {
            throw new Exception("Not enough recruits of that type to unrecruit");
        }

        // Remove units of that type
        this.distribution.put(recruitType, unitsOfThatType - quantity);

        // Add soldiers
        int preExistingSoldiers = this.distribution.get(UnitType.soldier);
        int newSoldiers = recruitType.getCost() * quantity;
        this.distribution.replace(UnitType.soldier, preExistingSoldiers + newSoldiers);
    }

    /**
     *  Transform all the reinforcements to a single UnitSelection, ready to be placed
     */
    public UnitSelection transform() throws CantSelectException {
        List<Unit> units = new ArrayList<>();
        distribution.forEach( (ut, q) ->
            units.addAll(
                    ut.spawn(this.player, q)
            )
        );
        distribution.clear();
        return new UnitSelection(game, units);
    }

    /**
     * Transform the given subselection to a UnitSelection
     *
     * TODO: Specify the Exception launched
     */
    public UnitSelection transform(Map<UnitType,Integer> subselection) throws Exception {
        List<Unit> units = new ArrayList<>();
        for (UnitType ut : subselection.keySet()) {
            int previouslyUndistributedUnits = distribution.get(ut);
            int unitsToDistribute = subselection.get(ut);

            if (unitsToDistribute > previouslyUndistributedUnits) {
                throw new Exception("There isn't that much units of that type to distribute");
            }

            units.addAll(ut.spawn(this.player, unitsToDistribute));
            distribution.replace(ut, previouslyUndistributedUnits - unitsToDistribute);
        }
        return new UnitSelection(game, units);
    }

    public void close() {
        this.distribution.clear();
        this.distribution = null;
    }
}
