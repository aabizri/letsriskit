package gui;

import java.awt.*;

public class Player {

    private String name;
    private Boolean IA;
    private Color couleur;

    public Player(String name, Boolean IA, Color couleur){
        this.name = name;
        this.IA = IA;
        this.couleur = couleur;
    }

    public String getName() {
        return name;
    }

    public Boolean getIA() {
        return IA;
    }

    public Color getCouleur() { return couleur; }
}
