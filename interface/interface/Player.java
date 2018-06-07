public class Player {

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

    public Boolean getIA() {
        return IA;
    }

    public String getCouleur() { return couleur; }
}
