package engine;

public interface Player {
    String getName();
    void play(Turn turn);

    void prepare(ReinforcementsInteraction interaction);
}
