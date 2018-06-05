package letsriskit.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReinforcementsDialogue {
    private Game game;
    private Player player;

    // distribution stores the letsriskit.engine.UnitType->Quantity distribution for reinforcements
    private Map<UnitType, Integer> distribution;

    public ReinforcementsDialogue(Game game, Player owner, List<UnitType> unitTypeList, int unitQuantity) {
        this.player = owner;

        this.distribution = new HashMap<>(unitTypeList.size());

        // Prepare keys
        unitTypeList.forEach(ut -> this.distribution.put(ut, 0));

        // Set the quantity of soldiers
        assert (distribution.containsKey(UnitType.soldier));
        distribution.put(UnitType.soldier, unitQuantity);
    }

    // Get unit total cost
    public int getTotalValue() {
        return distribution.keySet().stream().mapToInt(ut -> ut.getCost() * distribution.get(ut)).sum();
    }

    // Get number of units as currently configured
    public int getQuantity() {
        return distribution.values().stream().mapToInt(i -> i).sum();
    }

    // Get distribution
    public Map<UnitType, Integer> getDistribution() {
        return this.distribution;
    }

    // Recruitment procedure: convert some soldiers for some other units
    public void recruit(UnitType recruitType, int quantity) throws Exception {
        int transactionCost = recruitType.getCost() * quantity;
        int soldierQuantity = this.distribution.get(UnitType.soldier);

        // Sanity check
        if (soldierQuantity < transactionCost) {
            throw new Exception("Not enough soldiers to recruit riders and/or guns");
        }

        // Remove soldiers
        this.distribution.put(UnitType.soldier, soldierQuantity - transactionCost);

        // Add the new units
        int preExistingUnitsOfThatType = this.distribution.get(recruitType);
        this.distribution.put(recruitType, preExistingUnitsOfThatType + quantity);
    }

    // Disband procedure: convert some unit type to soldier
    public void unrecruit(UnitType recruitType, int quantity) throws Exception {
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

    // Transform all the reinforcements to a single letsriskit.engine.UnitSelection
    public UnitSelection transform() throws Exception {
        List<Unit> units = new ArrayList<>();
        distribution.forEach( (ut, q) ->
            units.addAll(
                    ut.spawn(this.player, q)
            )
        );
        distribution.clear();
        return new UnitSelection(game, units);
    }

    // Drop so many units of that type on a given territory
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

    // Drop the types on

    // Close it
    public void close() {
        this.distribution.clear();
        this.distribution = null;
    };
}
