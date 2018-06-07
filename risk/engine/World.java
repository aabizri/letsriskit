package engine;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Collection;

public class World {
    private final static String realWorldFilename = "world_map.json";
    public final static World realWorld;

    static {
        World realWorld1;
        try {
            realWorld1 = World.unmarshal(new FileReader(realWorldFilename));
        } catch (FileNotFoundException e) {
            realWorld1 = null;
            e.printStackTrace();
        }
        realWorld = realWorld1;
    }

    @NotNull
    private final String name;
    @NotNull
    private final Collection<Region> regions;

    public World(@NotNull String name, @NotNull Collection<Region> regions) {
        this.name = name;
        this.regions = regions;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull Collection<Region> getRegions() {
        return regions;
    }

    public static World unmarshal(Reader source) {
        return WorldInterchangeFormat.unmarshalJSON(source).transformToWorld();
    }

    @TestOnly
    public static void main(String []args) {
        FileReader fileReader;
        try {
            fileReader = new FileReader("world_map.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        World.unmarshal(fileReader);
    }
}
