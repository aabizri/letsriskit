package gui;

import engine.ReinforcementsInteraction;
import engine.Turn;

public class Player implements engine.Player {

    private String name;
    private Boolean IA;
    private String couleur;

    public Player(String name, Boolean IA, String couleur){
        this.name = name;
        this.IA = IA;
        this.couleur = couleur;
    }

    public String getName() {
        return name;
    }

    @Override
    public void play(Turn turn) {
    }

    private ReinforcementsInteraction initialPlacementInteraction;

    public ReinforcementsInteraction getInitialPlacementInteraction() {
        return this.initialPlacementInteraction;
    }

    @Override
    public void prepare(ReinforcementsInteraction interaction) {
        initialPlacementInteraction = interaction;
    }

    public Boolean getIA() {
        return IA;
    }

    public String getCouleur() { return couleur; }
}
