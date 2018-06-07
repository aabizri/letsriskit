import com.google.gson.Gson;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WorldInterchangeFormat {
    private String name;
    private Collection<RegionIR> regions;

    private WorldInterchangeFormat() {};

    private class RegionIR {
        public String name;
        public Collection<TerritoryIR> territories;
    }

    private class TerritoryIR {
        public String name;
        public Collection<String> neighbours;
    }

    private Map<String, Collection<String>> makeNeighbourhoodMaps() {
        int amountOfTerritories = this.extractTerritories().size();
        Map<String,Collection<String>> neighbours = new HashMap<>(amountOfTerritories);
        this.extractTerritories().forEach(t -> neighbours.put(t.name, t.neighbours));
        return neighbours;
    }

    private Collection<TerritoryIR> extractTerritories() {
        return this.regions.stream().flatMap(r -> r.territories.stream()).collect(Collectors.toList());
    }

    private boolean isCoherent() {
        Map<String, Collection<String>> neighbourhoodMap = this.makeNeighbourhoodMaps();

        boolean incoherent = neighbourhoodMap.entrySet().stream().anyMatch(e -> {
            String fixed = e.getKey();
            Collection<String> hisNeighbours = e.getValue();
            return hisNeighbours.stream().anyMatch(declaredNeighbour ->
                    neighbourhoodMap.get(declaredNeighbour) == null
                            || neighbourhoodMap.get(declaredNeighbour).stream().noneMatch(secondDegreeNeighbour -> secondDegreeNeighbour.equals(fixed))
            );
        });

        return !incoherent;
    }

    public static WorldInterchangeFormat unmarshalJSON(Reader source) {
        Gson gson = new Gson();
        return gson.fromJson(source, WorldInterchangeFormat.class);
    }

    public World transformToWorld() {
        Map<String, Collection<String>> neighbourhoodMap = this.makeNeighbourhoodMaps();
        Map<String, Territory> spawnedTerritories = new HashMap<>(neighbourhoodMap.size());
        Collection<Region> finalRegions = new ArrayList<>(this.regions.size());

        for (RegionIR regionIR: this.regions) {
            Region regionFinal = new Region(regionIR.name);
            for (TerritoryIR territoryIR: regionIR.territories) {
                Territory spawnedTerritory = regionFinal.newTerritory(territoryIR.name);
                spawnedTerritories.put(territoryIR.name,spawnedTerritory);
            }
            finalRegions.add(regionFinal);
        }

        neighbourhoodMap.forEach((fixed, neighbours) -> neighbours.forEach(neighbour -> spawnedTerritories.get(fixed).addNeighbour(spawnedTerritories.get(neighbour))));

        World world = new World(this.name, finalRegions);

        return null;
    }
}
