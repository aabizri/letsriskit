import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

public class World {
    @NotNull private String name;
    @NotNull private Collection<Region> regions;

    public World(String name, Collection<Region> regions) {
        this.name = name;
        this.regions = regions;
    }

    public static World unmarshal(Reader source) {
        return WorldInterchangeFormat.unmarshalJSON(source).transformToWorld();
    }

    @TestOnly
    public static void main(String []args) {
        FileReader fileReader;
        try {
            fileReader = new FileReader("real_world.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        World.unmarshal(fileReader);
    }
}
