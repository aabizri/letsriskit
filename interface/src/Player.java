import java.awt.*;
import java.util.List;

public class Player {

   // private engine.Game game;
   // private engine.Player enginePlayer;
    private List<Territory> territories;
    private String name;
    private Boolean IA;
    private String couleur;

    public Player(String name, Boolean IA, String couleur){
        this.name = name;
        this.IA = IA;
        this.couleur = couleur;
    }

    public Color getColor(){
        String c = this.getCouleur();
        Color color = Color.getColor(c);
        return color;
    }

    public String getName() {
        return name;
    }

    public Boolean getIA() {
        return IA;
    }

    public String getCouleur() { return couleur; }
}
